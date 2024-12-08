package com.example.isaacsarchive

import Adaptadores.AdaptadorComentario
import Clases.AdminSQLiteOpenHelper
import Clases.Comentario
import Clases.Objeto
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
import com.example.isaacsarchive.databinding.ActivityItemObjetoBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ItemObjetoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemObjetoBinding
    private lateinit var adaptadorComentario: AdaptadorComentario
    private lateinit var ventanaEnemigos: Intent
    private lateinit var ventanaPerfil: Intent

    private lateinit var db: AdminSQLiteOpenHelper


    private lateinit var twPerfil: TextView
    private lateinit var twTitulo: TextView
    private lateinit var twDescripcion: TextView
    private lateinit var twTipo: TextView
    private lateinit var cbDesbloqueado: CheckBox
    private lateinit var imgObjeto: ImageView
    private lateinit var imgVolver: ImageView
    private lateinit var etComentario: EditText
    private lateinit var buttonComentar: Button

    private lateinit var usuario: String
    private lateinit var objeto: Objeto
    private var listaComentarios = mutableListOf<Comentario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemObjetoBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        objeto = intent.getSerializableExtra("objeto") as Objeto
        setContentView(binding.root)
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 8)
        listaComentarios = db.obtenerComentariosObjeto(objeto)
        adaptadorComentario = AdaptadorComentario(listaComentarios, usuario)
        setupRecyclerView()


        twPerfil = findViewById(R.id.twPerfil)
        twPerfil.contentDescription = "Nombre de perfil del usuario"
        twTitulo = findViewById(R.id.twTituloObjeto)
        twTitulo.contentDescription = "Nombre del objeto"
        twDescripcion = findViewById(R.id.twDescripcionObjeto)
        twDescripcion.contentDescription = "Descripción del objeto"
        twTipo = findViewById(R.id.twTipoObjeto)
        twTipo.contentDescription = "Tipo de objeto"
        cbDesbloqueado = findViewById(R.id.cbDesbloqueadoObjeto)
        cbDesbloqueado.contentDescription = "Checkbox de desbloqueo de objeto"
        imgObjeto = findViewById(R.id.imgObjeto)
        imgObjeto.contentDescription = "Imagen del objeto"
        imgVolver = findViewById(R.id.imgVolver)
        imgVolver.contentDescription = "Botón para volver a la pantalla anterior"
        etComentario = findViewById(R.id.etComentario)
        etComentario.contentDescription = "Campo de texto para agregar comentario"
        buttonComentar = findViewById(R.id.buttonComentario)
        buttonComentar.contentDescription = "Botón para agregar comentario"

        twPerfil.text = usuario
        llenarObjeto()

        ventanaEnemigos = Intent(this, PrincipalEnemigosActivity::class.java)
        ventanaPerfil = Intent(this, PerfilActivity::class.java)

        cbDesbloqueado.isChecked = db.comprobarDesbloqueoObjeto(objeto, usuario)
        cbDesbloqueado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                objeto.desbloqueado = true
            } else {
                objeto.desbloqueado = false
            }
            actualizarEstadoDesbloqueado(objeto)
        }
    }

    fun abrirEnemigos(v: View?) {
        ventanaEnemigos.putExtra("usuario", usuario)
        startActivity(ventanaEnemigos)
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
                db.consultarObjeto(objeto.nombre)
            )
            etComentario.text.clear()
        } else {
            Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarEstadoDesbloqueado(objeto: Objeto) {
        if (db.actualizarDesbloqueadoObjeto(objeto, usuario)) {
            Toast.makeText(this, "Se actualizó el estado de desbloqueado.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al actualizar el estado de desbloqueado", Toast.LENGTH_SHORT).show()
        }
    }

    fun enviarComentario(nuevoComentario: String, idUsuario: Int, idObjeto: Int) {
        val comentario = Comentario(idUsuario, idObjeto, null, nuevoComentario, getFechaActual())
        if (db.anadirComentarioObjeto(comentario)) {
            listaComentarios.add(comentario)
            adaptadorComentario.notifyItemInserted(listaComentarios.size - 1)
            binding.rvListaComentarios.scrollToPosition(listaComentarios.size - 1)
            Toast.makeText(this, "Comentario agregado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al agregar el comentario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun llenarObjeto() {
        twTitulo.text = objeto.nombre
        twDescripcion.text = objeto.descripcion
        cbDesbloqueado.isSelected = db.comprobarDesbloqueoObjeto(objeto, usuario)
        twTipo.text = objeto.rareza

        val nombreImagen = obtenerRuta(objeto.nombre)
        val idImagen = imgObjeto.context.resources.getIdentifier(nombreImagen, "drawable", imgObjeto.context.packageName)
        imgObjeto.setImageResource(idImagen)
    }

    private fun obtenerRuta(nombre: String): String {
        if (nombre.lowercase().equals("gemini")) {
            return "gemini_objeto"
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