import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.Diferencias
import androidx.compose.ui.res.painterResource
import com.example.proyectonuevoamanecer.screens.juegos.encontrar_diferencias.GridOfButtons


@Composable
fun ImageCard(diferenciaImg: Diferencias) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = Color.Red,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),

                ) {
                    val img1 = painterResource(id = diferenciaImg.image1)
                    Image(painter = img1, contentDescription = "Encuentra las diferencias",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(),
                        alignment = Alignment.Center
                    )
                }
            }
            Surface(
                color = Color.Red,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(0.dp),
                ) {
                    val img2 = painterResource(id = diferenciaImg.image2)
                    Image(painter = img2, contentDescription = "Encuentra las diferencias",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(),
                        alignment = Alignment.Center

                    )
                    GridOfButtons(diferenciaImg.differences)
                }
            }


            Column(modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = diferenciaImg.differences.size.toString(), style = MaterialTheme.typography.bodyMedium)
                Text(text = diferenciaImg.name, style = MaterialTheme.typography.headlineMedium)
                //Text(text = diferenciaImg.hp, style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}

@Composable
@Preview
fun CardPreview() {
    // Define a preview card with sample data
    val samplePokemon = Diferencias(R.drawable.diff_image1, R.drawable.diff_image2, "Cavernicolas", 8, differences = mutableListOf<Int>(5, 10, 15))
    ImageCard(diferenciaImg = samplePokemon)
}
