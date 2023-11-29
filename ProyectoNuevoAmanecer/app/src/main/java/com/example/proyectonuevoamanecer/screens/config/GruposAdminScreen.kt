package com.example.proyectonuevoamanecer.screens.config

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectonuevoamanecer.api.GrupoAPI
import com.example.proyectonuevoamanecer.api.llamarApi
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Grupo
import com.example.proyectonuevoamanecer.databases.Miembro
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.databases.obtenerDatos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.UUID

class GruposViewModel : ViewModel() {
    val miembrosSeleccionados = mutableStateListOf<Pair<String, String>>()
    fun seleccionarMiembro(miembroItem: Pair<String, String>) {
        if (miembrosSeleccionados.contains(miembroItem)) {
            miembrosSeleccionados.remove(miembroItem)
        } else {
            miembrosSeleccionados.add(miembroItem)
        }
    }
    fun limpiarMiembros(){
        miembrosSeleccionados.clear()
    }
}
@Composable
fun Grupos() {
    val viewModel: GruposViewModel = viewModel()
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var grupos by remember { mutableStateOf<JSONArray?>(null) }
    var miembros by remember { mutableStateOf<JSONArray?>(null) }
    var activado by remember { mutableStateOf(false) }
    var clave by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var gameMaster by remember { mutableStateOf("") }
    val grupoParaEditar = remember { mutableStateOf<GrupoAPI?>(null) }
    LaunchedEffect(activado){
        withContext(Dispatchers.IO){usuarioActivo = repositorio.getUsuarioActivo()}
        grupos = obtenerDatos(context,"Grupo")
        miembros = obtenerDatos(context,"Miembros")
    }

    val tabs = listOf("Crear grupo", "Editar grupos")
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }
        when (selectedTab) {
            0 -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = clave,
                        onValueChange = {
                            if (it.length <= 9)
                                clave = it },
                        label = { Text("Clave") },
                        trailingIcon = {
                            IconButton(onClick = { clave = UUID.randomUUID().toString().take(9) }) {
                                Icon(Icons.Default.Home, contentDescription = "Generar clave")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = nombre,
                        onValueChange = {
                            if (it.length <= 20)
                                nombre = it },
                        label = { Text("Nombre") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = gameMaster,
                        onValueChange = {
                            if (it.length <= 20)
                                gameMaster = it },
                        label = { Text("GameMaster") }
                    )
                    Divider(color = Color.Gray, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        val data = mapOf("id" to clave, "nombre" to nombre, "gamemaster" to gameMaster)
                        scope.launch { withContext(Dispatchers.IO){
                            llamarApi("grupo",data,"POST")
                            repositorio.insertGrupo(Grupo(clave,nombre,gameMaster)) } }
                        activado = !activado
                    }) {
                        Text("Crear")
                    }
                }
            }
            1 -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (grupoParaEditar.value == null) {
                        LazyColumn {
                            items(grupos?.length() ?: 0) { index ->
                                val grupoItem = grupos?.getJSONObject(index)?.let { GrupoAPI(it) }
                                ListItem(
                                    headlineContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.AccountCircle, contentDescription = "User Icon", Modifier.padding(6.dp))
                                            Text(text = grupoItem?.Nombre ?: "") }
                                    },
                                    supportingContent = { Text(text = "Modificar grupo") },
                                    trailingContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically){
                                            IconButton(onClick ={grupoParaEditar.value = grupoItem }, Modifier.padding(6.dp)){
                                                Icon(
                                                    Icons.Default.Create, contentDescription = "Edit")
                                            }
                                            IconButton(onClick ={
                                                scope.launch {
                                                    if (grupoItem != null) {
                                                        borrarGrupo(grupoItem,repositorio)
                                                    } }
                                                activado = !activado
                                            }){ Icon(Icons.Default.Delete, contentDescription = "Delete") }
                                        }
                                    }
                                )
                            }
                        }
                    }
                    else{
                        EditarGrupoScreen(context,scope,viewModel,repositorio,grupoParaEditar.value) { grupoParaEditar.value = null }
                    }
                }
            }
        }
    }
}

