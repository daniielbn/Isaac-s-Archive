package com.example.isaacsarchive

import Clases.AdminSQLiteOpenHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilAyudaActivity : AppCompatActivity() {
    private lateinit var usuario: String
    private lateinit var twPerfil: TextView

    private lateinit var twConfiguracion: TextView
    private lateinit var twProgreso: TextView
    private lateinit var twAyuda: TextView
    private lateinit var twCerrarSesion: TextView

    private lateinit var ventanaInicio: Intent
    private lateinit var ventanaConfiguracion: Intent
    private lateinit var ventanaProgreso: Intent
    private lateinit var ventanaAccesibilidad: Intent
    private lateinit var ventanaLogin: Intent

    private lateinit var preferencias: SharedPreferences

    private val db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_ayuda)

        usuario = intent.getStringExtra("usuario").toString()
        twPerfil = findViewById(R.id.twPerfilAyuda)
        twPerfil.text = usuario

        twConfiguracion = findViewById(R.id.twConfiguracionAyuda)
        twConfiguracion.contentDescription = "Botón de configuración de perfil"
        twProgreso = findViewById(R.id.twProgresoAyuda)
        twProgreso.contentDescription = "Botón de progreso de perfil"
        twAyuda = findViewById(R.id.twAyudaAyuda)
        twAyuda.contentDescription = "Botón de ayuda de perfil"
        twCerrarSesion = findViewById(R.id.twCerrarSesionAyuda)
        twCerrarSesion.contentDescription = "Botón de cerrar sesión de perfil"

        ventanaInicio = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaConfiguracion = Intent(this, PerfilActivity::class.java)
        ventanaProgreso = Intent(this, PerfilProgresoActivity::class.java)
        ventanaAccesibilidad = Intent(this, PerfilAccesibilidadActivity::class.java)
        ventanaLogin = Intent(this, LoginActivity::class.java)

        preferencias = getSharedPreferences("preferencias-usuario", MODE_PRIVATE)
    }

    fun abrirInicio(v: View?) {
        ventanaInicio.putExtra("usuario", usuario)
        startActivity(ventanaInicio)
        finish()
    }

    fun abrirConfiguracion(v: View?) {
        ventanaConfiguracion.putExtra("usuario", usuario)
        startActivity(ventanaConfiguracion)
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

    fun cerrarSesion(v: View?) {
        preferencias.edit().putBoolean("recordar", false).apply()
        preferencias.edit().putString("usuario", "").apply()
        startActivity(ventanaLogin)
        finish()
    }

}