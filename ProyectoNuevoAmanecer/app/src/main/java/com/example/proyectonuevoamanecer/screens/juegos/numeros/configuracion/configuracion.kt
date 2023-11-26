package com.example.proyectonuevoamanecer.screens.juegos.numeros.configuracion

import org.json.JSONObject

data class Configuracion(
    val niveles: List<Nivel>,
    val tiempoInicial: Long,
    val tiempoAgregar: Long,
    val tiempoClickIncorrecto: Long,
    val puntosClickCorrecto: Int,
    val puntosClickIncorrecto: Int,
    val puntosCompletarSet: Int
) {
    constructor(DatosJuego: JSONObject) : this(
        niveles = DatosJuego.optJSONArray("Niveles")?.let { array ->
            List(array.length()) { i ->
                Nivel(array.optJSONObject(i))
            }
        } ?: emptyList(),
        tiempoInicial = DatosJuego.optLong("TiempoInicial"),
        tiempoAgregar = DatosJuego.optLong("TiempoAgregar"),
        tiempoClickIncorrecto = DatosJuego.optLong(("TiempoClickIncorrecto")),
        puntosClickCorrecto = DatosJuego.optInt("PuntosClickCorrecto"),
        puntosClickIncorrecto = DatosJuego.optInt("PuntosClickIncorrecto"),
        puntosCompletarSet = DatosJuego.optInt("PuntosCompletarSet")
    )
}

data class Nivel(
    val rango: IntRange,
    val cantidadNumeros: Int,
    val orden: Orden
) {
    constructor(DatosNivel: JSONObject?) : this(
        rango = 1..(DatosNivel?.optInt("Rango") ?: 100),
        cantidadNumeros = DatosNivel?.optInt("CantidadNumeros") ?: 10,
        orden = Orden.valueOf(DatosNivel?.optString("Orden") ?: "ASC")
    )
}

enum class Orden {
    ASC, DESC, AL
}
