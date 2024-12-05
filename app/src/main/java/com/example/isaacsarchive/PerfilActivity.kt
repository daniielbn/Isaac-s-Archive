package com.example.isaacsarchive

import Adaptadores.AdaptadorSpinner
import Clases.AdminSQLiteOpenHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class PerfilActivity : AppCompatActivity() {
    private var contadorEliminarCuenta = 0
    private lateinit var usuario: String
    private lateinit var twPerfil: TextView
    private lateinit var imgLogo: ImageView

    private lateinit var twProgreso: TextView
    private lateinit var twAccesbilidad: TextView
    private lateinit var twAyuda: TextView
    private lateinit var twCerrarSesion: TextView

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena1: EditText
    private lateinit var etContrasena2: EditText

    private lateinit var buttonUsuario: Button
    private lateinit var buttonContrasena: Button
    private lateinit var buttonPersonaje: Button
    private lateinit var buttonEliminarCuenta: Button

    private lateinit var spinner: Spinner

    private lateinit var preferencias: SharedPreferences

    private lateinit var ventanaLogin: Intent
    private lateinit var ventanaInicio: Intent
    private lateinit var ventanaProgreso: Intent
    private lateinit var ventanaAccesibilidad: Intent
    private lateinit var ventanaAyuda: Intent

    private val db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        usuario = intent.getStringExtra("usuario").toString()

        twPerfil = findViewById(R.id.twPerfilInicio)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        imgLogo = findViewById(R.id.imgLogoPrincipalPerfil)
        imgLogo.contentDescription = "Logo de Isaac's Archive"
        twPerfil.text = usuario

        twProgreso = findViewById(R.id.twProgreso)
        twProgreso.contentDescription = "Botón para acceder al perfil de progreso"
        twAccesbilidad = findViewById(R.id.twAccesibilidad)
        twAccesbilidad.contentDescription = "Botón para acceder al perfil de accesibilidad"
        twAyuda = findViewById(R.id.twAyuda)
        twAyuda.contentDescription = "Botón para acceder al perfil de ayuda"
        twCerrarSesion = findViewById(R.id.twCerrarSesion)
        twCerrarSesion.contentDescription = "Botón para cerrar sesión"

        etUsuario = findViewById(R.id.etUsuarioConf)
        etUsuario.contentDescription = "Campo de texto para introducir el nuevo usuario"
        etContrasena1 = findViewById(R.id.etCambiarContrasena1)
        etContrasena1.contentDescription = "Campo de texto para introducir la nueva contraseña"
        etContrasena2 = findViewById(R.id.etCambiarContrasena2)
        etContrasena2.contentDescription = "Campo de texto para repetir la nueva contraseña"
        spinner = findViewById(R.id.spinnerPersonajes)
        spinner.contentDescription = "Selector de personajes"

        buttonUsuario = findViewById(R.id.buttonActualizarUsuario)
        buttonUsuario.contentDescription = "Botón para actualizar el usuario"
        buttonContrasena = findViewById(R.id.buttonActualizarContrasena)
        buttonContrasena.contentDescription = "Botón para actualizar la contraseña"
        buttonPersonaje = findViewById(R.id.buttonActualizarPersonaje)
        buttonPersonaje.contentDescription = "Botón para actualizar el personaje"
        buttonEliminarCuenta = findViewById(R.id.buttonEliminarCuenta)
        buttonEliminarCuenta.contentDescription = "Botón para eliminar la cuenta"

        val items = listOf("Isaac", "Magdalene", "Cain", "Judas", "???", "Eve", "Samsom", "Azazel", "Lazarus", "Eden", "The Lost", "Lilith", "Keeper", "Apollyon", "The Forgotten", "Bethany", "Jacob & Esau")
        val adaptadorSpinner = AdaptadorSpinner(this, items)
        spinner.adapter = adaptadorSpinner

        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        ventanaLogin = Intent(this, LoginActivity::class.java)
        ventanaInicio = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaProgreso = Intent(this, PerfilProgresoActivity::class.java)
        ventanaAccesibilidad = Intent(this, PerfilAccesibilidadActivity::class.java)
        ventanaAyuda = Intent(this, PerfilAyudaActivity::class.java)

        val personajeUsuario = db.consultarPersonaje(usuario)
        if (!personajeUsuario.equals("")) {
            spinner.setSelection(items.indexOf(personajeUsuario))
        }
    }

    fun abrirInicio(v: View?) {
        ventanaInicio.putExtra("usuario", usuario)
        startActivity(ventanaInicio)
        finish()
    }

    fun abrirProgreso(v: View?) {
        ventanaProgreso.putExtra("usuario", usuario)
        startActivity(ventanaProgreso)
        finish()
    }

    fun abrirAccesibilidad(v: View?) {
        ventanaAccesibilidad.putExtra("usuario", usuario)
        startActivity(ventanaAccesibilidad)
        finish()
    }

    fun abrirAyuda(v: View?) {
        ventanaAyuda.putExtra("usuario", usuario)
        startActivity(ventanaAyuda)
        finish()
    }

    fun cambiarUsuario(v: View?) {
        if (comprobarUsuario()) {
            if (db.actualizarUsuario(usuario, etUsuario.text.toString())) {
                Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                usuario = etUsuario.text.toString()
                preferencias.edit().putString("usuario", usuario).apply()
                twPerfil.text = usuario
            } else {
                Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cambiarContrasena(v: View?) {
        if (comprobarContrasena(etContrasena1.text.toString(), etContrasena2.text.toString())) {
            if (db.actualizarContrasena(usuario, etContrasena1.text.toString())) {
                Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cambiarPersonaje(v: View?) {
        val personaje = spinner.selectedItem.toString()
        if (db.actualizarPersonaje(usuario, personaje)) {
            Toast.makeText(this, "Personaje actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al actualizar el personaje", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminarCuenta(v: View?) {
        contadorEliminarCuenta++
        if (contadorEliminarCuenta > 5) {
            db.eliminarUsuario(usuario)
            Toast.makeText(this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show()
            startActivity(ventanaLogin)
        } else {
            Toast.makeText(this, "Pulsa ${5 - contadorEliminarCuenta} veces para eliminar la cuenta", Toast.LENGTH_SHORT).show()
        }
    }

    fun cerrarSesion(v: View?) {
        preferencias.edit().putBoolean("recordar", false).apply()
        preferencias.edit().putString("usuario", "").apply()
        startActivity(ventanaLogin)
        finish()
    }

    fun comprobarCampoUsuario(): Boolean {
        if (etUsuario.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    fun comprobarCamposContrasena(): Boolean {
        val contrasenaAntigua = encriptarContrasena(db.consultarContrasena(usuario))
        if (etContrasena1.text.toString().isEmpty() || etContrasena2.text.toString().isEmpty()) {
            Toast.makeText(this, "Debe rellenar los campos de 'Contraseña' y 'Repetir contraseña' obligatoriamente", Toast.LENGTH_SHORT).show()
            return false
        } else if (encriptarContrasena(etContrasena1.text.toString()).equals(contrasenaAntigua)) {
            Toast.makeText(this, "La contraseña no puede ser la misma que la actual", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun comprobarContraseniaRegex(): Boolean {
        val contrasenia = etContrasena1.text.toString()
        val contraseniaRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
        if (!contrasenia.matches(contraseniaRegex)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun comprobarUsuario(): Boolean {
        if (comprobarCampoUsuario()) {
            if (usuario.equals(etUsuario.text.toString())) {
                Toast.makeText(this,"El usuario no puede ser el mismo que el actual", Toast.LENGTH_SHORT).show()
                return false
            } else if (db.consultarUsuario(etUsuario.text.toString()) > 0) {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                return false
            } else
                return true
        }
        return false
    }

    fun comprobarContrasena(contrasena: String, contrasenaRepetida: String): Boolean {
        if (comprobarCamposContrasena()) {
            if (!contrasena.equals(contrasenaRepetida)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return false
            } else if (comprobarContraseniaRegex()){
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
}