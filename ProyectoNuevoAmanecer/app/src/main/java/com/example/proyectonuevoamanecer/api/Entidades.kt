package com.example.proyectonuevoamanecer.api

import org.json.JSONObject
data class UsuarioAPI (
    val Id: String,
    val Nombre: String,
    val Admin: Boolean,
    ){
    constructor(datosUsuario: JSONObject): this (
        Id = datosUsuario.optString("ID"),
        Nombre = datosUsuario.optString("Nombre"),
        Admin = datosUsuario.optBoolean("Administrador")
    )
    fun getIdUsuario() = Id
    fun getNombreUsuario() = Nombre
    fun getAdminUsuario() = Admin
}

data class GrupoAPI (
    val Id: String,
    val Nombre: String,
    val Gamemaster: String,
){
    constructor(datosGrupo: JSONObject): this (
        Id = datosGrupo.optString("ID"),
        Nombre = datosGrupo.optString("Nombre"),
        Gamemaster = datosGrupo.optString("Gamemaster")
    )
    fun getIdGrupo() = Id
    fun getNombreGrupo() = Nombre
    fun getGameMasterGrupo() = Gamemaster
}

data class JuegoAPI (
    val Id: Int,
    val Nombre: String,
){
    constructor(datosJuego: JSONObject): this (
        Id = datosJuego.optInt("ID"),
        Nombre = datosJuego.optString("Nombre"),
    )
    fun getIdJuego() = Id
    fun getNombreJuego() = Nombre
}

data class MiembroAPI (
    val Id_Grupo: String,
    val Id_Usuario: String,
    val Configuracion: Boolean,
){
    constructor(datosMiembro: JSONObject): this (
        Id_Grupo = datosMiembro.optString("ID_Grupo"),
        Id_Usuario = datosMiembro.optString("ID_Usuario"),
        Configuracion = datosMiembro.optBoolean("Configuracion")
    )
    fun getIdGrupoMiembro() = Id_Grupo
    fun getIdUsuarioMiembro() = Id_Usuario
    fun getPermConfig() = Configuracion

}

data class ConfiguracionJuegoAPI (
    val Id_Juego: Int,
    val Id_Grupo: String,
    val Configuracion: JSONObject?,
){
    constructor(datosConfiguracionJuego: JSONObject): this (
        Id_Juego = datosConfiguracionJuego.optInt("ID_Juego"),
        Id_Grupo = datosConfiguracionJuego.optString("ID_Grupo"),
        Configuracion = datosConfiguracionJuego.optJSONObject("Configuracion")
    )
    fun getIdJuegoConfig() = Id_Juego
    fun getIdGrupoConfig() = Id_Grupo
    fun getConfig() = Configuracion
}

data class HistorialJuegoAPI (
    val Id_Usuario: String,
    val Id_Grupo: String,
    val Id_Juego: Int,
    val Puntuacion: Long,
){
    constructor(datosHistorialJuego: JSONObject): this (
        Id_Usuario = datosHistorialJuego.optString("ID_Jugador"),
        Id_Grupo = datosHistorialJuego.optString("ID_Grupo"),
        Id_Juego = datosHistorialJuego.optInt("ID_Juego"),
        Puntuacion = datosHistorialJuego.optLong("Puntuaje")
    )
    fun getIdUsuarioHistorial() = Id_Usuario
    fun getIdGrupoHistorial() = Id_Grupo
    fun getIdJuegoHistorial() = Id_Juego
    fun getPuntuaje() = Puntuacion
}