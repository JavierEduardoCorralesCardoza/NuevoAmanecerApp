package com.example.rompecabezas
import android.annotation.SuppressLint
import androidx.navigation.compose.composable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme


import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlin.math.pow

import kotlin.math.roundToInt
import kotlin.math.sqrt
import androidx.compose.foundation.clickable


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.padding

import android.media.MediaPlayer
import android.content.Context

import androidx.compose.ui.platform.LocalContext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.R


fun playSound(context: Context, soundId: Int) {
    MediaPlayer.create(context, soundId).apply {
        setOnCompletionListener { mp -> mp.release() }
        start()
    }
}

// Compute gridPositions within the LocalDensity block

data class Puzzle(val id: Int, @DrawableRes val fullImageResId: Int, val pieces: List<Int>)

val puzzles = listOf(
    Puzzle(1, R.drawable.p1, listOf(R.drawable.piece_1_1, R.drawable.piece_2_1,R.drawable.piece_3_1,R.drawable.piece_4_1,R.drawable.piece_5_1,R.drawable.piece_6_1,R.drawable.piece_7_1,R.drawable.piece_8_1,R.drawable.piece_9_1  )),
    Puzzle(4, R.drawable.p4, listOf(R.drawable.piece_1_4, R.drawable.piece_2_4,R.drawable.piece_3_4,R.drawable.piece_4_4,R.drawable.piece_5_4,R.drawable.piece_6_4,R.drawable.piece_7_4,R.drawable.piece_8_4,R.drawable.piece_9_4  )),
    Puzzle(2, R.drawable.p2, listOf(R.drawable.piece_1_2, R.drawable.piece_2_2,R.drawable.piece_3_2,R.drawable.piece_4_2,R.drawable.piece_5_2,R.drawable.piece_6_2,R.drawable.piece_7_2,R.drawable.piece_8_2,R.drawable.piece_9_2 )),
    Puzzle(3, R.drawable.p3, listOf(R.drawable.piece_1_3, R.drawable.piece_2_3,R.drawable.piece_3_3,R.drawable.piece_4_3,R.drawable.piece_5_3,R.drawable.piece_6_3,R.drawable.piece_7_3,R.drawable.piece_8_3,R.drawable.piece_9_3 )),
// ... Add all your puzzles here

)

@Composable
fun PuzzleSelectionScreen(navController: androidx.navigation.NavController) {

    // val context = LocalContext.current

    // Define your color and typography theme
    val typography = MaterialTheme.typography


    // You can use an image or a color for the background
    val backgroundImage = painterResource(id = R.drawable.fondo) // Replace with your image resource


    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop // This is to ensure the image covers the entire screen
        )

        // Your main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent) // Ensure background is transparent to show the image
        ) {

            Text(
                text = "Selecciona un rompecabezas",
                style = typography.headlineMedium, // Using Material3 typography
                color = MaterialTheme.colorScheme.onSurface, // Using Material3 color scheme
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Align text to the center horizontally
                    .padding(bottom = 16.dp)
            )

            // LazyColumn is used to render a list of items
            LazyColumn {
                items(puzzles) { puzzle ->
                    PuzzleListItem(puzzle = puzzle, onPuzzleSelected = { puzzleId ->
                        navController.navigate("puzzleBoard/$puzzleId")
                    })
                }
            }
        }
    }
}
@Composable
fun PuzzleListItem(puzzle: Puzzle, onPuzzleSelected: (Int) -> Unit) {
    // Represents a single item in the puzzle selection list
    Image(
        painter = painterResource(id = puzzle.fullImageResId),
        contentDescription = "Puzzle Image",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPuzzleSelected(puzzle.id) } // Clickable modifier to handle item selection
            .height(200.dp) // You can adjust the height as needed
    )
}

/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PuzzleApp()
            }
        }
    }
}*/

@Composable
fun Rompecabezas(navController: NavController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "puzzleSelection") {
        composable("puzzleSelection") {
            PuzzleSelectionScreen(navController)
        }

        composable("puzzleBoard/{puzzleId}", arguments = listOf(navArgument("puzzleId") { type = NavType.IntType })) { backStackEntry ->
            val puzzleId = backStackEntry.arguments?.getInt("puzzleId") ?: -1
            PuzzleBoard(puzzleId) // Pass the puzzleId to the PuzzleBoard function
        }
    }
}



