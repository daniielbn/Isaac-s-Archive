package ReconocimientoVoz

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

class ReconocimientoVoz : Service() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

    override fun onCreate() {
        super.onCreate()

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                // El servicio está listo para recibir entrada de voz
            }

            override fun onBeginningOfSpeech() {
                // Reconocimiento de voz comenzó
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Cambios en el volumen detectado (opcional)
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Buffer recibido
            }

            override fun onEndOfSpeech() {
                // El usuario dejó de hablar
            }

            override fun onError(error: Int) {
                // Manejar errores
            }

            override fun onResults(results: Bundle?) {
                // Resultados del reconocimiento de voz
                val recognizedText =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                recognizedText?.let {
                    // Enviar los resultados al cliente (actividad o fragmento)
                    sendBroadcast(Intent("SpeechRecognitionService.Result").apply {
                        putExtra("recognizedText", it)
                    })
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Resultados parciales (si es necesario)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                // Otros eventos
            }
        }

        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    fun empezarEscucha() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
        speechRecognizer.startListening(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}
