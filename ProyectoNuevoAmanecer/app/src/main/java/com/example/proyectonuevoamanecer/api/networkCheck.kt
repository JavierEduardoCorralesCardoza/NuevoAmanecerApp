package com.example.proyectonuevoamanecer.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

object Variables {
    // Global variable used to store network state
    var isNetworkConnected = false
}

// You need to pass the context when creating the class
class CheckNetwork() {
    fun registerNetworkCallback(context: Context) {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Variables.isNetworkConnected = true // Global Static Variable
                }

                override fun onLost(network: Network) {
                    Variables.isNetworkConnected = false // Global Static Variable
                }
            }
            )
            Variables.isNetworkConnected = false
        } catch (e: Exception) {
            Variables.isNetworkConnected = false
        }
    }
}


