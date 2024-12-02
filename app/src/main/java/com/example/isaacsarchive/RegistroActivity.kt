package com.example.isaacsarchive

import Clases.AdminSQLiteOpenHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class RegistroActivity : AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etContrasena1: EditText
    private lateinit var etContrasena2: EditText
    private lateinit var twError: TextView
    private lateinit var buttonRegistar: Button
    private lateinit var buttonSalir: Button
    private lateinit var ventanaLogin: Intent
    private lateinit var db: AdminSQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)

        // Inicializamos los elementos de la vista
        etUsuario = findViewById(R.id.etUsuarioRegistrar)
        etContrasena1 = findViewById(R.id.etContrasena1)
        etContrasena2 = findViewById(R.id.etContrasena2)
        twError = findViewById(R.id.twError)
        buttonRegistar = findViewById(R.id.buttonRegistrar)
        buttonSalir = findViewById(R.id.buttonSalir)
        ventanaLogin = Intent(this, LoginActivity::class.java)
    }

    fun registrar(v: View?) {
        if (comprobarUsuario() && comprobarContrasena() && db.consultarUsuario(etUsuario.text.toString()) == 0) {
            db.insertarUsuario(this, etUsuario.text.toString(), encriptarContrasena(etContrasena1.text.toString()))
            startActivity(ventanaLogin)
            borrarCampos()
            finish()
        }
    }

    fun salir(v: View?) {
        finish()
    }

    fun comprobarUsuario(): Boolean {
        if (etUsuario.text.isEmpty()) {
            twError.text = "El campo de usuario no puede estar vacío"
            return false
        }
        return true
        twError.text = ""
    }

    fun comprobarContrasena(): Boolean {
       if (etContrasena1.text.isNotEmpty() && etContrasena2.text.isNotEmpty()) {
           if (etContrasena1.text.toString() != etContrasena2.text.toString()) {
               twError.text = "Las contraseñas no coinciden"
               return false
           }
           if (comprobarContraseniaRegex()) {
               return true
           } else {
               return false
           }
       }
       twError.text = "El campo de contraseña no puede estar vacío"
       return false
    }

    fun comprobarContraseniaRegex(): Boolean {
        val contrasenia = etContrasena1.text.toString()
        val contraseniaRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
        if (contrasenia.isEmpty()) {
            twError.text = "Debes introducir una contraseña."
            return false
        } else if (!contrasenia.matches(contraseniaRegex)) {
            twError.text = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número."
            return false
        }
        twError.text = ""
        return true
    }

    fun encriptarContrasena(contrasena: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(contrasena.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun borrarCampos() {
        etUsuario.text.clear()
        etContrasena1.text.clear()
        etContrasena2.text.clear()
    }

}