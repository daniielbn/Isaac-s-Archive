package com.example.isaacsarchive.Perfil

import Adaptadores.AdaptadorTextoAyuda
import BaseActivity.BaseActivity
import BaseActivity.ReconocimientoVoz
import Clases.AdminSQLiteOpenHelper
import Clases.TextoAyuda
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isaacsarchive.Credenciales.LoginActivity
import com.example.isaacsarchive.Principales.PrincipalObjetosActivity
import com.example.isaacsarchive.R
import com.example.isaacsarchive.databinding.ActivityPerfilAyudaBinding

class PerfilAyudaActivity : ReconocimientoVoz() {
    private lateinit var binding: ActivityPerfilAyudaBinding
    private lateinit var adaptador: AdaptadorTextoAyuda
    var listaTextosAyuda = ArrayList<TextoAyuda>()

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

    private val db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 8)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilAyudaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        listaTextosAyuda = obtenerTextos()
        setupRecyclerView()

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

        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        if (preferencias.getBoolean("reconocimientoVoz", false)) {
            iniciarReconocimiento()
        }
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

    fun setupRecyclerView() {
        binding.rvListaTextosAyuda.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorTextoAyuda(listaTextosAyuda)
        binding.rvListaTextosAyuda.adapter = adaptador
    }

    private fun obtenerTextos(): ArrayList<TextoAyuda> {
        val lista = ArrayList<TextoAyuda>()
        lista.add(TextoAyuda("¿Qué es Isaacs Archive?", "Isaacs Archive es una aplicación que te permite llevar un registro de los objetos y enemigos que has desbloqueado en el videojuego The Binding of Isaac: Rebirth."))
        lista.add(TextoAyuda("¿Cómo puedo desbloquear objetos y enemigos?", "Para desbloquear objetos y enemigos en The Binding of Isaac: Rebirth, debes cumplir ciertos requisitos en el juego. Por ejemplo, para desbloquear el objeto 'Mom's Knife', debes vencer a Mom por primera vez."))
        lista.add(TextoAyuda("¿Cómo puedo añadir objetos y enemigos a mi perfil?", "Para añadir objetos y enemigos a tu perfil, debes buscarlos en la lista de objetos y enemigos y pulsar sobre ellos."))
        lista.add(TextoAyuda("¿Cómo puedo eliminar objetos y enemigos de mi perfil?", "Para eliminar objetos y enemigos de tu perfil, debes buscarlos en la lista de objetos y enemigos y pulsar sobre ellos."))
        lista.add(TextoAyuda("¿Cómo puedo buscar objetos y enemigos en la lista?", "Para buscar objetos y enemigos en la lista, debes escribir el nombre del objeto o enemigo en el campo de búsqueda y pulsar sobre el botón de buscar."))
        lista.add(TextoAyuda("¿Cómo puedo ver mi progreso en el juego?", "Para ver tu progreso en el juego, debes pulsar sobre el botón de progreso en la pantalla principal."))
        lista.add(TextoAyuda("¿Cómo puedo cambiar la configuración de mi perfil?", "Para cambiar la configuración de tu perfil, debes pulsar sobre el botón de configuración en la pantalla principal."))
        lista.add(TextoAyuda("¿Cómo puedo cerrar sesión en la aplicación?", "Para cerrar sesión en la aplicación, debes pulsar sobre el botón de cerrar sesión en la pantalla principal."))
        lista.add(TextoAyuda("¿Cómo puedo manjear el reconocimiento de voz?", "Para poder manejar la navegación de la aplicación por reconocimiento de voz, debes ir a la ventana de 'Accesibilidad' y activar la opción de 'Reconocimiento de voz'.\nLos comandos disponibles son:\n- 'inicio'\n - 'enemigos'\n- 'configuración'\n- 'progreso'\n- 'cerrar sesión'"))
        return lista
    }

    override fun manejarComando(comando: String) {
        when (comando) {
            "inicio" -> abrirInicio(null)
            "configuración" -> abrirConfiguracion(null)
            "progreso" -> abrirProgreso(null)
            "accesibilidad" -> abrirAccesibilidad(null)
            "cerrar sesión" -> cerrarSesion(null)
            else -> Toast.makeText(this, "Comando no reconocido", Toast.LENGTH_SHORT).show()
        }
    }
}