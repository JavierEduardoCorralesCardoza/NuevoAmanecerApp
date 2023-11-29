package com.example.proyectonuevoamanecer.screens.config

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.api.llamarApi
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Miembro
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.Usuario
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.databases.obtenerDatos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.util.UUID

class UsuariosViewModel : ViewModel() {
    val gruposSeleccionados = mutableStateListOf<Pair<String, String>>()

    fun seleccionarGrupo(grupoItem: Pair<String, String>) {
        if (gruposSeleccionados.contains(grupoItem)) {
            gruposSeleccionados.remove(grupoItem)
        } else {
            gruposSeleccionados.add(grupoItem)
        }
    }
}
@Composable
fun Administracion(navController: NavController){
    AdministracionTabs()
}
@Composable
fun AdministracionTabs(){
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Perfiles", "Usuarios","Grupos", "Juegos")
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Perfiles")
                            1 -> Icon(imageVector = Icons.Default.Face, contentDescription = "Usuarios")
                            2 -> Icon(imageVector = Icons.Filled.Info, contentDescription = "Grupos")
                            3 -> Icon(imageVector = Icons.Filled.Settings, contentDescription = "Juegos")
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Perfiles()
            1 -> Usuarios()
            2 -> Grupos()
            3 -> Juegos()
        }
        Divider()
    }
}
@Composable
fun Perfiles() {
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var usuarios by remember { mutableStateOf<JSONArray?>(null)}
    var usuario by remember { mutableStateOf<UsuarioAPI?>(null)}
    var perfil by remember { mutableStateOf<UsuarioAPI?>(null)}
    var activado by remember { mutableStateOf(false) }
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
                Icon(Icons.Default.AccountCircle, contentDescription = "User Icon", modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally))
                Text(text = perfil?.Nombre ?: "", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
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
                            Text(text = usuarioItem?.Nombre ?: "", Modifier.padding(6.dp))}
                        },
                    supportingContent = { Text(text = "Ver perfil")},
                    modifier = Modifier.clickable { perfil = usuarioItem }
                )
            }
        }
    }
}

