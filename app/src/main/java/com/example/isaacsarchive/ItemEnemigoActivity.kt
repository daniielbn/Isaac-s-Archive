package com.example.isaacsarchive

import Adaptadores.AdaptadorComentario
import Clases.AdminSQLiteOpenHelper
import Clases.Comentario
import Clases.Enemigo
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isaacsarchive.databinding.ActivityItemEnemigoBinding

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ItemEnemigoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemEnemigoBinding
    private lateinit var adaptadorComentario: AdaptadorComentario
    private lateinit var ventanaObjeto: Intent
    private lateinit var ventanaPerfil: Intent
    private lateinit var db: AdminSQLiteOpenHelper

    private lateinit var twPerfil: TextView
    private lateinit var twTitulo: TextView
    private lateinit var twDescripcion: TextView
    private lateinit var twTipo: TextView
    private lateinit var cbDesbloqueado: CheckBox
    private lateinit var imgEnemigo: ImageView
    private lateinit var imgVolver: ImageView
    private lateinit var etComentario: EditText
    private lateinit var buttonComentar: Button

    private lateinit var usuario: String
    private lateinit var enemigo: Enemigo
    private var listaComentarios = mutableListOf<Comentario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemEnemigoBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        enemigo = intent.getSerializableExtra("enemigo") as Enemigo
        setContentView(binding.root)
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)
        listaComentarios = db.obtenerComentariosEnemigo(enemigo)
        adaptadorComentario = AdaptadorComentario(listaComentarios, usuario)
        setupRecyclerView()

        twPerfil = findViewById(R.id.twPerfil)
        twPerfil.contentDescription = "Nombre de perfil del usuario"
        twTitulo = findViewById(R.id.twTituloEnemigo)
        twTitulo.contentDescription = "Nombre del enemigo"
        twDescripcion = findViewById(R.id.twDescripcionEnemigo)
        twDescripcion.contentDescription = "Descripción del enemigo"
        twTipo = findViewById(R.id.twTipoEnemigo)
        twTipo.contentDescription = "Tipo de enemigo"
        cbDesbloqueado = findViewById(R.id.cbDesbloqueadoEnemigo)
        cbDesbloqueado.contentDescription = "Checkbox para desbloquear enemigo"
        imgEnemigo = findViewById(R.id.imgEnemigo)
        imgEnemigo.contentDescription = "Imagen del enemigo"
        imgVolver = findViewById(R.id.imgVolver)
        imgVolver.contentDescription = "Botón para volver a la pantalla anterior"
        etComentario = findViewById(R.id.etComentario)
        etComentario.contentDescription = "Campo de texto para agregar comentario"
        buttonComentar = findViewById(R.id.buttonComentario)
        buttonComentar.contentDescription = "Botón para agregar comentario"

        twPerfil.text = usuario
        llenarEnemigo()

        ventanaObjeto = Intent(this, PrincipalObjetosActivity::class.java)
        ventanaPerfil = Intent(this, PerfilActivity::class.java)

        cbDesbloqueado.isChecked = db.comprobarDesbloqueoEnemigo(enemigo, usuario)
        cbDesbloqueado.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (isChecked) {
                enemigo.desbloqueado = true
                actualizarEstadoDesbloqueado(enemigo, 0)
            } else {
                enemigo.desbloqueado = false
                actualizarEstadoDesbloqueado(enemigo, 1)
            }
        }
    }

    fun abrirObjetos(v: View?) {
        ventanaObjeto.putExtra("usuario", usuario)
        startActivity(ventanaObjeto)
    }

    fun abrirPerfil(v: View?) {
        ventanaPerfil.putExtra("usuario", usuario)
        startActivity(ventanaPerfil)
    }

    fun volver(v: View?) {
        finish()
    }

    fun anadirComentario(v: View?) {
        if (etComentario.text.toString().isNotEmpty()) {
            enviarComentario(
                etComentario.text.toString(),
                db.consultarUsuario(usuario),
                db.consultarEnemigo(enemigo.nombre)
            )
            etComentario.text.clear()
        } else {
            Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarEstadoDesbloqueado(enemigo: Enemigo, opcion: Int) {
        if (opcion == 0) {
            if (db.actualizarDesbloqueadoEnemigo(enemigo, usuario)) {
                Toast.makeText(this, "Se actualizó el estado de desbloqueado.\nEl enemigo ahora está desbloqueado.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar el estado de desbloqueado", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (db.eliminarDesbloqueoEnemigo(enemigo, usuario)) {
                Toast.makeText(this, "Se actualizó el estado de desbloqueado.\nEl enemigo ahora está bloqueado.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar el estado de desbloqueado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarComentario(nuevoComentario: String, idUsuario: Int, idEnemigo: Int) {
        val comentario = Comentario(idUsuario, null, idEnemigo, nuevoComentario, getFechaActual())
        if (db.anadirComentarioEnemigo(comentario)) {
            listaComentarios.add(comentario)
            adaptadorComentario.notifyItemInserted(listaComentarios.size - 1)
            binding.rvListaComentarios.scrollToPosition(listaComentarios.size - 1)
            Toast.makeText(this, "Comentario agregado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al agregar el comentario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun llenarEnemigo() {
        twTitulo.text = enemigo.nombre
        twDescripcion.text = enemigo.descripcion
        cbDesbloqueado.isSelected = enemigo.desbloqueado
        twTipo.text = enemigo.tipo

        val nombreImagen = obtenerRuta(enemigo.nombre)
        val idImagen = imgEnemigo.context.resources.getIdentifier(nombreImagen, "drawable", imgEnemigo.context.packageName)
        imgEnemigo.setImageResource(idImagen)
    }

    private fun obtenerRuta(nombre: String): String {
        if (nombre.lowercase().equals("gemini")) {
            return "gemini_enemigo"
        }
        return nombre.lowercase().replace(" ", "_").replace("'", "").replace(".", "")
    }

    private fun setupRecyclerView() {
        binding.rvListaComentarios.layoutManager = LinearLayoutManager(this)
        binding.rvListaComentarios.adapter = adaptadorComentario
    }

    private fun getFechaActual(): String {
        val formato =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return LocalDateTime.now().format(formato)
    }
}