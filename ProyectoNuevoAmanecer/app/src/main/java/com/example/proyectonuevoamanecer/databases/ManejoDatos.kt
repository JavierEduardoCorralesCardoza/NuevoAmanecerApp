package com.example.proyectonuevoamanecer.databases

import android.content.Context
import com.example.proyectonuevoamanecer.api.ConfiguracionJuegoAPI
import com.example.proyectonuevoamanecer.api.GrupoAPI
import com.example.proyectonuevoamanecer.api.HistorialJuegoAPI
import com.example.proyectonuevoamanecer.api.JuegoAPI
import com.example.proyectonuevoamanecer.api.MiembroAPI
import com.example.proyectonuevoamanecer.api.UsuarioAPI
import com.example.proyectonuevoamanecer.api.Variables
import com.example.proyectonuevoamanecer.api.llamarApi
import org.json.JSONArray
import org.json.JSONObject

suspend fun obtenerDatos(context: Context, nombreTabla: String, ids: Map<String, String?>? = null): JSONArray? {
    val isConnected = Variables.isNetworkConnected
    if (isConnected) {
        // Si hay conexión a internet, llama a la API y actualiza la base de datos local
        val dataApi = llamarApi(nombreTabla.lowercase(), emptyMap(),"GET", ids)
        var dataAPIArrray: JSONArray? = null
        when(nombreTabla.lowercase()){
            "usuario" -> {dataAPIArrray = dataApi.getJSONArray("Usuarios")}
            "grupo" -> {dataAPIArrray = dataApi.getJSONArray("Grupos")}
            "juego" -> {dataAPIArrray = dataApi.getJSONArray("Juegos")}
            "miembros" -> {dataAPIArrray = dataApi.getJSONArray("Miembros")}
            "configuracion" -> {dataAPIArrray = dataApi.getJSONArray("Configuracion")}
            "historial" -> {dataAPIArrray = dataApi.getJSONArray("Historiales")}
        }
        if (dataAPIArrray != null) {
            actualizarBaseDeDatosLocal(context, nombreTabla, dataAPIArrray)
        }
        return dataAPIArrray ?: JSONArray()
    } else {
        return obtenerDatosDeBaseDeDatosLocal(context, nombreTabla, ids)
    }
}
suspend fun actualizarBaseDeDatosLocal(context: Context,nombreTabla: String, data: JSONArray) {
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    for (i in 0 until data.length()) {
        val item = data.getJSONObject(i)
        when(nombreTabla.lowercase()){
            "usuario" -> {
                val usuarioAPI = UsuarioAPI(item)
                val usuario = Usuario(usuarioAPI.Id, usuarioAPI.Nombre, usuarioAPI.Admin)
                repositorio.insertUsuario(usuario)
            }
            "grupo" -> {
                val grupoAPI = GrupoAPI(item)
                val grupo = Grupo(grupoAPI.Id, grupoAPI.Nombre, grupoAPI.Gamemaster)
                repositorio.insertGrupo(grupo)
            }
            "juego" -> {
                val juegoAPI = JuegoAPI(item)
                val juego = Juego(juegoAPI.Id, juegoAPI.Nombre)
                repositorio.insertJuego(juego)
            }
            "miembros" -> {
                val miembroAPI = MiembroAPI(item)
                val miembro = Miembro(miembroAPI.Id_Grupo, miembroAPI.Id_Usuario, miembroAPI.Configuracion)
                repositorio.insertMiembro(miembro)
            }
            "configuracion" -> {
                val configuracionAPI = ConfiguracionJuegoAPI(item)
                val configuracion = Configuracion(configuracionAPI.Id_Juego, configuracionAPI.Id_Grupo, configuracionAPI.Configuracion.toString())
                repositorio.insertConfiguracion(configuracion)
            }
            "historial" -> {
                val historialAPI = HistorialJuegoAPI(item)
                val historial = Historial(historialAPI.Id_Usuario, historialAPI.Id_Grupo, historialAPI.Id_Juego, historialAPI.Puntuacion.toInt())
                repositorio.insertHistorial(historial)
            }
        }
    }
}