@Composable
fun Usuarios() {
    val viewModel: UsuariosViewModel = viewModel()
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var usuarios by remember { mutableStateOf<JSONArray?>(null)}
    var usuario by remember { mutableStateOf<UsuarioAPI?>(null)}
    var miembros by remember { mutableStateOf<JSONArray?>(null)}
    var grupos by remember { mutableStateOf<JSONArray?>(null)}
    var activado by remember { mutableStateOf(false) }
    var clave by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var isAdministrador by remember { mutableStateOf(false) }
    var mostrarGrupos by remember { mutableStateOf(false) }
    val usuarioParaEditar = remember { mutableStateOf<UsuarioAPI?>(null) }
    LaunchedEffect(activado){
        withContext(Dispatchers.IO){usuarioActivo = repositorio.getUsuarioActivo()}
        usuarios = obtenerDatos(context,"Usuario")
        miembros = obtenerDatos(context,"Miembros")
        grupos = obtenerDatos(context,"Grupo")
    }

    val tabs = listOf("Crear usuario", "Editar usuario")
    var selectedTab by remember { mutableStateOf(0) }

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
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text("Administrador")
                        Checkbox(
                            checked = isAdministrador,
                            onCheckedChange = { isAdministrador = it }
                        )
                    }
                    Divider(color = Color.Gray, thickness = 2.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.align(Alignment.CenterHorizontally)){
                        TextButton(onClick = { mostrarGrupos = !mostrarGrupos }) {
                            Text(text = "Añadir a Grupo")
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Añadir Grupos")
                        }
                    }
                    if (mostrarGrupos) {
                        Box(modifier = Modifier.fillMaxHeight(0.7f)) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(grupos?.length() ?: 0) { index ->
                                    val grupoItem = Pair(
                                        grupos?.getJSONObject(index)?.getString("Nombre") ?: "",
                                        grupos?.getJSONObject(index)?.getString("ID") ?: ""
                                    )
                                    val isSelected = viewModel.gruposSeleccionados.contains(grupoItem)
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clickable { viewModel.seleccionarGrupo(grupoItem) },
                                        colors = CardDefaults.cardColors((if (isSelected) Color.LightGray else Color.White))
                                    ) {
                                        ListItem(
                                            headlineContent = { Text(grupoItem.first) },
                                            leadingContent = { Checkbox(checked = isSelected, onCheckedChange = null) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        var data = mapOf("id" to "$clave", "nombre" to "$nombre", "administrador" to isAdministrador)
                        scope.launch { withContext(Dispatchers.IO){
                            llamarApi("usuario",data,"POST")
                            repositorio.insertUsuario(Usuario(clave,nombre,isAdministrador))
                            viewModel.gruposSeleccionados.forEach { grupoSeleccionado ->
                                data = mapOf("id_grupo" to grupoSeleccionado.second, "id_usuario" to "$clave","configuracion" to false)
                                llamarApi("miembros",data,"POST")
                                repositorio.insertMiembro(Miembro(grupoSeleccionado.second,clave,false))
                            }
                        } }
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
                    if (usuarioParaEditar.value == null) {
                        LazyColumn {
                            items(usuarios?.length() ?: 0) { index ->
                                val usuarioItem = usuarios?.getJSONObject(index)?.let { UsuarioAPI(it) }
                                ListItem(
                                    headlineContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.AccountCircle, contentDescription = "User Icon", Modifier.padding(6.dp))
                                            Text(text = usuarioItem?.Nombre ?: "") }
                                    },
                                    supportingContent = { Text(text = "Ver perfil")},
                                    trailingContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically){
                                            IconButton(onClick ={usuarioParaEditar.value = usuarioItem }, Modifier.padding(6.dp)){Icon(Icons.Default.Create, contentDescription = "Edit")}
                                            IconButton(onClick ={/*TODO*/}){Icon(Icons.Default.Delete, contentDescription = "Delete")}}}
                                )
                            }
                        }
                    }
                    else{
                        EditarUsuarioScreen(context,scope,viewModel,repositorio,usuarioParaEditar.value) { usuarioParaEditar.value = null }
                    }
                }
            }
        }
    }
}

