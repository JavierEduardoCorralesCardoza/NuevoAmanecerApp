package com.example.proyectonuevoamanecer.screens.flashcards

import android.content.Context
import android.net.Uri
import java.io.File
import java.util.UUID

fun copyImageToInternalStore(context: Context, uri: Uri):String{
    val uuid:String = UUID.randomUUID().toString()
    val filename="$uuid.jpg"
    val file = File(context.filesDir,filename)
    val inputStream = context.contentResolver.openInputStream(uri)
    val fileOutputStream = context.openFileOutput(filename,Context.MODE_PRIVATE)
    inputStream?.copyTo(fileOutputStream)
    inputStream?.close()
    fileOutputStream?.close()
    return file.absolutePath
}