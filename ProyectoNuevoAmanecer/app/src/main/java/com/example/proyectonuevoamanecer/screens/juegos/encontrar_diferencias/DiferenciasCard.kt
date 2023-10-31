import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.Diferencias
import androidx.compose.ui.res.painterResource


@Composable
fun ImageCard(diferenciaImg: Diferencias) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(15.dp)
                    .size(300.dp)
            ) {
                val img1 = painterResource(id = diferenciaImg.image1)
                Image(painter = img1, contentDescription = "Encuentra las diferencias")
            }

            Column(modifier = Modifier.padding(4.dp)) {
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
    val samplePokemon = Diferencias(R.drawable.diff_image1, R.drawable.diff_image2, "Cavernicolas", 8)
    ImageCard(diferenciaImg = samplePokemon)
}
