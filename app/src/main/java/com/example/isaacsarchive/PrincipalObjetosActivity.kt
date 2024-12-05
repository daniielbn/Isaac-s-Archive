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
    private lateinit var ventanaPerfil: Intent
    private lateinit var db: AdminSQLiteOpenHelper

    var listaObjetos = ArrayList<Objeto>()
    private lateinit var twPerfil: TextView
    private lateinit var usuario: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalObjetosBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 7)
        setContentView(binding.root)

        listaObjetos = db.obtenerObjetos()
        setupRecyclerView()

        twPerfil = findViewById(R.id.twPerfil)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        twPerfil.text = usuario

        ventanaEnemigos = Intent(this, PrincipalEnemigosActivity::class.java)
        ventanaPerfil = Intent(this, PerfilActivity::class.java)

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

    fun abrirPerfil(v: View?) {
        ventanaPerfil.putExtra("usuario", usuario)
        startActivity(ventanaPerfil)
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
}