package com.example.isaacsarchive

import Clases.AdminSQLiteOpenHelper
import Clases.Objeto
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isaacsarchive.databinding.ActivityPrincipalObjetosBinding

class PrincipalObjetosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalObjetosBinding
    private lateinit var adaptador: AdaptadorObjeto
    private lateinit var ventanaEnemigos: Intent

    var listaObjetos = arrayListOf<Objeto>()
    private lateinit var twPerfil: TextView
    private lateinit var usuario: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalObjetosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        twPerfil = findViewById(R.id.twPerfil)
        usuario = intent.getStringExtra("usuario").toString()

        ventanaEnemigos = Intent(this, PrincipalEnemigosActivity::class.java)
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

    fun abrirEnemigos(v: View?) {
        ventanaEnemigos.putExtra("usuario", usuario)
        startActivity(ventanaEnemigos)
    }

    fun establecerNombreUsuario() {
        twPerfil.setText(usuario)
    }

    fun llenarLista() {
        val dbHelper = AdminSQLiteOpenHelper(this, "IsaacsArchive1.0", null, 1)
        val db = dbHelper.readableDatabase

        try {
            val cursor = db.rawQuery("SELECT nombre, rareza, descripcion FROM Objetos", null)
            if (cursor.moveToFirst()) {
                do {
                    val nombre = cursor.getString(0)
                    val rareza = cursor.getString(1)
                    val descripcion = cursor.getString(2)
                    val objeto = Objeto(nombre, rareza, descripcion)
                    listaObjetos.add(objeto)
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
        adaptador = AdaptadorObjeto(listaObjetos, usuario)
        binding.rvLista.adapter = adaptador
    }

    fun filtar(texto: String) {
        var listaFiltrada = arrayListOf<Objeto>()
        listaObjetos.forEach {
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
//            db.execSQL("UPDATE Objetos SET desbloqueado = ? WHERE nombre = ?", arrayOf(desbloqueado, nombre))
//            db.close()
//        }
//    }
}