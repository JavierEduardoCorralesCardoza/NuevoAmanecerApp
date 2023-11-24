package com.example.proyectonuevoamanecer.clases

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectonuevoamanecer.screens.config.ConfiguracionViewModel
import java.util.Locale

fun processTTS(context: Context, word: String, viewModel: ConfiguracionViewModel){

    if(viewModel.tts.value) {
        var textToSpeech: TextToSpeech? = null
        textToSpeech = TextToSpeech(context) {

            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    val locSpanish = Locale("spa", "MEX")

                    txtToSpeech.language = locSpanish
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        word,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            } else {
                //  NO TTS ENGINE INSTALLED ON DEVICE

            }

        }
    }
}