suspend fun obtenerDatosDeBaseDeDatosLocal(context: Context, nombreTabla: String, ids: Map<String, String?>? = null): JSONArray? {
    val db = DbDatabase.getInstance(context)
    val repositorio = Repositorio(db.dbDao())
    return when(nombreTabla.lowercase()){
        "usuarioactivo" -> {
            repositorio.getUsuarioActivo()?.let { transformarEntidadAJson(nombreTabla, it) }
        }
        "usuario" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getUsuarios()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getUsuario(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        "grupo" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getGrupos()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getGrupo(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        "juego" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getJuegos()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getJuego(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        "miembros" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getMiembros()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getMiembro(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        "configuracion" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getConfiguraciones()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getConfiguracion(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        "historial" -> {
            if (ids.isNullOrEmpty()){
                repositorio.getHistoriales()?.let{ transformarEntidadAJson(nombreTabla,it)}
            }
            else{
                repositorio.getHistorial(ids)?.let { transformarEntidadAJson(nombreTabla, it) }
            }
        }
        else -> {
            println("TEST!!!!")
            // Si el nombre de la tabla no coincide con ninguno de los anteriores, devuelve null
            JSONArray()
        }
    }
}

fun <T>transformarEntidadAJson(identificador: String, entidad: T): JSONArray {
    return transformarListaAJson(identificador, listOf(entidad))
}

fun <T>transformarListaAJson(identificador: String, listaEntidades: List<T>?): JSONArray {
    return when (identificador) {
        "usuarioactivo" -> {
            val listaUsuarioActivo = listaEntidades as List<UsuarioActivo>
            JSONArray().apply {
                listaUsuarioActivo.forEach { usuarioActivo ->
                    put(JSONObject().apply {
                        put("ID", usuarioActivo.id)
                        put("ID_Usuario", usuarioActivo.id_usuario)
                        put("ID_Grupo", usuarioActivo.id_grupo)
                    })
                }
            }
        }
        "usuario" -> {
            val listaUsuario = listaEntidades as List<Usuario>
            JSONArray().apply {
                listaUsuario.forEach { usuario ->
                    put(JSONObject().apply {
                        put("ID", usuario.id)
                        put("Nombre", usuario.nombre)
                        put("Administrador", usuario.administrador)
                    })
                }
            }
        }
        "grupo" -> {
            val listaGrupo = listaEntidades as List<Grupo>
            JSONArray().apply {
                listaGrupo.forEach { grupo ->
                    put(JSONObject().apply {
                        put("ID", grupo.id)
                        put("Nombre", grupo.nombre)
                        put("Gamemaster", grupo.gamemaster)
                    })
                }
            }
        }
        "juego" -> {
            val listaJuego = listaEntidades as List<Juego>
            JSONArray().apply {
                listaJuego.forEach { juego ->
                    put(JSONObject().apply {
                        put("ID", juego.id)
                        put("Nombre", juego.nombre)
                    })
                }
            }
        }
        "miembros" -> {
            val listaMiembro = listaEntidades as List<Miembro>
            JSONArray().apply {
                listaMiembro.forEach { miembro ->
                    put(JSONObject().apply {
                        put("ID_Grupo", miembro.id_grupo)
                        put("ID_Usuario", miembro.id_usuario)
                        put("Configuracion", miembro.configuracion)
                    })
                }
            }
        }
        "configuracion" -> {
            val listaConfiguracion = listaEntidades as List<Configuracion>
            JSONArray().apply {
                listaConfiguracion.forEach { configuracion ->
                    put(JSONObject().apply {
                        put("ID_Juego", configuracion.id_juego)
                        put("ID_Grupo", configuracion.id_grupo)
                        put("Configuracion", configuracion.configuracion)
                    })
                }
            }
        }
        "historial" -> {
            val listaHistorial = listaEntidades as List<Historial>
            JSONArray().apply {
                listaHistorial.forEach { historial ->
                    put(JSONObject().apply {
                        put("ID_Jugador", historial.id_jugador)
                        put("ID_Grupo", historial.id_grupo)
                        put("ID_Juego", historial.id_juego)
                        put("Puntuaje", historial.nivel)
                    })
                }
            }
        }
        else -> JSONArray()
    }
}