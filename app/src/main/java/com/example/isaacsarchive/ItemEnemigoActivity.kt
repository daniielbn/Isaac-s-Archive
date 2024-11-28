package com.example.isaacsarchive

import Adaptadores.AdaptadorComentario
import Clases.AdminSQLiteOpenHelper
import Clases.Comentario
import Clases.Enemigo
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.isaacsarchive.databinding.ActivityItemEnemigoBinding
import com.example.isaacsarchive.databinding.ActivityItemObjetoBinding

class ItemEnemigoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemEnemigoBinding
    private lateinit var adaptadorComentario: AdaptadorComentario
    private lateinit var ventanaObjeto: Intent

    private lateinit var twUsuario: TextView
    private lateinit var twTitulo: TextView
    private lateinit var twDescripcion: TextView
    private lateinit var twTipo: TextView
    private lateinit var cbDesbloqueado: TextView
    private lateinit var imgEnemigo: ImageView
    private lateinit var imgVolver: ImageView
    private lateinit var etComentario: EditText

    private lateinit var usuario: String
    private lateinit var enemigo: Enemigo
    private var listaComentarios = mutableListOf<Comentario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemEnemigoBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        adaptadorComentario = AdaptadorComentario(listaComentarios, usuario)
        setContentView(binding.root)
        setupRecyclerView()

        usuario = intent.getStringExtra("usuario").toString()
        enemigo = intent.getSerializableExtra("enemigo") as Enemigo

        twUsuario = findViewById(R.id.twPerfil)
        twTitulo = findViewById(R.id.twTituloEnemigo)
        twDescripcion = findViewById(R.id.twDescripcionEnemigo)
        twTipo = findViewById(R.id.twTipoEnemigo)
        cbDesbloqueado = findViewById(R.id.cbDesbloqueadoEnemigo)
        imgEnemigo = findViewById(R.id.imgEnemigo)
        imgVolver = findViewById(R.id.imgVolver)
        etComentario = findViewById(R.id.etComentario)

        twUsuario.text = usuario

        ventanaObjeto = Intent(this, PrincipalEnemigosActivity::class.java)

        llenarEnemigo()
        llenarComentarios()
    }

    fun abrirObjetos(v: View?) {
        ventanaObjeto.putExtra("usuario", usuario)
        startActivity(ventanaObjeto)
    }

    fun volver(v: View?) {
        finish()
    }

    fun anadirComentario(v: View?) {
        val comentario = etComentario.text.toString()
        val idUsuario = consultarIdUsuario(usuario)
        val idEnemigo = consultarIdEnemigo(enemigo.nombre)

        if (comentario.isNotEmpty()) {
            enviarComentario(comentario, idUsuario, idEnemigo)
            etComentario.text.clear()
        } else {
            Toast.makeText(this, "El comentario no puede estar vacío.", Toast.LENGTH_SHORT)
        }
    }

    private fun enviarComentario(comentario: String, idUsuario: Int, idEnemigo: Int) {

        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val bd = admin.writableDatabase

        try {
            val valores = ContentValues().apply {
                put("id_usuario", idUsuario)
                put("id_enemigo", idEnemigo)
                put("comentario", comentario)
                put("fecha", System.currentTimeMillis())
            }
            val id = bd.insert("Comentarios", null, valores)
            if (id == -1L) {
                Toast.makeText(this, "Error al agregar el comentario", Toast.LENGTH_SHORT).show()
            } else {
                val comentario = Comentario(idUsuario, idEnemigo, null, comentario, System.currentTimeMillis())
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

    private fun consultarIdEnemigo(nombre: String): Int {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.writableDatabase
        try {
            val cursor = db.rawQuery("SELECT id FROM Enemigos WHERE nombre = ?", arrayOf(nombre))
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

    private fun consultarIdUsuario(usuario: String): Int {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.writableDatabase
        try {
             val cursor = db.rawQuery("SELECT id FROM Usuarios WHERE usuario = ?", arrayOf(usuario))
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

    private fun llenarComentarios() {
        val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = admin.readableDatabase
        try {
            if (consultarIdEnemigo(enemigo.nombre) != null) {
                val cursor = db.rawQuery("SELECT id_usuario, id_objeto, id_enemigo, comentario, fecha FROM Comentarios WHERE id_enemigo = ?", arrayOf(consultarIdEnemigo(enemigo.nombre).toString()))
                if (cursor.moveToFirst()) {
                    do {
                        val id_usuario = cursor.getInt(0)
                        val id_objeto = if (!cursor.isNull(1)) cursor.getInt(1) else null
                        val id_enemigo = cursor.getInt(2)
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

    private fun llenarEnemigo() {
        twTitulo.text = enemigo.nombre
        twDescripcion.text = enemigo.descripcion
        cbDesbloqueado.isSelected = enemigo.desbloqueado

        val nombreImagen = obtenerRuta(enemigo.nombre)
        val idImagen = imgEnemigo.context.resources.getIdentifier(nombreImagen, "drawable", imgEnemigo.context.packageName)
        imgEnemigo.setImageResource(idImagen)
    }

    private fun obtenerRuta(nombre: String): String {
        return nombre.lowercase().replace("-", "").replace("'", "")
    }

    private fun setupRecyclerView() {
        binding.rvListaComentarios.layoutManager = LinearLayoutManager(this)
        binding.rvListaComentarios.adapter = adaptadorComentario
    }
}