@SuppressLint("UnrememberedMutableState")
@Composable
fun PuzzleBoard(puzzleId: Int) {
    val gridSize = 3 // 3x3 grid
    val screenHeight = LocalConfiguration.current.screenWidthDp.dp
    // Calcular el tamaño de la pieza basándote en la altura de la pantalla
    val pieceSize = screenHeight * 0.17f // Puedes ajustar el factor según tus necesidades
    val snapThresholdPx = with(LocalDensity.current) { 10.dp.toPx() }
    val puzzle = puzzles.find { it.id == puzzleId } ?: return

    // Define the grid positions
    val gridPositions = with(LocalDensity.current) {
        List(gridSize * gridSize) { index ->
            val row = index / gridSize
            val col = index % gridSize
            Offset(col * pieceSize.toPx(), row * pieceSize.toPx())
        }
    }

    // Create a shuffled list of indices for the initial positions
    val shuffledIndices = List(gridSize * gridSize) { it }.shuffled()

    // State for tracking occupied positions
    val occupiedPositions = remember { mutableStateListOf<Offset?>().apply { repeat(gridSize * gridSize) { add(null) } } }

    val gameComplete by derivedStateOf {
        occupiedPositions.zip(gridPositions).all { (occupied, correct) -> occupied == correct }
    }
    val frameImage = painterResource(id = R.drawable.frame)
    // Use a Box to fill the entire screen
    Box(
        contentAlignment = Alignment.Center, // This will center its children
        modifier = Modifier.fillMaxSize().background(Color.White) // The background color for the whole screen
            .background(Color.LightGray)
    ) {
        Image(
            painter = frameImage,
            contentDescription = "Frame",
            modifier = Modifier
                .fillMaxWidth(fraction = 1f) // Adjust the fraction to control the size
                .aspectRatio(0.7f) // Set the aspect ratio of the frame if it is square
                .align(Alignment.Center) // This will center the frame in the Box
                .offset(x = pieceSize*-0.18f)
        )

        if (gameComplete) {
            // Display the "You Win" message when the game is complete
            Text("¡Ganaste!", style = MaterialTheme.typography.headlineMedium)
        } else {
            // Display the puzzle board centered in the Box
            Box(
                // This inner Box contains the puzzle pieces
                modifier = Modifier
                    .size(gridSize * pieceSize) // Set the size of the puzzle board
                    .align(Alignment.Center) // Align the puzzle board to the center of the outer Box
            ) {
                puzzle.pieces.forEachIndexed { index, imageRes ->
                    DraggablePuzzlePiece(
                        imageResId = imageRes,
                        initialPosition = gridPositions[shuffledIndices[index]],
                        correctPosition = gridPositions[index],
                        gridSize = gridSize,
                        pieceSize = pieceSize,
                        occupiedPositions = occupiedPositions,
                        index = index,
                        snapThresholdPx = snapThresholdPx,
                        gridPositions = gridPositions
                    )
                }
            }
        }
    }
}


@Composable
fun DraggablePuzzlePiece(
    imageResId: Int,
    initialPosition: Offset,
    correctPosition: Offset,
    gridSize: Int,
    pieceSize: Dp,
    occupiedPositions: MutableList<Offset?>,
    index: Int,
    snapThresholdPx: Float,
    gridPositions: List<Offset>,

    ) {
    var position by remember { mutableStateOf(initialPosition) }

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Puzzle Piece",
        modifier = Modifier
            .size(pieceSize)
            .offset { IntOffset(position.x.roundToInt(), position.y.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->

                    val newPosition = position + dragAmount
                    val closestGridPosition = gridPositions.minByOrNull { gridPosition -> gridPosition.getDistanceTo(newPosition) }

                    if (closestGridPosition != null) {
                        val distanceToClosestPx = closestGridPosition.getDistanceTo(newPosition)
                        if (distanceToClosestPx < snapThresholdPx) {
                            position = closestGridPosition
                            if (closestGridPosition == correctPosition) {
                                occupiedPositions[index] = position
                            } else {
                                occupiedPositions[index] = null
                            }
                        } else {
                            position = newPosition
                        }
                    }
                }
            }

    )
}

private fun Offset.getDistanceTo(other: Offset): Float {
    return sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
}
// List of correct positions for the puzzle pieces
val correctPositions = listOf(
    Offset(0f, 0f),
    Offset(1f, 0f),
    Offset(2f, 0f),
    Offset(0f, 1f),
    Offset(1f, 1f),
    Offset(2f, 1f),
    Offset(0f, 2f),
    Offset(1f, 2f),
    Offset(2f, 2f),
)



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        //  PuzzleBoard() // Directly calling PuzzleBoard to show in the preview
    }
}

