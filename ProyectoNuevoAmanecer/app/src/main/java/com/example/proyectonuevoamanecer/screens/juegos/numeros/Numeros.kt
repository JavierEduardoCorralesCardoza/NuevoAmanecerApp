package com.example.nuevoamanecer_numeros.Numeros

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.example.nuevoamanecer_numeros.Numeros.viewModel.NumerosViewModel
import com.example.nuevoamanecer_numeros.Numeros.viewModel.NumerosViewModelFactory
import com.example.proyectonuevoamanecer.api.llamarApi
import org.json.JSONObject
enum class GameScreenState {
    SelectLevel,
    PlayNormal,
    PlayTimed
}

@Composable
fun SelectLevelScreen(onModeSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona un Modo",style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Button(onClick = { onModeSelected(1) }) {
                Text("Normal")
            }
        }
        Column {
            Button(onClick = { onModeSelected(2) }) {
                Text("Temporizado")
            }
        }
    }
}
@Composable
fun Numeros(navController: NavController) {
    var currentScreen by remember { mutableStateOf(GameScreenState.SelectLevel) }
    when (currentScreen) {
        GameScreenState.SelectLevel -> SelectLevelScreen { level ->
            currentScreen = when (level) {
                1 -> GameScreenState.PlayNormal
                2 -> GameScreenState.PlayTimed
                else -> GameScreenState.SelectLevel
            }
        }
        GameScreenState.PlayNormal -> GameScreen(Timed = false)
        GameScreenState.PlayTimed -> GameScreen(Timed = true)
    }
}
@Composable
fun GameScreen(Timed: Boolean){
    val primaryColor = MaterialTheme.colorScheme.tertiary
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val application = LocalContext.current.applicationContext as Application
    val viewModel = ViewModelProvider(viewModelStoreOwner!!, NumerosViewModelFactory(application, 1,true,Timed)).get(NumerosViewModel::class.java)

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = primaryColor)
                .border(width = 2.dp, color = Color.Black)
        ) {
            if (Timed) {
                Text(style = MaterialTheme.typography.displaySmall,
                    text = "Tiempo restante: ${viewModel.tiempoRestante.value / 1000}s",
                    modifier = Modifier.align(Alignment.TopStart))}  // Muestra el tiempo restante
            Text(style = MaterialTheme.typography.displaySmall,
                text = "Puntaje más alto: ${if (Timed) viewModel.highScoreTimed.value else viewModel.highScoreNormal.value}",
                modifier = Modifier.align(Alignment.TopEnd))  // Muestra el puntaje más alto
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .border(width = 2.dp, color = Color.Black)
        ) {
            GameBox(viewModel = viewModel, Timed = Timed)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = primaryColor)
                .border(width = 2.dp, color = Color.Black)
        ) {
            if (!viewModel.juegoEnProgreso.value) {
                Button(onClick = {
                    viewModel.startGame() }, modifier = Modifier.align(Alignment.Center)) {
                    Text(style = MaterialTheme.typography.displaySmall,
                        text = "Iniciar juego")
                }
            }
        }
    }
}



fun Configuracion(DatosJuego: JSONObject): Int {
    val grupo = DatosJuego.optJSONObject("Grupo")

    if (grupo != null) {
        val dificultad = grupo.optInt("Dificultad")

        when (dificultad) {
            1 -> return 1
            2 -> return 2
            else -> return 3
        }
    }

    // En caso de que no se encuentre la estructura esperada en los datos JSON
    return -1
}

@Composable
fun GameBox(viewModel: NumerosViewModel,Timed: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
    var puntacion by remember {mutableStateOf(0) }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        val density = LocalDensity.current
        val boxWidth = with(density) { constraints.maxWidth.toDp() / 2 }
        val boxHeight = with(density) { constraints.maxHeight.toDp() / 2 }
        val textHeight = with(density) { MaterialTheme.typography.displaySmall.fontSize.toDp() }
        LaunchedEffect(Unit){
            viewModel.setConstraints(boxWidth, boxHeight, textHeight)}
        if (viewModel.juegoEnProgreso.value){
        viewModel.numbers.forEach { number ->
            // Determina el color del numero
            val color = when {
                viewModel.NumerosIncorrecto.contains(number) -> Color.Red
                viewModel.NumerosOprimidos.contains(number) -> Color.Green
                else -> Color.Black
            }

            // Usa las posiciones aleatorias generadas
            val (offsetX, offsetY) = viewModel.posiciones[number] ?: Pair(0.dp, 0.dp)
            obtenerTodosLosUsuarios()
            Text(
                text = number.toString(),
                color = color,
                modifier = Modifier
                    .padding(4.dp)
                    .absoluteOffset(offsetX, offsetY) // Cambiar la posicion del numero
                    .clickable {
                        viewModel.addNumber(number)
                    },
                style = MaterialTheme.typography.displayMedium
            )
        }
            Text(style = MaterialTheme.typography.displaySmall, text = "Puntuación: ${viewModel.score.value}", modifier = Modifier.align(Alignment.BottomCenter))  // Muestra la puntuación
        }
        else {
            Box(modifier = Modifier.fillMaxSize()){
                Text("¡NÚMEROS!", style = MaterialTheme.typography.displayLarge, modifier = Modifier.align(Alignment.Center))  // Muestra el título del juego
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(style = MaterialTheme.typography.displayLarge, text = "Juego terminado") },
                text = { Text(style = MaterialTheme.typography.displayMedium, text = "Puntuación obtenida: $puntacion") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        viewModel.startGame()
                    }) {
                        Text(style = MaterialTheme.typography.displaySmall, text = "Intentar otra vez")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text(style = MaterialTheme.typography.displaySmall,text = "Cerrar")
                    }
                }
            )
        }
    }

    if (Timed) {
        LaunchedEffect(viewModel.tiempoRestante.value) {
            if (viewModel.tiempoRestante.value?.let { it <= 0 } == true
                && viewModel.juegoEnProgreso.value) {
                showDialog = true
                puntacion = viewModel.score.value
            }
        }
    }
}

fun obtenerTodosLosUsuarios() {
    println("Test 1")
    val response = llamarApi("miembros", emptyMap(),"GET")
    println("Test 2")
    println(response)
}
