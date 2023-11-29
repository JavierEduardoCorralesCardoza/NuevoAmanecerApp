package com.example.proyectonuevoamanecer.screens.juegos.numeros

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel.NumerosViewModelFactory
import com.example.proyectonuevoamanecer.api.llamarApi
import com.example.proyectonuevoamanecer.clases.BotonBase
import com.example.proyectonuevoamanecer.fonts.MyFontFamily
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.Configuracion
import com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion.MenuConfiguracion
import com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel.NumerosViewModel
import com.example.proyectonuevoamanecer.screens.juegos.numeros.viewModel.ScreenViewModel
import com.example.proyectonuevoamanecer.widgets.Gif
import org.json.JSONObject
enum class GameScreenState {
    SelectLevel,
    PlayNormal,
    PlayTimed
}

@Composable
fun SelectLevelScreen(onModeSelected: (Int) -> Unit) {
    /*Gif(
        gif = R.font.,
        modifier = Modifier.fillMaxSize()
    )*/
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                verticalArrangement= Arrangement.Center,
                horizontalAlignment= Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    "Selecciona un Modo",
                    style = MaterialTheme.typography.displayMedium,
                    //fontWeight = FontWeight.Bold,
                    color = Color(0xF7, 0xEE, 0xBA, 0xFF),
                    fontFamily = MyFontFamily,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onModeSelected(1) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Normal")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onModeSelected(2) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Temporizado")
                }
            }
        }
    }
}
@Composable
fun Numeros(navController: NavController) {
    val screenViewModel: ScreenViewModel = viewModel()
    when (screenViewModel.currentScreen.value) {
        GameScreenState.SelectLevel -> SelectLevelScreen(screenViewModel::onModeSelected)
        GameScreenState.PlayNormal -> GameScreen(Timed = false)
        GameScreenState.PlayTimed -> GameScreen(Timed = true)
    }
}

