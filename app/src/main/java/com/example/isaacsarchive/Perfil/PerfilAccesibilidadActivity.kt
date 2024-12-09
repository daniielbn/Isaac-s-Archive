package com.example.isaacsarchive.Perfil

import BaseActivity.BaseActivity
import Clases.AdminSQLiteOpenHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import com.example.isaacsarchive.Credenciales.LoginActivity
import com.example.isaacsarchive.Principales.PrincipalObjetosActivity
import com.example.isaacsarchive.R

class PerfilAccesibilidadActivity : BaseActivity() {
    private var tamanoTexto = 100
    private lateinit var usuario: String
    private lateinit var twPerfil: TextView

    private lateinit var twConfiguracion: TextView
    private lateinit var twProgreso: TextView
    private lateinit var twAyuda: TextView
    private lateinit var twCerrarSesion: TextView

    private lateinit var imgAccesibilidad: ImageView
    private lateinit var sbTamanoTexto: SeekBar
    private lateinit var rgTema: RadioGroup
    private lateinit var rbClaro: RadioButton
    private lateinit var rbOscuro: RadioButton
    private lateinit var rbDefecto: RadioButton

    private lateinit var ventanaInicio: Intent
    private lateinit var ventanaConfiguracion: Intent
    private lateinit var ventanaAccesibilidad: Intent
    private lateinit var ventanaProgreso: Intent
    private lateinit var ventanaAyuda: Intent
    private lateinit var ventanaLogin: Intent

    private lateinit var preferencias: SharedPreferences

    private val db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 8)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_accesibilidad)

        usuario = intent.getStringExtra("usuario").toString()
        twPerfil = findViewById(R.id.twPerfilAccesibilidad)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        twPerfil.text = usuario

        twConfiguracion = findViewById(R.id.twConfiguracionAccesibilidad)
        twConfiguracion.contentDescription = "Botón de configuración de perfil"
        twProgreso = findViewById(R.id.twProgresoAccesibilidad)
        twProgreso.contentDescription = "Botón de progreso de perfil"
        twAyuda = findViewById(R.id.twAyudaAccesibilidad)
        twAyuda.contentDescription = "Botón de ayuda de perfil"
        twCerrarSesion = findViewById(R.id.twCerrarSesionAccesibilidad)
        twCerrarSesion.contentDescription = "Botón de cerrar sesión de perfil"

        imgAccesibilidad = findViewById(R.id.imgAccesibilidad)
        imgAccesibilidad.contentDescription = "Imagen de accesibilidad de perfil"

        sbTamanoTexto = findViewById(R.id.sbTamanoTexto)
        sbTamanoTexto.min = 100
        sbTamanoTexto.max = 150


        rgTema = findViewById(R.id.rgTema)
        rgTema.contentDescription = "Grupo de botones de tema"
        rbClaro = findViewById(R.id.rbClaro)
        rbClaro.contentDescription = "Botón de tema claro"
        rbOscuro = findViewById(R.id.rbOscuro)
        rbOscuro.contentDescription = "Botón de tema oscuro"
        rbDefecto = findViewById(R.id.rbDefecto)
        rbDefecto.contentDescription = "Botón de tema por defecto"

        ventanaInicio = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaConfiguracion = Intent(this, PerfilActivity::class.java)
        ventanaAccesibilidad = Intent(this, PerfilAccesibilidadActivity::class.java)
        ventanaProgreso = Intent(this, PerfilProgresoActivity::class.java)
        ventanaAyuda = Intent(this, PerfilAyudaActivity::class.java)
        ventanaLogin = Intent(this, LoginActivity::class.java)

        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        establecerImagen()
        comprobarTema()
        val antiguoEscalado = preferencias.getInt("tamanoTexto", 100)
        sbTamanoTexto.progress = antiguoEscalado

        sbTamanoTexto.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tamanoTexto = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun abrirInicio(v: View?) {
        ventanaInicio.putExtra("usuario", usuario)
        startActivity(ventanaInicio)
    }

    fun abrirConfiguracion(v: View?) {
        ventanaConfiguracion.putExtra("usuario", usuario)
        startActivity(ventanaConfiguracion)
    }

    fun abrirProgreso(v: View?) {
        ventanaProgreso.putExtra("usuario", usuario)
        startActivity(ventanaProgreso)
    }

    fun abrirAyuda(v: View?) {
        ventanaAyuda.putExtra("usuario", usuario)
        startActivity(ventanaAyuda)
    }

    fun cerrarSesion(v: View?) {
        preferencias.edit().putBoolean("recordar", false).apply()
        preferencias.edit().putString("usuario", "").apply()
        startActivity(ventanaLogin)
    }

    fun actualizarTexto(v: View?) {
        preferencias.edit().putInt("tamanoTexto", tamanoTexto).apply()
        ventanaAccesibilidad.putExtra("usuario", usuario)
        startActivity(ventanaAccesibilidad)
    }

    fun actualizarTema(v: View?) {
        if (rbClaro.isChecked) {
            preferencias.edit().putString("tema", "claro").apply()
        } else if (rbOscuro.isChecked) {
            preferencias.edit().putString("tema", "oscuro").apply()
        } else {
            preferencias.edit().putString("tema", "").apply()
        }
        establecerTema(preferencias.getString("tema", "").toString())
    }

    fun establecerImagen() {
        if (preferencias.getString("tema", "claro").equals("claro")) {
            imgAccesibilidad.setImageResource(R.drawable.accesibilidad_claro)
        } else {
            imgAccesibilidad.setImageResource(R.drawable.accesibilidad_oscuro)
        }
    }

    fun establecerTema(esOscuro: String) {
        if (esOscuro.equals("oscuro")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (esOscuro.equals("claro")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun comprobarTema() {
        val tema = preferencias.getString("tema", "").toString()
        if (tema == "claro") {
            rbClaro.isChecked = true
        } else if (tema == "oscuro") {
            rbOscuro.isChecked = true
        } else {
            rbDefecto.isChecked = true
        }
    }
}