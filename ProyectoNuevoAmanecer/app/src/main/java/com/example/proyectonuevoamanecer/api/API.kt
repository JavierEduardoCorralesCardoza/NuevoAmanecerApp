package com.example.proyectonuevoamanecer.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

val client = OkHttpClient()
val JSON = "application/json; charset=utf-8".toMediaType()

suspend fun api(nombreTabla: String, data: Map<String, Any>, metodo: String, ids: Map<String, String>? = null): JSONObject = withContext(Dispatchers.IO) {
    val baseUrl = "http://nextjs-nuevo-amanecer.vercel.app/pages/api"
    var endpoint = "$baseUrl/$nombreTabla"
    if (ids != null) {
        endpoint += ids.entries.joinToString("&", "?") { "${it.key}=${it.value}" }
    }
    when (metodo) {
        "GET" -> {
            val request = Request.Builder()
                .url(endpoint)
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("Respuesta del servidor: $responseBody")
            JSONObject(responseBody)
        }
        "PUT" -> {
            val body = JSONObject(data).toString().toRequestBody(JSON)
            val request = Request.Builder()
                .url(endpoint)
                .put(body)
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("Respuesta del servidor: $responseBody")
            JSONObject(responseBody)
        }
        "POST" -> {
            val body = JSONObject(data).toString().toRequestBody(JSON)
            val request = Request.Builder()
                .url(endpoint)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("Respuesta del servidor: $responseBody")
            JSONObject(responseBody)
        }
        "DELETE" -> {
            val request = Request.Builder()
                .url(endpoint)
                .delete()
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            println("Respuesta del servidor: $responseBody")
            JSONObject(responseBody)
        }
        else -> throw IllegalArgumentException("Método no soportado: $metodo")
    }
}

fun llamarApi(nombreTabla: String, data: Map<String, Any>, metodo: String, ids: Map<String, String>? = null): JSONObject {
    var response: JSONObject? = null
    runBlocking {
        response = api(nombreTabla, data, metodo, ids)
    }
    return response!!
}