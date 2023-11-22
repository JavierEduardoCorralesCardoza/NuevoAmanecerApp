package com.example.juego1jetpc.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R


enum class GameScreenState {
    SelectLevel,
    PlayLevel1,
    CongratulationsLevel1,
    PlayLevel2,
    CongratulationsLevel2,
    PlayLevel3,
    CongratulationsLevel3
}
@Composable
fun EligirImagen(navController: NavController) {
    var currentScreen by remember { mutableStateOf(GameScreenState.SelectLevel) }
    var score by remember { mutableStateOf(0) }

    val nivel1Opciones = listOf(
        Pair(R.drawable.cat, "Gato"),
        Pair(R.drawable.banana, "Banana"),
        Pair(R.drawable.blueberries, "Moras"),
        Pair(R.drawable.casa, "Casa"),
        Pair(R.drawable.perro, "Perro"),
        Pair(R.drawable.fresa, "Fresa"),
        Pair(R.drawable.jugo, "Jugo"),
        Pair(R.drawable.jabon, "Jabón"),
        Pair(R.drawable.lentes, "Lentes"),
        Pair(R.drawable.carro, "Carro"),


        )

    val nivel2Opciones = listOf(
        Pair(R.drawable.husky, "Husky"),
        Pair(R.drawable.computadora, "Computadora"),
        Pair(R.drawable.carne, "Carne"),
        Pair(R.drawable.botella, "Botella"),
        Pair(R.drawable.martillo, "Martillo"),
        Pair(R.drawable.avion, "Avión"),
        Pair(R.drawable.tenis, "Tenis"),
        Pair(R.drawable.laptop, "Laptop"),
        Pair(R.drawable.cable, "Cable"),
        Pair(R.drawable.gorra, "Gorra"),
        // Agrega más opciones aquí
    )

    val nivel3Opciones = listOf(
        Pair(R.drawable.martillo1, "¿Qué uso para construir una casa?"),
        Pair(R.drawable.escoba, "¿Qué uso para limpiar el suelo?"),
        Pair(R.drawable.carne1, "¿Cuál puedo cocinar en un sartén?"),
        Pair(R.drawable.pajaro, "¿Cuál vuela?"),
        Pair(R.drawable.abrigo, "¿Cuál me protege del frío?"),
        Pair(R.drawable.mochila, "¿Cuál me sirve para guardar libros?"),
        Pair(R.drawable.tijeras, "¿Cuál me sirve para cortar papel?"),
        Pair(R.drawable.raton, "¿Cuál me sirve para navegar en una computadora?"),
        Pair(R.drawable.television, "¿Cuál me sirve para ver las caricaturas?"),
        Pair(R.drawable.audifonos, "¿Cuál me sirve para escuchar música sin molestar a nadie?"),
        // Agrega más opciones aquí
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            GameScreenState.SelectLevel -> SelectLevelScreen { level ->
                currentScreen = when (level) {
                    1 -> GameScreenState.PlayLevel1
                    2 -> GameScreenState.PlayLevel2
                    3 -> GameScreenState.PlayLevel3
                    else -> GameScreenState.SelectLevel
                }
            }
            GameScreenState.PlayLevel1 -> GameScreen(
                imageOptions = nivel1Opciones,
                onGameFinish = { scoreResult ->
                    score = scoreResult
                    currentScreen = GameScreenState.CongratulationsLevel1
                }
            )
            GameScreenState.CongratulationsLevel1 -> CongratulationsScreen(
                score = score,
                onBackToMenu = { currentScreen = GameScreenState.SelectLevel }
            )
            GameScreenState.PlayLevel2 -> GameScreen(
                imageOptions = nivel2Opciones,
                onGameFinish = { scoreResult ->
                    score = scoreResult
                    currentScreen = GameScreenState.CongratulationsLevel2
                }
            )
            GameScreenState.CongratulationsLevel2 -> CongratulationsScreen(
                score = score,
                onBackToMenu = { currentScreen = GameScreenState.SelectLevel }
            )
            GameScreenState.PlayLevel3 -> GameScreen(
                imageOptions = nivel3Opciones,
                onGameFinish = { scoreResult ->
                    score = scoreResult
                    currentScreen = GameScreenState.CongratulationsLevel3
                }
            )
            GameScreenState.CongratulationsLevel3 -> CongratulationsScreen(
                score = score,
                onBackToMenu = { currentScreen = GameScreenState.SelectLevel }
            )
        }
    }
}

// Funciones composable para las pantallas de selección de nivel y felicitaciones

@Composable
fun SelectLevelScreen(onLevelSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona un Nivel", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { onLevelSelected(1) }) {
            Text("Nivel 1")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onLevelSelected(2) }) {
            Text("Nivel 2")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onLevelSelected(3) }) {
            Text("Nivel 3")
        }
    }
}

@Composable
fun CongratulationsScreen(score: Int, onBackToMenu: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Felicidades!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Tu puntaje: $score", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onBackToMenu) {
            Text("Volver al Menú Principal")
        }
    }
}

@Composable
fun GameScreen(
    imageOptions: List<Pair<Int, String>>,
    onGameFinish: (Int) -> Unit
) {
    var correctAnswers by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var currentOptions by remember { mutableStateOf<List<Pair<Int, String>>>(emptyList()) }
    var showSuccessScreen by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf("") }

    val currentCorrectOption = imageOptions[correctAnswers % imageOptions.size]

    LaunchedEffect(key1 = correctAnswers) {
        if (correctAnswers < imageOptions.size) {
            val incorrectOption = (imageOptions - currentCorrectOption).random()
            currentOptions = listOf(currentCorrectOption, incorrectOption).shuffled()
            feedbackMessage = "" // Limpia el mensaje de retroalimentación
        } else {
            showSuccessScreen = true
        }
    }

    if (showSuccessScreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "¡Completaste la actividad con éxito!\nTu puntaje final es: $score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            onGameFinish(score) // Llama al callback con el puntaje final
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Puntaje: $score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End).padding(end = 16.dp)
            )

            Text(
                text = "Selecciona la imagen correcta: ${currentCorrectOption.second}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            Text(
                text = feedbackMessage,
                fontSize = 16.sp,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )

            currentOptions.forEach { option ->
                Image(
                    painter = painterResource(id = option.first),
                    contentDescription = option.second,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            if (option == currentCorrectOption) {
                                score++
                                correctAnswers++
                                feedbackMessage = ""
                            } else {
                                score--
                                feedbackMessage = "¡Inténtalo de nuevo!"
                            }
                        }
                )
            }
        }
    }
}
