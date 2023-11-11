import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectonuevoamanecer.R
import com.example.proyectonuevoamanecer.clases.Diferencias
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.clases.BotonDiferencias
import com.example.proyectonuevoamanecer.screens.juegos.encontrar_diferencias.GridOfButtons


@Composable
fun DiferenciasCard(navController: NavController){
    val samplePokemon = Diferencias(
        R.drawable.diff_image1,
        R.drawable.diff_image2,
        "Cavernicolas",
        differenceNumber = 8 ,
        differences = mutableListOf<BotonDiferencias>(
            BotonDiferencias(posX = 210, posY = 50),
            BotonDiferencias(posX = 30, posY = 65),
            BotonDiferencias(posX = 90, posY = 100),
            BotonDiferencias(posX = 285, posY = 90),
            BotonDiferencias(posX = 20, posY = 120),
            BotonDiferencias(posX = 135, posY = 120),
            BotonDiferencias(posX = 250, posY = 115),
            BotonDiferencias(posX = 210, posY = 135, sizeX = 50, sizeY = 50),

            ))
    ImageCard(diferenciaImg = samplePokemon)
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ImageCard(diferenciaImg: Diferencias) {
    var listSize by remember { mutableStateOf(diferenciaImg.differences.size) }
    Card(
        modifier = Modifier
            .padding(4.dp),

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
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(),
                        alignment = Alignment.Center
                    )
                }
            }
            Surface(
                color = Color.Red,

                modifier = Modifier
                    .padding(6.dp),

                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            ){
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {
                    val img2 = painterResource(id = diferenciaImg.image2)
                    Image(painter = img2, contentDescription = "Encuentra las diferencias",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(),
                        alignment = Alignment.Center

                    )
                    //GridOfButtons(diferenciaImg.differences, diferenciaImg.bigDifferences)
                }
                var botonesPresionados by remember { mutableStateOf(List(8) { false }) }

                for (i in 0..7){
                    Button(onClick = {
                        if (!botonesPresionados[i] && listSize > 0) {
                            botonesPresionados = botonesPresionados.toMutableList().apply { set(i, true) }
                            listSize--
                        }
                    },
                        modifier = Modifier
                            .absoluteOffset(
                                diferenciaImg.differences[i].posX.dp,
                                diferenciaImg.differences[i].posY.dp
                            )
                            .size(
                                diferenciaImg.differences[i].siseX.dp,
                                diferenciaImg.differences[i].siseY.dp
                            )
                            .alpha(0.5f)
                    ) {

                    }
                }
                }
            Column(modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text("Diferencias restantes: $listSize", style = MaterialTheme.typography.bodyMedium)
                Text(text = diferenciaImg.name, style = MaterialTheme.typography.headlineMedium)
                //Text(text = diferenciaImg.hp, style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}


