package BaseActivity

import android.content.Intent
import android.content.SharedPreferences
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.isaacsarchive.Perfil.PerfilActivity
import com.example.isaacsarchive.Perfil.PerfilAyudaActivity
import java.util.Locale

abstract class ReconocimientoVoz : BaseActivity() {
    private lateinit var reconocimientoVoz: ActivityResultLauncher<Intent>
    private lateinit var preferencias: SharedPreferences

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        reconocimientoVoz = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK && resultado.data != null) {
                val resultados = resultado.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!resultados.isNullOrEmpty()) {
                    manejarComando(resultados[0])
                }
            }
        }

        if (estaActivadoReconocimiento()) {
            iniciarReconocimiento()
        }
    }

    private fun estaActivadoReconocimiento(): Boolean {
        return preferencias.getBoolean("reconocimientoVoz", false)
    }

    protected fun iniciarReconocimiento() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di un comando (En caso de duda, di 'ayuda')")

        try {
            reconocimientoVoz.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al iniciar reconocimiento de voz", Toast.LENGTH_SHORT).show()
        }
    }

    protected abstract fun manejarComando(comando: String);
}