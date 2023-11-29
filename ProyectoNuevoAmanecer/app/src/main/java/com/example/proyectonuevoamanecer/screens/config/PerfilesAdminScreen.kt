package com.example.proyectonuevoamanecer.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.databases.obtenerDatos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

@Composable
fun Perfiles() {
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var usuarios by remember { mutableStateOf<JSONArray?>(null) }
    var usuario by remember { mutableStateOf<UsuarioAPI?>(null) }
    var perfil by remember { mutableStateOf<UsuarioAPI?>(null) }
    val activado by remember { mutableStateOf(false) }
    LaunchedEffect(activado){
        withContext(Dispatchers.IO){usuarioActivo = repositorio.getUsuarioActivo()}
        usuarios = obtenerDatos(context,"Usuario")
        usuario = obtenerDatos(context,"Usuario", mapOf("id" to "${usuarioActivo?.id_usuario}"))?.getJSONObject(0)
            ?.let { UsuarioAPI(it) }
        perfil = usuario
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth()) {
                Icon(
                    Icons.Default.AccountCircle, contentDescription = "User Icon", modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally))
                Text(text = perfil?.Nombre ?: "", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.align(
                    Alignment.CenterHorizontally))
            }
            if (perfil != usuario) {
                IconButton(
                    onClick = { perfil = usuario },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Home Icon")
                }
            }
        }
        Divider(color = Color.Gray, thickness = 2.dp)
        // Aquí va la sección de información del perfil
        Box(modifier = Modifier.fillMaxHeight(0.3f)) {
            Text(text = "EJEMPLO", style = MaterialTheme.typography.displayLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 2.dp)
        LazyColumn {
            items(usuarios?.length() ?: 0) { index ->
                val usuarioItem = usuarios?.getJSONObject(index)?.let { UsuarioAPI(it) }
                ListItem(
                    headlineContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "User Icon")
                            Text(text = usuarioItem?.Nombre ?: "", Modifier.padding(6.dp))
                        }
                    },
                    supportingContent = { Text(text = "Ver perfil") },
                    modifier = Modifier.clickable { perfil = usuarioItem }
                )
            }
        }
    }
}