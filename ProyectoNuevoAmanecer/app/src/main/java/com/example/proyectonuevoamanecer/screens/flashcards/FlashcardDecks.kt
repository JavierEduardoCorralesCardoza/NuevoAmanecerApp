package com.example.proyectonuevoamanecer.screens.flashcards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.screens.AppRoutes

@Composable
fun FlashcardDecks(navController: NavController)
{
    val context = LocalContext.current
    val database = FlashcardDatabase.getInstance(context)
    val viewModel:FlashViewModel= viewModel(factory = FlashViewModelFactory(database))
    val showDialog = remember { mutableStateOf(false)}
    val mazos by viewModel.allMazos.collectAsState(initial = emptyList())

    BodyContentDecks(navController,showDialog, mazos, viewModel)
    Text(text = "Mazos", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
    if(showDialog.value){
        CrearMazoDialog(showDialog){mazoTitulo->
            val newMazo = MazoEntity(0, mazoTitulo)
            viewModel.insertMazo(newMazo)
        }
    }

}

data class DropDownItem(
    val text: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonItem(
    mazo: MazoEntity,
    personName: String,
    dropDownItems: List<DropDownItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FlashViewModel,
    onItemClick: (DropDownItem) -> Unit,
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    var showDialog by remember{
        mutableStateOf(false)
    }
    var mazoCartas = viewModel.getMazoConCartasPorNombre(mazo.titulo).collectAsState(initial = null)
    //val interactionSource = remember {
    // MutableInteractionSource()
    //}
    //val density = LocalDensity.current

    Spacer(modifier = Modifier.height(20.dp))
    Card(

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .onSizeChanged {
                itemHeight = it.height.dp
            }
    ) {
        Box(

            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = { offset ->
                            isContextMenuVisible = true
                            pressOffset = DpOffset(offset.x.toDp(), offset.y.toDp())
                        },
                        onTap = {
                            if (!isContextMenuVisible) {
                                if (mazoCartas == null || mazoCartas.value?.cartas?.isEmpty() != false) {
                                    showDialog = true
                                    println("Vacioooo")
                                } else {
                                    showDialog = false
                                    navController.navigate(AppRoutes.FlashcardGame.route + "/${personName}")
                                }
                            }
                        }
                    )

                }
                .padding(16.dp)
        ) {
            Text(text = personName)
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemClick(item)
                        isContextMenuVisible = false
                    }, text = { Text(text = item.text) })

            }
        }
    }
    if(showDialog){
        AlertDialog(
            onDismissRequest = {showDialog=false },
            title={Text("Error")},
            text={Text("El mazo está vacío. Haz clic en 'Agregar Tarjeta' para añadir tarjetas al mazo.")},
            confirmButton = {
                Button(
                    onClick={showDialog=false}
                ){
                    Text("Aceptar")
                }
            })
    }
}


@Composable
fun BodyContentDecks(
    navController: NavController,
    showDialog:MutableState<Boolean>,
    mazos:List<MazoEntity>,
    viewModel: FlashViewModel= androidx.lifecycle.viewmodel.compose.viewModel()
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(mazos) { mazo ->
            val showDeleteDialog = remember { mutableStateOf(false) }
            val showRenameDialog = remember { mutableStateOf(false)}
            val showAddCardDialog= remember{ mutableStateOf(false)}
            PersonItem(
                mazo= mazo,
                personName = mazo.titulo,
                navController= navController,
                dropDownItems = listOf(
                    DropDownItem("Añadir Tarjeta"),
                    DropDownItem("Renombrar Mazo"),
                    DropDownItem("Borrar Mazo"),
                    DropDownItem("Editar Mazo"),
                ),
                viewModel = viewModel
            ) {item ->
                when(item.text){
                    "Añadir Tarjeta" ->{
                        showAddCardDialog.value=true
                    }
                    "Borrar Mazo"->{
                        val MazoId = mazo.id
                        viewModel.deleteMazo(MazoId, mazos)
                    }

                    "Renombrar Mazo"->{
                        showRenameDialog.value = true
                    }

                    "Editar Mazo"->{
                        showDeleteDialog.value = true
                    }
                }
            }

            if(showAddCardDialog.value){
                AddCardDialog(showAddCardDialog,mazo.id ){newCard ->
                    viewModel.insertCartaFlashIntoMazo(newCard,mazo.id)
                    viewModel.renameMazo(mazo.id, mazo.titulo)

                }
            }
            if(showRenameDialog.value){
                RenameMazoDialog(showRenameDialog, mazo.id){newName->
                    viewModel.renameMazo(mazo.id, newName)
                    showRenameDialog.value = false
                }
            }
            if(showDeleteDialog.value){
                val mazoCartas = viewModel.getMazoConCartasPorNombre(mazo.titulo).collectAsState(null)
                DeleteCardDialog(showDeleteDialog, mazoCartas.value?.cartas ?: emptyList()){carta->
                    viewModel.deleteCartaFlash(carta.id)
                }
            }
        }
    }

    Box(
        modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier.paddingFromBaseline(top = 0.dp, bottom = 32.dp)
        ) {
            Text("Crear Mazo")
        }
    }
}

















































