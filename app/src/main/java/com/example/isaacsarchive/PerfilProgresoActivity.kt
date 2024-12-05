package com.example.isaacsarchive

import Clases.AdminSQLiteOpenHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PerfilProgresoActivity : AppCompatActivity() {
    private var contadorEliminar = 0

    private var totalObjetos = 0
    private var objetosDesbloqueados = 0
    private var porcentajeObjetos = 0.0f

    private var totalEnemigos = 0
    private var enemigosDesbloqueados = 0
    private var porcentajeEnemigos = 0.0f

    private lateinit var usuario: String
    private lateinit var twPerfil: TextView

    private lateinit var twConfiguracion: TextView
    private lateinit var twAccesibilidad: TextView
    private lateinit var twAyuda: TextView
    private lateinit var twCerrarSesion: TextView

    private lateinit var twTotalObjetos: TextView
    private lateinit var twObjetosDesbloqueados: TextView
    private lateinit var twPorcentajeObjetos: TextView

    private lateinit var twTotalEnemigos: TextView
    private lateinit var twEnemigosDesbloqueados: TextView
    private lateinit var twPorcentajeEnemigos: TextView

    private lateinit var pbObjetos: ProgressBar
    private lateinit var pbEnemigos: ProgressBar

    private lateinit var ventanaInicio: Intent
    private lateinit var ventanaConfiguracion: Intent
    private lateinit var ventanaAccesibilidad: Intent
    private lateinit var ventanaAyuda: Intent
    private lateinit var ventanaLogin: Intent

    private lateinit var preferencias: SharedPreferences

    private val db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_progreso)

        totalObjetos = db.obtenerNumeroObjetos()
        objetosDesbloqueados = db.obtenerNumeroObjetosUsuario()

        totalEnemigos = db.obtenerNumeroEnemigos()
        enemigosDesbloqueados = db.obtenerNumeroEnemigosUsuario()

        usuario = intent.getStringExtra("usuario").toString()
        twPerfil = findViewById(R.id.twPerfilPerfil)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        twPerfil.text = usuario

        twConfiguracion = findViewById(R.id.twConfiguracionPerfil)
        twConfiguracion.contentDescription = "Botón de configuración de perfil"
        twAccesibilidad = findViewById(R.id.twAccesibilidadPerfil)
        twAccesibilidad.contentDescription = "Botón de accesibilidad de perfil"
        twAyuda = findViewById(R.id.twAyudaPerfil)
        twAyuda.contentDescription = "Botón de ayuda de perfil"
        twCerrarSesion = findViewById(R.id.twCerrarSesionPerfil)
        twCerrarSesion.contentDescription = "Botón de cerrar sesión de perfil"

        twTotalObjetos = findViewById(R.id.twTotalObjetos)
        twTotalObjetos.contentDescription = "Número total de objetos"
        twObjetosDesbloqueados = findViewById(R.id.twObjetosDesbloqueados)
        twObjetosDesbloqueados.contentDescription = "Número de objetos desbloqueados por el usuario"
        twPorcentajeObjetos = findViewById(R.id.twPorcentajeObjetos)
        twPorcentajeObjetos.contentDescription = "Porcentaje de objetos desbloqueados por el usuario"

        twTotalEnemigos = findViewById(R.id.twTotalEnemigos)
        twTotalEnemigos.contentDescription = "Número total de enemigos"
        twEnemigosDesbloqueados = findViewById(R.id.twEnemigosDesbloqueados)
        twEnemigosDesbloqueados.contentDescription = "Número de enemigos desbloqueados por el usuario"
        twPorcentajeEnemigos = findViewById(R.id.twPorcentajeEnemigos)
        twPorcentajeEnemigos.contentDescription = "Porcentaje de enemigos desbloqueados por el usuario"

        pbObjetos = findViewById(R.id.pbObjetos)
        pbObjetos.contentDescription = "Barra de progreso de objetos desbloqueados"
        pbEnemigos = findViewById(R.id.pbEnemigos)
        pbEnemigos.contentDescription = "Barra de progreso de enemigos desbloqueados"

        twTotalObjetos.text = totalObjetos.toString()
        twObjetosDesbloqueados.text = objetosDesbloqueados.toString()

        twTotalEnemigos.text = totalEnemigos.toString()
        twEnemigosDesbloqueados.text = enemigosDesbloqueados.toString()

        porcentajeObjetos = (objetosDesbloqueados.toFloat() / totalObjetos.toFloat()) * 100
        porcentajeEnemigos = (enemigosDesbloqueados.toFloat() / totalEnemigos.toFloat()) * 100

        pbObjetos.progress = porcentajeObjetos.toInt()
        pbEnemigos.progress = porcentajeEnemigos.toInt()

        twPorcentajeObjetos.text = porcentajeObjetos.toInt().toString() + "%"
        twPorcentajeEnemigos.text = porcentajeEnemigos.toInt().toString() + "%"

        ventanaInicio = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaConfiguracion = Intent(this, PerfilActivity::class.java)
        ventanaAccesibilidad = Intent(this, PerfilAccesibilidadActivity::class.java)
        ventanaAyuda = Intent(this, PerfilAyudaActivity::class.java)
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

    fun cerrarSesion(v: View?) {
        preferencias.edit().putBoolean("recordar", false).apply()
        preferencias.edit().putString("usuario", "").apply()
        startActivity(ventanaLogin)
        finish()
    }

    fun eliminarProgreso(v: View?) {
        contadorEliminar++
        if (contadorEliminar <= 3) {
            Toast.makeText(this, "Pulsa ${3 - contadorEliminar} para eliminar el progreso.", Toast.LENGTH_SHORT).show()
        } else {
            if (db.eliminarDesbloqueosEnemigoUsuario(usuario) && db.eliminarDesbloqueosObjetoUsuario(usuario)) {
                Toast.makeText(this, "Progreso eliminado correctamente", Toast.LENGTH_SHORT).show()
                twObjetosDesbloqueados.text = "0"
                twEnemigosDesbloqueados.text = "0"
                pbObjetos.progress = 0
                pbEnemigos.progress = 0
                twPorcentajeObjetos.text = "0%"
                twPorcentajeEnemigos.text = "0%"
            }
        }
    }
}