@Composable
fun EditarGrupoScreen(context: Context, scope: CoroutineScope, viewModel: GruposViewModel, repositorio: Repositorio, grupo: GrupoAPI?, onDoneEditing: () -> Unit) {
    var clave by remember { mutableStateOf(grupo?.Id ?: "") }
    var nombre by remember { mutableStateOf(grupo?.Nombre ?: "") }
    var gameMaster by remember { mutableStateOf(grupo?.Gamemaster ?: "") }
    var mostrarMiembros by remember { mutableStateOf(false) }
    var miembrosGrupo by remember { mutableStateOf<JSONArray?>(null) }
    var grupos by remember { mutableStateOf<JSONArray?>(null) }
    val activado by remember { mutableStateOf(false) }
    LaunchedEffect(activado){
        withContext(Dispatchers.IO){
            viewModel.limpiarMiembros()
            grupos = obtenerDatos(context,"Grupo", mapOf("id" to clave))
            miembrosGrupo = obtenerDatos(context, "Miembros", mapOf("id_grupo" to clave))
            val gruposLength = grupos?.length() ?: 0
            miembrosGrupo?.length() ?: 0
            for (i in 0 until gruposLength) {
                grupos?.getJSONObject(i)?.getString("ID") ?: ""
                for (j in 0 until gruposLength) {
                    val idMiembroGrupo = if (miembrosGrupo != null && miembrosGrupo!!.length() > j) {
                        miembrosGrupo!!.getJSONObject(j).getString("ID_Usuario")
                    } else {
                        ""
                    }
                    val datos = obtenerDatos(context,"usuario", mapOf("id" to idMiembroGrupo))
                    if (datos != null && datos.length() > 0) {
                        val nombreMiembro = datos.getJSONObject(0)
                        if (idMiembroGrupo == nombreMiembro?.getString("ID")) {
                            if (nombreMiembro != null) {
                                viewModel.seleccionarMiembro(Pair(nombreMiembro.getString("Nombre"), idMiembroGrupo))
                            }
                        }
                    }
                }
            }
        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = clave,
            onValueChange = {
                if (it.length <= 9)
                    clave = it },
            label = { Text("Clave") },
            trailingIcon = {
                IconButton(onClick = { clave = UUID.randomUUID().toString().take(9) }) {
                    Icon(Icons.Default.Home, contentDescription = "Generar clave")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nombre,
            onValueChange = {
                if (it.length <= 20)
                    nombre = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = gameMaster,
            onValueChange = {
                if (it.length <= 9)
                    gameMaster = it },
            label = { Text("GameMaster") }
        )
        Divider(color = Color.Gray, thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.align(Alignment.CenterHorizontally)){
            TextButton(onClick = { mostrarMiembros = !mostrarMiembros }) {
                Text(text = "Ver Miembros")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Modificar Miembros")
            }
        }
        var todosLosMiembros by remember { mutableStateOf<List<Pair<String,String>>>(emptyList()) }
        LaunchedEffect(true){
            withContext(Dispatchers.IO){ grupo?.Id?.let { todosLosMiembros = obtenerMiembrosDeGrupo(context, it) } }}
        if (mostrarMiembros) {
            Column {
                Text("Miembros", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
                LazyColumn {
                    items(todosLosMiembros.size) { index ->
                        val miembroItem = todosLosMiembros[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { viewModel.seleccionarMiembro(miembroItem) },
                            colors = CardDefaults.cardColors(Color.LightGray)
                        ) {
                            ListItem(
                                headlineContent = { Text(miembroItem.second) },
                                trailingContent = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Settings, contentDescription = "Configuracion")
                                        Checkbox(checked = viewModel.miembrosSeleccionados.contains(miembroItem), onCheckedChange = null)
                                    }
                                     }
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val data = mapOf("id" to clave, "nombre" to nombre, "gamemaster" to gameMaster)
            scope.launch{
                withContext(Dispatchers.IO){
                    llamarApi("grupo",data,"PUT", mapOf("id" to grupo?.Id))
                    repositorio.getGrupo(mapOf("id" to grupo?.Id))
                        ?.let { repositorio.deleteGrupo(it) }
                    repositorio.insertGrupo(Grupo(clave,nombre,gameMaster))

                    // Obtén la lista actual de miembros del grupo
                    val miembrosActuales = grupo?.Id?.let { obtenerMiembrosDeGrupo(context, it) }

                    // Actualiza a los miembros con la configuración habilitada
                    for (miembro in viewModel.miembrosSeleccionados) {
                        if (!miembrosActuales?.contains(miembro)!!) {
                            updateMiembroEnGrupo(repositorio, grupo.Id, miembro)
                        }
                    }
                }
            }
            onDoneEditing()
        }) {
            Text("Actualizar")
        }
    }
}
suspend fun obtenerMiembrosDeGrupo(context: Context, id: String): List<Pair<String,String>> {
    val resultado = mutableListOf<Pair<String,String>>()
    withContext(Dispatchers.IO) {
        val miembrosDatos = obtenerDatos(context, "Miembros", mapOf("id_grupo" to id))
        val miembrosDatosLength = miembrosDatos?.length() ?: 0

        for (i in 0 until miembrosDatosLength) {
            val idMiembro = miembrosDatos?.getJSONObject(i)?.getString("ID_Usuario")
            val usuario = obtenerDatos(context, "Usuario", mapOf("id" to idMiembro))
            for (j in 0 until (usuario?.length() ?: 0)) {
                usuario?.getJSONObject(j)?.let { resultado.add(Pair(it.getString("ID"), it.getString("Nombre"))) }
            }
        }
    }
    return resultado
}

suspend fun updateMiembroEnGrupo( repositorio: Repositorio, id: String, miembro: Pair<String,String>){
    withContext(Dispatchers.IO){
        println(miembro.second)
        llamarApi("miembros", mapOf("id_grupo" to id, "id_usuario" to miembro.second, "configuracion" to true),"PUT",
            mapOf("id_usuario" to miembro.second, "id_grupo" to id))
        repositorio.insertMiembro(Miembro(id,miembro.second,true))
    }
}

suspend fun borrarGrupo(grupoItem: GrupoAPI, repositorio: Repositorio) {
    withContext(Dispatchers.IO){
        llamarApi("historial", emptyMap(), "DELETE",mapOf("id_grupo" to grupoItem.Id))
        llamarApi("miembros", emptyMap(), "DELETE",mapOf("id_grupo" to grupoItem.Id))
        llamarApi("grupo", emptyMap(), "DELETE",mapOf("id" to grupoItem.Id))
        repositorio.getHistorial(mapOf("id_grupo" to grupoItem.Id))
            ?.let { repositorio.deleteHistorial(it) }
        repositorio.getMiembro(mapOf("id_grupo" to grupoItem.Id))
            ?.let { repositorio.deleteMiembro(it) }
        repositorio.getUsuario(mapOf("id" to grupoItem.Id))
            ?.let { repositorio.deleteUsuario(it) }
    }
}