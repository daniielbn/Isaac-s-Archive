package com.example.isaacsarchive

import Clases.AdminSQLiteOpenHelper
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
    private lateinit var db: AdminSQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)

        // Inicializamos los elementos de la vista
        etUsuario = findViewById(R.id.etUsuario)
        etUsuario.contentDescription = "Nombre de usuario"
        etContrasenia = findViewById(R.id.etContrasena)
        etContrasenia.contentDescription = "Contraseña"
        twError = findViewById(R.id.twErrorInicio)
        twError.contentDescription = "Error"
        cbRecordar = findViewById(R.id.cbRecordar)
        cbRecordar.contentDescription = "Recordar contraseña"
        buttonIniciar = findViewById(R.id.buttonIniciar)
        buttonIniciar.contentDescription = "Iniciar sesión"
        buttonRegistrar = findViewById(R.id.buttonRegistrarme)
        buttonRegistrar.contentDescription = "Registrarme"

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
        if (comprobarUsuario() && comprobarContraseña() && db.consultarUsuario(usuario) > 0) {
            return true
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