package com.example.proyectonuevoamanecer.screens.juegos.encontrar_diferencias
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.io.Console

@Composable
fun GridOfButtons(activeBoxNumbers: MutableList<Int>) {
    var isBoxClicked by remember { mutableStateOf(false)}

    LazyVerticalGrid(
        columns = GridCells.Fixed(11),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(66) { it ->

            Box(
                modifier = Modifier
                    .size(30.dp, 35.dp)
                    .clickable {
                       if(activeBoxNumbers.contains(it)) {
                            activeBoxNumbers.remove(it)
                            isBoxClicked = !isBoxClicked
                        }

                        println("Box $it clicked!")
                    }
                    .background(if (isBoxClicked) Color.Red.copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.2f))
            ) {
                Text(text = "$it")
            }
        }
    }

}




@Preview
@Composable
fun PreviewGridOfButtons() {
    var activeBoxNumbers by remember { mutableStateOf(mutableListOf<Int>(5, 10, 15)) }
    GridOfButtons(activeBoxNumbers)
}

