package com.example.isaacsarchive

import Adaptadores.AdaptadorComentario
import Clases.AdminSQLiteOpenHelper
import Clases.Comentario
import Clases.Objeto
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.isaacsarchive.databinding.ActivityItemObjetoBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ItemObjetoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemObjetoBinding
    private lateinit var adaptadorComentario: AdaptadorComentario
    private lateinit var ventanaEnemigos: Intent

    private lateinit var twUsuario: TextView
    private lateinit var twTitulo: TextView
    private lateinit var twDescripcion: TextView
    private lateinit var twTipo: TextView
    private lateinit var cbDesbloqueado: CheckBox
    private lateinit var imgObjeto: ImageView
    private lateinit var imgVolver: ImageView
    private lateinit var etComentario: EditText

    private lateinit var usuario: String
    private lateinit var objeto: Objeto
    private var listaComentarios = mutableListOf<Comentario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemObjetoBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        adaptadorComentario = AdaptadorComentario(listaComentarios, usuario)
        setContentView(binding.root)
        setupRecyclerView()

        twUsuario = findViewById(R.id.twPerfil)

        twTitulo = findViewById(R.id.twTituloObjeto)
        twDescripcion = findViewById(R.id.twDescripcionObjeto)
        twTipo = findViewById(R.id.twTipoObjeto)
        cbDesbloqueado = findViewById(R.id.cbDesbloqueadoObjeto)
        imgObjeto = findViewById(R.id.imgObjeto)
        imgVolver = findViewById(R.id.imgVolver)
        etComentario = findViewById(R.id.etComentario)

        twUsuario.text = usuario

        objeto = intent.getSerializableExtra("objeto") as Objeto

        ventanaEnemigos = Intent(this, PrincipalEnemigosActivity::class.java)

        llenarObjeto()
        llenarComentarios()
    }

    fun abrirEnemigos(v: View?) {
        ventanaEnemigos.putExtra("usuario", usuario)
        startActivity(ventanaEnemigos)
    }

    fun volver(v: View?) {
        finish()
    }

    fun anadirComentario(v: View?) {
        val comentario = etComentario.text.toString()
        val idUsuario = consultarIdUsuario(usuario)
        val idObjeto = consultarIdObjeto(objeto.nombre)

        if (comentario.isNotEmpty()) {
            enviarComentario(comentario, idUsuario, idObjeto)
            etComentario.text.clear()
        } else {
            Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }

    fun enviarComentario(nuevoComentario: String, idUsuario: Int, idObjeto: Int) {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val bd = admin.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("id_usuario", idUsuario)
                put("id_objeto", idObjeto)
                put("comentario", nuevoComentario)
                put("fecha", System.currentTimeMillis())
            }
            val id = bd.insert("Comentarios", null, valores)
            if (id == -1L) {
                Toast.makeText(this, "Error al agregar el comentario", Toast.LENGTH_SHORT).show()
            } else {
                val comentario = Comentario(idUsuario, idObjeto, null, nuevoComentario, System.currentTimeMillis())
                listaComentarios.add(comentario)

                adaptadorComentario.notifyItemInserted(listaComentarios.size - 1)
                binding.rvListaComentarios.scrollToPosition(listaComentarios.size - 1)

                Toast.makeText(this, "Comentario agregado con éxito", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ocurrió un error al agregar el comentario", Toast.LENGTH_SHORT).show()
        } finally {
            bd.close()
        }
    }

    private fun setupRecyclerView() {
        binding.rvListaComentarios.layoutManager = LinearLayoutManager(this)
        binding.rvListaComentarios.adapter = adaptadorComentario
    }

    private fun llenarObjeto() {
        twTitulo.text = objeto.nombre
        twDescripcion.text = objeto.descripcion
        cbDesbloqueado.isSelected = objeto.desbloqueado

        val nombreImagen = obtenerRuta(objeto.nombre)
        val idImagen = imgObjeto.context.resources.getIdentifier(nombreImagen, "drawable", imgObjeto.context.packageName)
        imgObjeto.setImageResource(idImagen)
    }

    private fun llenarComentarios() {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.readableDatabase
        try {
            if (consultarIdObjeto(objeto.nombre) != null) {
                val cursor = db.rawQuery("SELECT id_usuario, id_objeto, id_enemigo, comentario, fecha FROM Comentarios WHERE id_objeto = ?", arrayOf(consultarIdObjeto(objeto.nombre).toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val id_usuario = cursor.getInt(0)
                        val id_objeto = cursor.getInt(1)
                        val id_enemigo = if (!cursor.isNull(2)) cursor.getInt(2) else null
                        val comentario = cursor.getString(3)
                        val fecha = cursor.getLong(4)
                        val comentarioObjeto = Comentario(id_usuario, id_objeto, id_enemigo, comentario, fecha)
                        listaComentarios.add(comentarioObjeto)
                    } while (cursor.moveToNext())
                }
                cursor.close()
            } else {
               Log.e("Error", "No se ha encontrado el objeto")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Error", "Error al cargar los comentarios")
        } finally {
            db.close()
        }
    }

    private fun obtenerRuta(nombre: String): String {
        return nombre.toLowerCase().replace(" ", "_").replace("'", "")
    }

    private fun consultarIdUsuario(nombre: String): Int {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.readableDatabase
        try {
            val cursor = db.rawQuery("SELECT id FROM Usuarios WHERE usuario = ?", arrayOf(nombre))
            if (cursor.moveToFirst()) {
                return cursor.getInt(0)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return -1
    }

    private fun consultarIdObjeto(nombre: String): Int {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.readableDatabase
        try {
            val cursor = db.rawQuery("SELECT id FROM Objetos WHERE nombre = ?", arrayOf(nombre))
            if (cursor.moveToFirst()) {
                return cursor.getInt(0)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return -1
    }
}