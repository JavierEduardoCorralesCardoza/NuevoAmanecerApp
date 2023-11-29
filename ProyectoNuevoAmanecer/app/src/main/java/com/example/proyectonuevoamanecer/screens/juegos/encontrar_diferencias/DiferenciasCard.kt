import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.Diferencias
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.clases.BotonDiferencias
import com.example.proyectonuevoamanecer.screens.juegos.memorama.MemoramaViewModel
import com.example.proyectonuevoamanecer.screens.juegos.memorama.NavigationButton
import kotlinx.coroutines.delay


@Preview
@Composable
fun prev(){
    val samplePokemon1 = Diferencias(
        R.drawable.diff_image1,
        R.drawable.diff_image2,
        "Cavernicolas",
        differenceNumber = 8 ,
        differences = mutableListOf<BotonDiferencias>(
            BotonDiferencias(minX = 6f, minY = 3f, maxX = 12f, maxY = 6f),
            BotonDiferencias(minX = 1.4f, minY = 4.4f, maxX = 1.63f, maxY = 6.5f),
            BotonDiferencias(minX = 2.9f, minY = 2.8f, maxX = 3.8f, maxY = 3.5f),
            BotonDiferencias(minX = 1.0f, minY = 3.00f, maxX = 1.15f, maxY = 3.5f),
            BotonDiferencias(minX = 7f, minY = 2f, maxX = 12.2f, maxY = 2.66f),
            BotonDiferencias(minX = 2f, minY = 2.2f, maxX = 2.34f, maxY = 3f),
            BotonDiferencias(minX = 1.190f, minY = 2.380f, maxX = 1.345f, maxY = 2.845f),
            BotonDiferencias(minX = 1.2370f, minY = 1.970f, maxX = 1.479f, maxY = 2.370f),
        ))
}

@Composable
fun DiferenciasCard(navController: NavController, lvl: Int){

    val nivel: Int = when (lvl){
        1 -> 4
        2 -> 2
        3 -> 0
        else -> {
            1
        }
    }
    val samplePokemon1 = Diferencias(
        R.drawable.diff_image1,
        R.drawable.diff_image2,
        "Cavernicolas",
        differenceNumber = 8 ,
        differences = mutableListOf<BotonDiferencias>(
            BotonDiferencias(minX = 6f, minY = 3f, maxX = 11f, maxY = 5f),
            BotonDiferencias(minX = 1.4f, minY = 4.4f, maxX = 1.53f, maxY = 6.1f),
            BotonDiferencias(minX = 2.9f, minY = 2.8f, maxX = 3.5f, maxY = 3.3f),
            BotonDiferencias(minX = 1.055f, minY = 3.02f, maxX = 1.11f, maxY = 3.5f),
            BotonDiferencias(minX = 7.1f, minY = 2.2f, maxX = 12.2f, maxY = 2.66f),
            BotonDiferencias(minX = 2.1f, minY = 2.32f, maxX = 2.34f, maxY = 3f),
            BotonDiferencias(minX = 1.193f, minY = 2.387f, maxX = 1.245f, maxY = 2.645f),
            BotonDiferencias(minX = 1.2376f, minY = 1.9723f, maxX = 1.479f, maxY = 2.370f),
            ))
    //val listDiff = listOf<Diferencias>(samplePokemon1)
    ImageCard(diferenciaImg = samplePokemon1, navController, nivel)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ImageCard(diferenciaImg: Diferencias, navController: NavController, nivel: Int) {
    var clickPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var listSize by remember { mutableStateOf((diferenciaImg.differences.size)-nivel) }
    //var XD by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxSize(),

        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = diferenciaImg.name, style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.White)
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .clip(RoundedCornerShape(16.dp))
                ,
                ) {
                val img1 = painterResource(id = diferenciaImg.image1)
                Image(painter = img1, contentDescription = "Encuentra las diferencias",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showSnackbar = true } ,
                    alignment = Alignment.Center
                )
            }
            var anchoImagen by remember { mutableStateOf(0f) }
            var largoImagen by remember { mutableStateOf(0f) }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .padding(0.dp)
                    //.border(width = 2.dp, color = Color.Yellow)

            ) {
                val img2 = painterResource(id = diferenciaImg.image2)
                Image(painter = img2, contentDescription = "Encuentra las diferencias",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { size ->
                            anchoImagen = size.width.toFloat()
                            largoImagen = size.height.toFloat()

                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                clickPosition = offset
                                val x = (1000f / 1002 * anchoImagen) / clickPosition.x
                                val y = (1000f / 596 * largoImagen) / clickPosition.y
                                val ind =
                                    findDiff(X = x, Y = y, diffList = diferenciaImg.differences)
                                if (ind != -1) {
                                    diferenciaImg.differences.removeAt(ind)
                                    listSize--
                                }
                                //XD = findDiff(X = x, Y = y, diffList = diferenciaImg.differences)
                            }

                        }
                    ,
                    alignment = Alignment.Center

                )

            }
            Card(
                modifier = Modifier.padding(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xC0, 0x5A, 0x22, 0xFF))
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    WinningMess(diferenciasFaltanttes = listSize, navController)

                    Text("Diferencias restantes:",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text("$listSize",
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )


                    /*var W = (1000f/1002 * anchoImagen) / clickPosition.x
                    var Z = (1000f/596 * largoImagen) / clickPosition.y
                    Text("X: $W Y: $Z", style = MaterialTheme.typography.bodyMedium)*/
                    //Text("Diff: $XD", style = MaterialTheme.typography.bodyMedium)

                    //Text(text = diferenciaImg.hp, style = MaterialTheme.typography.bodyMedium)
                }
            }


        }
    }
    if (showSnackbar) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
            }
        ) {
            Text("Selecciona las diferencias en la pantalla de abajo")
        }

        // Ocultar el Snackbar después de 2 segundos
        LaunchedEffect(key1 = showSnackbar) {
            delay(2000L)
            showSnackbar = false
        }
    }

}

fun findDiff(X: Float, Y:Float, diffList: MutableList<BotonDiferencias>): Int {
    for(i in 0 until diffList.size){
        if(
            X > diffList[i].minX
            && X < diffList[i].maxX
            && Y > diffList[i].minY
            && Y < diffList[i].maxY){
            //diffList[i].active = false
            //diffList.removeAt(i)
            return i
        }
    }
    return -1
}
@Composable
fun WinningMess(diferenciasFaltanttes: Int, navController: NavController){
    if (diferenciasFaltanttes == 0) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("¡Felicidades!") },
            text = { Text("Haz ganadoooooo") },
            confirmButton = {
                NavigationButton(navController = navController)
            }
        )
    }
}