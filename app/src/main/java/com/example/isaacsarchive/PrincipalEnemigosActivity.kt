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
    private lateinit var ventanaPerfil: Intent
    private lateinit var db: AdminSQLiteOpenHelper

    var listaEnemigos = ArrayList<Enemigo>()
    private lateinit var twPerfil: TextView
    private lateinit var usuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalEnemigosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = intent.getStringExtra("usuario").toString()
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 8)
        listaEnemigos = db.obtenerEnemigos()
        setupRecyclerView()

        twPerfil = findViewById(R.id.twPerfil)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        twPerfil.text = usuario

        ventanaObjetos = Intent(this, PrincipalObjetosActivity::class.java)
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

    fun abrirObjetos(v: View?) {
        ventanaObjetos.putExtra("usuario", usuario)
        startActivity(ventanaObjetos)
    }

    fun abrirPerfil(v: View?) {
        ventanaPerfil.putExtra("usuario", usuario)
        startActivity(ventanaPerfil)
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
}