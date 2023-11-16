package com.example.proyectonuevoamanecer.screens.flashcards

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.clases.Mazos
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.ui.theme.ProyectoNuevoAmanecerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectonuevoamanecer.clases.CartaFlash

@Composable
fun FlashcardDecks(navController: NavController)
{
    val context = LocalContext.current
    val database = FlashcardDatabase.getInstance(context)
    val viewModel:FlashViewModel= viewModel(factory = FlashViewModelFactory(database))
    val showDialog = remember { mutableStateOf(false)}
    val mazos by viewModel.allMazos.collectAsState(initial = emptyList())
    val mazosList: MutableList<Mazos> = mazos.map {
        Mazos(it.titulo, it.flashcardList)
    }.toMutableList()
    BodyContentDecks(navController,showDialog, mazosList)
    Text(text = "Mazos", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
    if(showDialog.value){
        CrearMazoDialog(showDialog){mazoTitulo->
            val newMazo = MazoEntity(0, mazoTitulo, mutableListOf())
            viewModel.insertMazo(newMazo)

        }
    }

}

data class DropDownItem(
    val text: String
)

@Composable
fun PersonItem(
    personName: String,
    dropDownItems: List<DropDownItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
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
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current
    Spacer(modifier = Modifier.height(20.dp))
    Card(

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(

            modifier = Modifier
                .fillMaxWidth()
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current
                )
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                            navController.navigate(AppRoutes.FlashcardGame.route + "/${personName}")
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
}

@Composable
fun BodyContentDecks(navController: NavController,showDialog:MutableState<Boolean>, mazosList:MutableList<Mazos>) {


    LazyColumn(

        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(mazosList)
         { mazo ->
            PersonItem(
                personName = mazo.titulo,
                navController= navController,
                dropDownItems = listOf(
                    DropDownItem("Añadir Tarjeta"),
                    DropDownItem("Renombrar Mazo"),
                    DropDownItem("Borrar Mazo"),
                    DropDownItem("Editar Mazo"),
                )
            ) {


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

















