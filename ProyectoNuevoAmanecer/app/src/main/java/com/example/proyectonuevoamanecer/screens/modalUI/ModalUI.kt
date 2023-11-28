package com.example.proyectonuevoamanecer.screens.modalUI

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.proyectonuevoamanecer.api.GrupoAPI
import com.example.proyectonuevoamanecer.api.MiembroAPI
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.databases.DbDatabase
import com.example.proyectonuevoamanecer.databases.Repositorio
import com.example.proyectonuevoamanecer.databases.UsuarioActivo
import com.example.proyectonuevoamanecer.databases.obtenerDatos
import com.example.proyectonuevoamanecer.screens.AppRoutes
import com.example.proyectonuevoamanecer.screens.Navegacion
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalUi(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isAtStartDestination = currentRoute == AppRoutes.HomeScreen.route
    val isAtLogin = currentRoute == AppRoutes.LoginScreen.route
    val appBarColor = when (currentRoute) {
        AppRoutes.Numeros.route -> MaterialTheme.colorScheme.tertiary
        AppRoutes.Rompecabezas.route -> Color.hsl(74F,0.23F,0.87F,1F)
        AppRoutes.EligirImagen.route -> Color.White
        else -> Color.Transparent
    }
    val altColor = if (appBarColor != Color.Transparent) Color.Black else Color.White
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.65F)
            ) {
                ModalDrawerContent(
                    currentScreen = currentRoute,
                    navController = navController,
                    drawerState)}
            },
        ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo Amanecer") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarColor,
                        titleContentColor = altColor),
                    navigationIcon = {
                        if (!isAtLogin) {
                            IconButton(onClick = {
                                if (!isAtStartDestination) { navController.popBackStack() }}
                            ) {
                                if (isAtStartDestination) {
                                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = altColor)
                                } else {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = altColor)
                                }
                            }
                        }
                    },
                    actions = {
                        if(!isAtLogin){
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                        }
                                    }
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = altColor
                                )
                            }
                        }
                    }
                )
            }, containerColor = Color.Transparent,

            ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                Navegacion(navController)
            }
        }
    }
}
@Composable
fun ModalDrawerContent(currentScreen: String?, navController: NavHostController, drawerState: DrawerState) {
    val context = LocalContext.current
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    val scope = rememberCoroutineScope()
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo?>(null) }
    var administrador by remember { mutableStateOf<UsuarioAPI?>(null) }

    var grupoSeleccionado by remember { mutableStateOf<GrupoAPI?>(null) }
    var ids: Map<String, String?>?
    if(drawerState.targetValue == DrawerValue.Open){
        LaunchedEffect(drawerState.targetValue == DrawerValue.Open) {
            usuarioActivo = repositorio.getUsuarioActivo()
            administrador = obtenerDatos(context,"usuario",mapOf("id" to "${usuarioActivo?.id_usuario}"))?.getJSONObject(0)?.let { UsuarioAPI(it) }
            ids =  mapOf("id" to "${usuarioActivo?.id_grupo}")
            grupoSeleccionado = obtenerDatos(context,"grupo",ids)?.getJSONObject(0)?.let { GrupoAPI(it) }
        }
    }
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(16.dp)) {
        Icon(Icons.Filled.AccountCircle , contentDescription = "Usuario")
        Text(text = administrador?.Nombre ?: "Invitado", Modifier.padding(horizontal = 8.dp))
    }
    usuarioActivo?.let { SeleccionarGrupo(drawerState,it, grupoSeleccionado,repositorio) }
    Text("Menu", modifier = Modifier.padding(16.dp))
    Divider()
    NavigationDrawerItem(
        label = { Text(text = "Inicio") },
        selected = false,
        onClick = {
        navController.navigate(AppRoutes.HomeScreen.route) {
            popUpTo(AppRoutes.HomeScreen.route) { inclusive = true } }
            scope.launch { drawerState.close() }},
        icon = {Icon(Icons.Filled.Home, contentDescription = "Inicio")}
    )

    NavigationDrawerItem(
        label = { Text(text = "Juegos") },
        selected = false,
        onClick = {
        navController.navigate(AppRoutes.JuegosScreen.route) {
            popUpTo(AppRoutes.JuegosScreen.route) { inclusive = true } }
            scope.launch { drawerState.close() }},
        icon = {Icon(Icons.Filled.PlayArrow, contentDescription = "Inicio")}
        )
    if(administrador?.Admin == true){
        NavigationDrawerItem(
            label = { Text(text = "Administracion") },
            selected = false,
            onClick = {/* TODO */},
            icon = {Icon(Icons.Filled.AddCircle, contentDescription = "Inicio")}
        )
    }
    NavigationDrawerItem(
        label = { Text(text = "Cerrar Sesion") },
        selected = false,
        onClick = {
        navController.navigate(AppRoutes.LoginScreen.route) {
            popUpTo(AppRoutes.LoginScreen.route) { inclusive = true }
            scope.launch {
                val sharedPreferences = context.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
                var mantenerSesion = sharedPreferences.getBoolean("mantenerSesion", false)
                mantenerSesion = false
                usuarioActivo?.let { repositorio.deleteUsuarioActivo(it) }
                drawerState.close() }}
    },
        icon = {Icon(Icons.Filled.ExitToApp, contentDescription = "Inicio")})
}
@Composable
fun SeleccionarGrupo(drawerState: DrawerState, usuarioActivo: UsuarioActivo, grupoSeleccionado: GrupoAPI?,repositorio: Repositorio) {
    var showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        SelectGroupDialog(drawerState,showDialog,repositorio,usuarioActivo)
    }

    NavigationDrawerItem(
        label = { Text(text = grupoSeleccionado?.Nombre.toString() ?: "Grupo No seleccionado") },
        selected = false,
        onClick = {
            showDialog.value = true
        },
        icon = { Icon(Icons.Filled.Create, contentDescription = "Inicio") }
    )
}
@Composable
fun SelectGroupDialog(drawerState: DrawerState, showDialog: MutableState<Boolean>, repositorio: Repositorio, usuarioactivo: UsuarioActivo) {
    val context = LocalContext.current
    var gruposMiembroUsuario by remember { mutableStateOf(JSONArray()) }
    val miembroList = remember{ mutableStateOf<List<MiembroAPI?>>(emptyList())}
    val grupoList = remember{ mutableStateOf<List<GrupoAPI?>>(emptyList())}
    var usuarioActivo by remember { mutableStateOf<UsuarioActivo>(usuarioactivo)}

    LaunchedEffect(showDialog.value){
        usuarioActivo = repositorio.getUsuarioActivo()!!
        println(usuarioActivo)
        val miembroListTemp = mutableListOf<MiembroAPI>()
        val grupoListTemp = mutableListOf<GrupoAPI>()

        gruposMiembroUsuario = obtenerDatos(context,"Miembros", mapOf("id_usuario" to usuarioActivo.id_usuario))!!
        println(usuarioActivo)
        for (i in 0 until gruposMiembroUsuario.length()) {
            val item = gruposMiembroUsuario.getJSONObject(i)
            val miembro = MiembroAPI(item.getString("ID_Grupo"), item.getString("ID_Usuario"), item.optBoolean("Configuracion"))
            miembroListTemp.add(miembro)
        }
        miembroList.value = miembroListTemp
        // Obtiene los IDs de los grupos
        val ids = miembroList.value.map { it?.Id_Grupo }.toList()
        for (id in ids) {
            val grupoArray = obtenerDatos(context,"Grupo", mapOf("id" to id))!!
            for (i in 0 until grupoArray.length()) {
                val item = grupoArray.getJSONObject(i)
                val grupo = GrupoAPI(item.getString("ID"), item.getString("Nombre"), item.getString("Gamemaster"))
                grupoListTemp.add(grupo)
            }
        }
        grupoList.value = grupoListTemp
    }

    var selectedGrupo by remember { mutableStateOf(grupoList.value.find { it?.Id == "${usuarioActivo.id_grupo}" }) }
    println(usuarioActivo)
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Selecciona grupo") },
            text = {
                Column {
                    grupoList.value.forEach { grupo ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (grupo?.Id == selectedGrupo?.Id),
                                    onClick = { selectedGrupo = grupo }
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (grupo?.Id == selectedGrupo?.Id),
                                onClick = { selectedGrupo = grupo }
                            )

                            grupo?.Nombre?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineSmall.merge(),
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Aceptar")
                    val scope = rememberCoroutineScope()
                    usuarioActivo.id_grupo = selectedGrupo?.Id
                    scope.launch {
                        repositorio.setUsuarioActivo(usuarioActivo)
                        drawerState.close()
                    }
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                    val scope = rememberCoroutineScope()
                    scope.launch{drawerState.close()}
                }
            }
        )
    }
}