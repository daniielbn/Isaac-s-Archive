package com.example.isaacsarchive

import Adaptadores.AdaptadorEnemigo
import Clases.AdminSQLiteOpenHelper
import Clases.Enemigo
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isaacsarchive.databinding.ActivityPrincipalEnemigosBinding

class PrincipalEnemigosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalEnemigosBinding
    private lateinit var adaptador: AdaptadorEnemigo
    private lateinit var ventanaObjetos: Intent

    var listaEnemigos = arrayListOf<Enemigo>()
    private lateinit var twPerfil: TextView
    private lateinit var usuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalEnemigosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        twPerfil = findViewById(R.id.twPerfil)
        usuario = intent.getStringExtra("usuario").toString()

        ventanaObjetos = Intent(this, PrincipalObjetosActivity::class.java)
        establecerNombreUsuario()

        llenarLista()
        setupRecyclerView()

        binding.etBuscador.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filtar(s.toString())
            }

        })

    }

    fun abrirObjetos(v: View?) {
        ventanaObjetos.putExtra("usuario", usuario)
        startActivity(ventanaObjetos)
    }

    fun establecerNombreUsuario() {
        twPerfil.setText(usuario)
    }

    fun llenarLista() {
        val dbHelper = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = dbHelper.readableDatabase

        try {
            val cursor = db.rawQuery("SELECT nombre, tipo, descripcion FROM Enemigos", null)
            if (cursor.moveToFirst()) {
                do {
                    val nombre = cursor.getString(0)
                    val tipo = cursor.getString(1)
                    val descripcion = cursor.getString(2)
                    val enemigo = Enemigo(nombre, tipo, descripcion)
                    listaEnemigos.add(enemigo)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun setupRecyclerView() {
        binding.rvLista.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorEnemigo(listaEnemigos, usuario)
        binding.rvLista.adapter = adaptador
    }

    fun filtar(texto: String) {
        var listaFiltrada = arrayListOf<Enemigo>()
        listaEnemigos.forEach {
            if (it.nombre.toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(it)
            }
        }
        adaptador.filtar(listaFiltrada)
    }

//    fun actualizarDesbloqueado(v: View?) {
//        if (v != null) {
//            val checkBox = v.findViewById<CheckBox>(R.id.cbDesbloqueado)
//            val nombre = v.findViewById<TextView>(R.id.twNombre).text.toString()
//            val desbloqueado = if (checkBox.isChecked) 1 else 0
//
//            val admin = AdminSQLiteOpenHelper(this, "IsaacsArchive100", null, 1)
//            val db = admin.writableDatabase
//            db.execSQL("UPDATE Enemigos SET desbloqueado = ? WHERE nombre = ?", arrayOf(desbloqueado, nombre))
//            db.close()
//        }
//    }
}