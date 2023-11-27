package com.example.proyectonuevoamanecer.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

fun checkPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
}