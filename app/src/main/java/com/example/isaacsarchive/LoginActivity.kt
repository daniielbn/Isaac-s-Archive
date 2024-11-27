package com.example.isaacsarchive

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etContrasenia: EditText
    private lateinit var twError: TextView
    private lateinit var cbRecordar: CheckBox
    private lateinit var buttonIniciar: Button
    private lateinit var buttonRegistrar: Button
    private lateinit var ventanaRegistro: Intent
    private lateinit var ventanaPrincipal: Intent
    private lateinit var preferencias: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicializamos los elementos de la vista
        etUsuario = findViewById(R.id.etUsuario)
        etContrasenia = findViewById(R.id.etContrasena)
        twError = findViewById(R.id.twErrorInicio)
        cbRecordar = findViewById(R.id.cbRecordar)
        buttonIniciar = findViewById(R.id.buttonIniciar)
        buttonRegistrar = findViewById(R.id.buttonRegistrarme)
        ventanaPrincipal = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaRegistro = Intent(this, RegistroActivity::class.java)
        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        comprobarRecordarContrasena()

    }

    fun iniciarSesion(v: View?) {
        val usuario = etUsuario.text.toString()
        val contrasenaEncriptada = encriptarContrasena(etContrasenia.text.toString())
        if (verificarUsuario(usuario, contrasenaEncriptada)) {
            recordarContrasena()
            abrirPrincipal(usuario)
        } else {
            twError.text = "Usuario o contraseña incorrectos."
        }
    }

    fun registrarUsuario(v: View?) {
        startActivity(ventanaRegistro)
    }

    fun comprobarUsuario(): Boolean {
        if (etUsuario.text.isEmpty()) {
            twError.text = "Introduce un usuario."
            return false
        }
        return true
    }

    fun comprobarContraseña(): Boolean {
        if (etContrasenia.text.isEmpty()) {
            twError.text = "Introduce una contraseña."
            return false
        }
        return true
    }

    fun recordarContrasena() {
        if (cbRecordar.isChecked) {
            preferencias.edit().putBoolean("recordar", true).apply()
            preferencias.edit().putString("usuario", etUsuario.text.toString()).apply()
        } else {
            preferencias.edit().putBoolean("recordar", false).apply()
            preferencias.edit().putString("usuario", "").apply()
        }
    }

    fun comprobarRecordarContrasena() {
        if (preferencias.getBoolean("recordar", false)) {
            val usuario = preferencias.getString("usuario", "")
            abrirPrincipal(usuario!!)
        }
    }

    fun verificarUsuario(usuario: String, contrasena: String): Boolean {
        if (comprobarUsuario() && comprobarContraseña()) {
            val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive100", null, 1)
            val bd = admin.writableDatabase
            val cursor = bd.rawQuery("select * from Usuarios where usuario = ? and contrasena = ?", arrayOf(usuario, contrasena))
            if (cursor.moveToFirst()) {
                return true
            }
        }
        return false
    }

    fun encriptarContrasena(contrasena: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(contrasena.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun abrirPrincipal(usuario: String) {
        ventanaPrincipal.putExtra("usuario", usuario)
        startActivity(ventanaPrincipal)
    }
}