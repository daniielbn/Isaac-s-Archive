package BaseActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val preferencias = newBase.getSharedPreferences("preferencias_usuario", Context.MODE_PRIVATE)
        val tamanoTexto = preferencias.getInt("tamanoTexto", 100)
        val escalado = ajustarEscalaTexto(newBase, tamanoTexto / 100f)
        super.attachBaseContext(escalado)
    }

    private fun ajustarEscalaTexto(context: Context, esclaa: Float): Context {
        val configuracion = context.resources.configuration
        configuracion.fontScale = esclaa
        return context.createConfigurationContext(configuracion)
    }
}