@Composable
fun GameScreen(Timed: Boolean){
    val primaryColor = Color.Transparent
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val application = LocalContext.current.applicationContext as Application
    val viewModel = ViewModelProvider(viewModelStoreOwner!!, NumerosViewModelFactory(
        application,
        obtenerConfiguracion(), Timed
    )
    )[NumerosViewModel::class.java]

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement= Arrangement.Center,
            horizontalAlignment= Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = primaryColor)

                //.border(width = 2.dp, color = Color.Black)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Números",
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart),
                    style = MaterialTheme.typography.displayLarge,
                    fontFamily = MyFontFamily,
                    textAlign = TextAlign.Center,
                    color = Color(0x21, 0x96, 0xF3, 0xFF)
                )
                BotonBase {
                    MenuConfiguracion(viewModel)
                }
            }
            Text(
                text = "Instrucciones: Haz click en orden ${if (viewModel.ordenActual == "ASC") "Ascendente" else "Descendente"}",
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterStart),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (Timed) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "Tiempo restante: ${viewModel.tiempoRestante.value / 1000}s",
                        modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)
                            .weight(1f),
                        fontFamily = MyFontFamily,
                        textAlign = TextAlign.Center
                    )  // Muestra el tiempo restante
                }
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = "Puntaje más alto: ${if (Timed) viewModel.highScoreTimed.value else viewModel.highScoreNormal.value}",
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart)
                        .weight(1f),
                    fontFamily = MyFontFamily,
                    textAlign = TextAlign.Center
                )  // Muestra el puntaje más alto
            }
        }

        Box(
            modifier = Modifier
                .weight(2f)
                //.padding(start = 16.dp, end = 16.dp)
                //.border(width = 2.dp, color = Color.Black)
        ) {
            GameBox(viewModel = viewModel, Timed = Timed)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = primaryColor)
                //.border(width = 2.dp, color = Color.Black)
        ) {
            if (!viewModel.juegoEnProgreso.value) {
                Button(onClick = {
                    viewModel.startGame() }, modifier = Modifier.align(Alignment.Center)) {
                    Text(style = MaterialTheme.typography.displaySmall,
                        text = "Iniciar juego",
                        modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart),
                        fontFamily = MyFontFamily,
                        textAlign = TextAlign.Center
                        )
                }
            }
        }
    }
}
@Composable
fun GameBox(viewModel: NumerosViewModel,Timed: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
    var puntacion by remember { mutableIntStateOf(0) }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x0, 0x0, 0x0, 0xF3)),
        contentAlignment = Alignment.Center) {
        Gif(
            gif = R.drawable.lines,
            modifier = Modifier.fillMaxSize(),
            alfa = 0.3f
        )
        val density = LocalDensity.current
        val boxWidth = with(density) { constraints.maxWidth.toDp() / 2 }
        val boxHeight = with(density) { constraints.maxHeight.toDp() / 2 }
        val textHeight = with(density) { MaterialTheme.typography.displaySmall.fontSize.toDp() }
        LaunchedEffect(Unit){
            viewModel.setConstraints(boxWidth, boxHeight, textHeight)}
        if (viewModel.juegoEnProgreso.value){
        viewModel.numbers.forEach { number ->
            // Determina el color del numero
            val color = viewModel.coloresNumeros[number] ?: Color.White
            // Usa las posiciones aleatorias generadas
            val (offsetX, offsetY) = viewModel.posiciones[number] ?: Pair(0.dp, 0.dp)
            Text(
                text = number.toString(),
                color = color,
                modifier = Modifier
                    .padding(4.dp)
                    .absoluteOffset(offsetX, offsetY) // Cambiar la posicion del numero
                    .clickable {
                        viewModel.addNumber(number)
                    },
                style = MaterialTheme.typography.displayMedium,
                fontFamily = MyFontFamily,
                textAlign = TextAlign.Center
            )
        }
            Text(style = MaterialTheme.typography.displaySmall,
                text = "Puntuación: ${viewModel.score.value}",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
                    .wrapContentSize(Alignment.CenterStart),  // Muestra la puntuación
                fontFamily = MyFontFamily,
                textAlign = TextAlign.Center,
                color = Color(0x80, 0xD5, 0x9E, 0xFF))
        }
        else {
            Box(modifier = Modifier.fillMaxSize()){
                Text("¡NÚMEROS!", style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentSize(Alignment.CenterStart),
                        fontFamily = MyFontFamily,
                        textAlign = TextAlign.Center
                )  // Muestra el título del juego
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(style = MaterialTheme.typography.displayLarge,
                    text = "Juego terminado",
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart))},
                text = { Text(style = MaterialTheme.typography.displayMedium,
                    text = "Puntuación obtenida: $puntacion",
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterStart)) },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        viewModel.startGame()
                    }) {
                        Text(style = MaterialTheme.typography.displaySmall,
                            text = "Intentar otra vez",
                            modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart))
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text(style = MaterialTheme.typography.displaySmall,
                            text = "Cerrar",
                            modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart))
                    }
                }
            )
        }
    }

    if (Timed) {
        LaunchedEffect(viewModel.tiempoRestante.value) {
            if (viewModel.tiempoRestante.value <= 0
                && viewModel.juegoEnProgreso.value) {
                showDialog = true
                puntacion = viewModel.score.value
            }
        }
    }
}

fun obtenerConfiguracion(): Configuracion {
    return if (hayConexionInternet()) {
        val response = llamarApi("miembros", emptyMap(),"GET")
        Configuracion(response)
    } else {
        // Devuelve la configuración predeterminada si no hay conexión a Internet
        Configuracion(JSONObject("{\"Niveles\":[{\"Rango\":10,\"CantidadNumeros\":10,\"Orden\":\"ASC\"},{\"Rango\":10,\"CantidadNumeros\":10,\"Orden\":\"DESC\"}],\"TiempoInicial\":30,\"TiempoAgregar\":15,\"TiempoClickIncorrecto\":1,\"PuntosClickCorrecto\":50,\"PuntosClickIncorrecto\":10,\"PuntosCompletarSet\":150}"))
    }
}

fun hayConexionInternet(): Boolean {
    // Implementa esta función para verificar si hay conexión a Internet
    // ...
    return false
}
