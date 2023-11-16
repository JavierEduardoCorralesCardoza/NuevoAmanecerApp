package com.example.proyectonuevoamanecer.screens.juegos.rompecabezas

import androidx.navigation.NavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.proyectonuevoamanecer.R
import kotlin.math.pow

import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun Rompecabezas(navController: NavController) {
    PuzzleBoard()
}

val pieceSize = 150.dp // Assuming each piece is 100.dp
val correctPositions = listOf(
    // First row
    Offset(0f, 0f),
    Offset(100f, 0f),
    Offset(200f, 0f),
    Offset(300f, 0f),
    // Second row
    Offset(0f, 100f),
    Offset(100f, 100f),
    Offset(200f, 100f),
    Offset(300f, 100f),
    // Third row
    Offset(0f, 200f),
    Offset(100f, 200f),
    Offset(200f, 200f),
    Offset(300f, 200f),
    // Fourth row
    Offset(0f, 300f),
    Offset(100f, 300f),
    Offset(200f, 300f),
    Offset(300f, 300f)
)

//val pieceSizePx = with(LocalDensity.current) { pieceSize.toPx() }
val snapThreshold = 30.dp // or whatever value is appropriate


val gridSize = 3 // 4x4 grid


// Compute gridPositions within the LocalDensity block


val puzzleAreaSize = gridSize * pieceSize // Assuming each piece is 100.dp and it's a 4x4 puzzle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Use Surface for handling background colors
                Surface(modifier = Modifier.fillMaxSize()) {
                    PuzzleBackground {
                        PuzzleBoard()
                    }
                }
            }
        }
    }
}

@Composable
fun PuzzleBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.LightGray), // Set the background color here
        contentAlignment = Alignment.Center
    ) {
        // This is your puzzle assembly area
        Box(
            modifier = Modifier
                .size(puzzleAreaSize)
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ) {
            content()
        }
    }
}
@Composable
fun PuzzleBoard() {
    val localDensity = LocalDensity.current // correct use within composable
    val gridSize = 4// 4x4 grid
    val pieceSize = 150.dp // Assuming each piece is 100.dp
    // Convert the snap threshold to pixels
    val snapThresholdPx = with(localDensity) { snapThreshold.toPx() }
    // Calculate grid positions in pixels within the local density scope
    val gridPositions = with(LocalDensity.current) {
        List(gridSize * gridSize) { index ->
            val row = index / gridSize
            val col = index % gridSize
            Offset(col * pieceSize.toPx(), row * pieceSize.toPx())
        }
    }

    val imagePieces = listOf(
        // Replace with your actual drawable resource IDs
        R.drawable.pigeon_piece1,
        R.drawable.pigeon_piece2,
        R.drawable.pigeon_piece3,
        R.drawable.pigeon_piece4,
        R.drawable.pigeon_piece5,
        R.drawable.pigeon_piece6,
        R.drawable.pigeon_piece7,
        R.drawable.pigeon_piece8,
        R.drawable.pigeon_piece9
    )

    // This state list tracks which positions are occupied
    val shuffledInitialPositions = remember { correctPositions.shuffled() }
    val occupiedPositions = remember { mutableStateListOf<Offset?>().apply { addAll(List(imagePieces.size) { null }) } }

    Box(
        modifier = Modifier
            .size(pieceSize)
            .background(Color.White), // Background color for the puzzle area
        contentAlignment = Alignment.TopStart
    ) {
        // Pass gridPositions to each DraggablePuzzlePiece
        imagePieces.forEachIndexed { index, imageRes ->
            DraggablePuzzlePiece(
                imageResId = imageRes,
                initialPosition = gridPositions[index],
                correctPosition = correctPositions[index],
                gridSize = gridSize,
                pieceSize = pieceSize,
                occupiedPositions = occupiedPositions,
                index = index,
                snapThresholdPx = snapThresholdPx,
                gridPositions = gridPositions, // Pass gridPositions here

            )
        }
    }
}@Composable
fun DraggablePuzzlePiece(
    imageResId: Int,
    initialPosition: Offset,
    correctPosition: Offset,
    gridSize: Int,
    pieceSize: Dp,
    occupiedPositions: MutableList<Offset?>,
    index: Int,
    snapThresholdPx: Float,
    gridPositions: List<Offset>
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
                    change.consumeAllChanges()
                    var newPosition = position + dragAmount

                    // Find the closest grid position
                    val closestGridPosition = gridPositions.minByOrNull { it.distanceTo(newPosition) }
                    if (closestGridPosition != null) {
                        val distanceToClosest = newPosition.distanceTo(closestGridPosition)

                        // Check if the piece should snap to the closest grid position
                        if (distanceToClosest < snapThresholdPx && !occupiedPositions.contains(closestGridPosition)) {
                            newPosition = closestGridPosition
                        }
                    }

                    // Update the position state
                    position = newPosition

                    // Optionally, update the occupiedPositions list to track which slots are filled
                    if (position == correctPosition) {
                        occupiedPositions[index] = position
                    } else {
                        occupiedPositions[index] = null
                    }
                }
            }
    )
}

// Extension function to calculate distance between two Offsets
fun Offset.distanceTo(other: Offset): Float {
    return sqrt((this.x - other.x).pow(2) + (this.y - other.y).pow(2))
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        PuzzleBoard() // Directly calling PuzzleBoard to show in the preview
    }
}

@Composable
fun PuzzleAppTheme(content: () -> Unit) {

}