@Composable
fun EditarUsuarioScreen(context: Context,scope: CoroutineScope,viewModel: UsuariosViewModel,repositorio: Repositorio,usuario: UsuarioAPI?, onDoneEditing: () -> Unit) {
    var clave by remember { mutableStateOf(usuario?.Id ?: "") }
    var nombre by remember { mutableStateOf(usuario?.Nombre ?: "") }
    var isAdministrador by remember { mutableStateOf(usuario?.Admin ?: false) }
    var mostrarGrupos by remember { mutableStateOf(false) }
    var gruposMiembro by remember { mutableStateOf<JSONArray?>(null)}
    var grupos by remember { mutableStateOf<JSONArray?>(null)}
    LaunchedEffect(true){
        withContext(Dispatchers.IO){
            gruposMiembro = obtenerDatos(context,"Miembros", mapOf("id_usuario" to clave))
            grupos = obtenerDatos(context, "Grupo")
            val gruposMiembroLength = gruposMiembro?.length() ?: 0
            val gruposLength = grupos?.length() ?: 0
            for (i in 0 until gruposMiembroLength) {
                val idGrupoMiembro = gruposMiembro?.getJSONObject(i)?.getString("ID_Grupo") ?: ""
                for (j in 0 until gruposLength) {
                    val idGrupo = grupos?.getJSONObject(j)?.getString("ID") ?: ""
                    val nombreGrupo = grupos?.getJSONObject(j)?.getString("Nombre") ?: ""
                    if (idGrupoMiembro.toString() == idGrupo.toString()) {
                        viewModel.seleccionarGrupo(Pair(nombreGrupo, idGrupo))
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
        Row(verticalAlignment = Alignment.CenterVertically){
            Text("Administrador")
            Checkbox(
                checked = isAdministrador,
                onCheckedChange = { isAdministrador = it }
            )
        }
        Divider(color = Color.Gray, thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.align(Alignment.CenterHorizontally)){
            TextButton(onClick = { mostrarGrupos = !mostrarGrupos }) {
                Text(text = "Añadir a Grupo")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Añadir Grupos")
            }
        }
        if (mostrarGrupos) {
            Row(Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight(0.7f)) {
                    Column {
                        Text("Miembro", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
                        LazyColumn {
                            items(viewModel.gruposSeleccionados.size) { index ->
                                val grupoItem = viewModel.gruposSeleccionados[index]
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable { viewModel.seleccionarGrupo(grupoItem) },
                                    colors = CardDefaults.cardColors(Color.LightGray)
                                ) {
                                    ListItem(
                                        headlineContent = { Text(grupoItem.first) },
                                        leadingContent = { Checkbox(checked = true, onCheckedChange = null) }
                                    )
                                }
                            }
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f).fillMaxHeight(0.7f)) {
                    Column {
                        Text("Grupos", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.CenterHorizontally))
                        LazyColumn {
                            items(grupos?.length() ?: 0) { index ->
                                val grupoItem = Pair(
                                    grupos?.getJSONObject(index)?.getString("Nombre") ?: "",
                                    grupos?.getJSONObject(index)?.getString("ID") ?: ""
                                )
                                if (!viewModel.gruposSeleccionados.contains(grupoItem)) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clickable { viewModel.seleccionarGrupo(grupoItem) },
                                        colors = CardDefaults.cardColors(Color.White)
                                    ) { ListItem(headlineContent = { Text(grupoItem.first) }, leadingContent = { Checkbox(checked = false, onCheckedChange = null) })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            var data = mapOf("id" to "$clave", "nombre" to "$nombre", "administrador" to isAdministrador)
            scope.launch{
                withContext(Dispatchers.IO){
                    llamarApi("usuario",data,"PUT", mapOf("id" to usuario?.Id))
                    repositorio.getUsuario(mapOf("id" to usuario?.Id))
                        ?.let { repositorio.deleteUsuario(it) }
                    repositorio.insertUsuario(Usuario(clave,nombre,isAdministrador))
/*
                    // Obtén la lista actual de grupos del usuario
                    val gruposActuales = obtenerGruposDeUsuario(usuario?.Id)

                    // Inserta al usuario en los nuevos grupos
                    for (grupo in viewModel.gruposSeleccionados) {
                        if (!gruposActuales.contains(grupo)) {
                            insertarUsuarioEnGrupo(usuario?.Id, grupo)
                        }
                    }

                    // Elimina al usuario de los grupos de los que ya no es miembro
                    for (grupo in gruposActuales) {
                        if (!viewModel.gruposSeleccionados.contains(grupo)) {
                            eliminarUsuarioDeGrupo(usuario?.Id, grupo)
                        }
                    }*/
                }
            }
            onDoneEditing()
        }) {
            Text("Actualizar")
        }
    }
}

@Composable
fun borrarUsuario(usuarioItem: UsuarioAPI, scope: CoroutineScope, repositorio: Repositorio) {
    LaunchedEffect(true) { withContext(Dispatchers.IO){
        llamarApi("historial", mapOf("id_jugador" to usuarioItem.Id), "DELETE")
        llamarApi("miembros", mapOf("id_usuario" to usuarioItem.Id), "DELETE")
        llamarApi("usuario", mapOf("id" to usuarioItem.Id), "DELETE")
        repositorio.getHistorial(mapOf("id_jugador" to usuarioItem.Id))
            ?.let { repositorio.deleteHistorial(it) }
        repositorio.getMiembro(mapOf("id_usuario" to usuarioItem.Id))
            ?.let { repositorio.deleteMiembro(it) }
        repositorio.getUsuario(mapOf("id" to usuarioItem.Id))
            ?.let { repositorio.deleteUsuario(it) }
    } }
}


@Composable
fun Grupos(){}
@Composable
fun Juegos(){}



