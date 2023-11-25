import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.Diferencias
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectonuevoamanecer.clases.BotonDiferencias
import com.example.proyectonuevoamanecer.screens.juegos.memorama.MemoramaViewModel
import com.example.proyectonuevoamanecer.screens.juegos.memorama.NavigationButton


@Preview
@Composable
fun prev(){
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
    //ImageCard(diferenciaImg = samplePokemon1)
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

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),

                ) {
                val img1 = painterResource(id = diferenciaImg.image1)
                Image(painter = img1, contentDescription = "Encuentra las diferencias",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(),
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
                                var x = (1000f / 1002 * anchoImagen) / clickPosition.x
                                var y = (1000f / 596 * largoImagen) / clickPosition.y
                                var ind = findDiff(X = x, Y = y, diffList = diferenciaImg.differences)
                                if (ind != -1) {
                                    diferenciaImg.differences.removeAt(ind)
                                    listSize--
                                }
                                //XD = findDiff(X = x, Y = y, diffList = diferenciaImg.differences)
                            }
                        }
                    /*.clickable {
                        println("...................................")
                        //
                        }*/
                    ,
                    alignment = Alignment.Center

                )

            }
            Column(modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                WinningMess(diferenciasFaltanttes = listSize, navController)
                Text("Diferencias restantes: $listSize", style = MaterialTheme.typography.displayMedium)
                /*var W = (1000f/1002 * anchoImagen) / clickPosition.x
                var Z = (1000f/596 * largoImagen) / clickPosition.y
                Text("X: $W Y: $Z", style = MaterialTheme.typography.bodyMedium)*/
                //Text("Diff: $XD", style = MaterialTheme.typography.bodyMedium)
                Text(text = diferenciaImg.name, style = MaterialTheme.typography.displaySmall)
                //Text(text = diferenciaImg.hp, style = MaterialTheme.typography.bodyMedium)
            }

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


