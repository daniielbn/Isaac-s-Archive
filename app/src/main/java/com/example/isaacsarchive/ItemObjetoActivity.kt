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
    private var primero = true
    private lateinit var binding: ActivityItemObjetoBinding
    private lateinit var adaptadorComentario: AdaptadorComentario
    private lateinit var ventanaEnemigos: Intent
    private lateinit var db: AdminSQLiteOpenHelper


    private lateinit var twUsuario: TextView
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
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)
        listaComentarios = db.obtenerComentariosObjeto(objeto)
        adaptadorComentario = AdaptadorComentario(listaComentarios, usuario)
        setupRecyclerView()


        twUsuario = findViewById(R.id.twPerfil)
        twTitulo = findViewById(R.id.twTituloObjeto)
        twDescripcion = findViewById(R.id.twDescripcionObjeto)
        twTipo = findViewById(R.id.twTipoObjeto)
        cbDesbloqueado = findViewById(R.id.cbDesbloqueadoObjeto)
        imgObjeto = findViewById(R.id.imgObjeto)
        imgVolver = findViewById(R.id.imgVolver)
        etComentario = findViewById(R.id.etComentario)
        buttonComentar = findViewById(R.id.buttonComentario)

        twUsuario.text = usuario
        llenarObjeto()

        ventanaEnemigos = Intent(this, PrincipalEnemigosActivity::class.java)

        cbDesbloqueado.isChecked = db.comprobarDesbloqueoObjeto(objeto, usuario)
        cbDesbloqueado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                objeto.desbloqueado = true
                actualizarEstadoDesbloqueado(objeto, 0)
            } else {
                objeto.desbloqueado = false
                actualizarEstadoDesbloqueado(objeto, 1)
            }
        }
    }

    fun abrirEnemigos(v: View?) {
        ventanaEnemigos.putExtra("usuario", usuario)
        startActivity(ventanaEnemigos)
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

    private fun actualizarEstadoDesbloqueado(objeto: Objeto, opcion: Int) {
        if (opcion == 0) {
            if (db.actualizarDesbloqueadoObjeto(objeto, usuario)) {
                Toast.makeText(this, "Se actualizó el estado de desbloqueado.\n El objeto ahora está desbloqueado.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar el estado de desbloqueado", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (db.eliminarDesbloqueoObjeto(objeto, usuario)) {
                Toast.makeText(this, "Se actualizó el estado de desbloqueado.\nEl objeto ahora esta bloqueado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar el estado de desbloqueado", Toast.LENGTH_SHORT).show()
            }
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
        cbDesbloqueado.isSelected = objeto.desbloqueado
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