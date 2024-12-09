package com.example.isaacsarchive.Principales

import BaseActivity.BaseActivity
import Clases.AdminSQLiteOpenHelper
import Clases.Objeto
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isaacsarchive.AdaptadorObjeto
import com.example.isaacsarchive.Perfil.PerfilActivity
import com.example.isaacsarchive.R
import com.example.isaacsarchive.databinding.ActivityPrincipalObjetosBinding
import java.util.Locale

class PrincipalObjetosActivity : BaseActivity() {
    private lateinit var binding: ActivityPrincipalObjetosBinding
    private lateinit var adaptador: AdaptadorObjeto
    private lateinit var ventanaEnemigos: Intent
    private lateinit var ventanaPerfil: Intent
    private lateinit var db: AdminSQLiteOpenHelper
    private lateinit var imgMicrofono: ImageView

    var listaObjetos = ArrayList<Objeto>()
    private lateinit var twPerfil: TextView
    private lateinit var usuario: String

    private lateinit var preferencias: SharedPreferences

    private val SPEECH_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalObjetosBinding.inflate(layoutInflater)
        usuario = intent.getStringExtra("usuario").toString()
        db = AdminSQLiteOpenHelper(this, "IsaacsArchive", null, 8)
        setContentView(binding.root)

        listaObjetos = db.obtenerObjetos()
        setupRecyclerView()

        preferencias = getSharedPreferences("preferencias_usuario", MODE_PRIVATE)

        twPerfil = findViewById(R.id.twPerfil)
        twPerfil.contentDescription = "Nombre de perfil de usuario"
        twPerfil.text = usuario

        imgMicrofono = findViewById(R.id.imgMicrofonoObjeto)
        imgMicrofono.contentDescription = "Botón de búsqueda por voz"

        establecerImagenMicrofono()

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

    fun iniciarReconocimientoVoz(v: View?) {
        startSpeechToText()
    }

    private fun setupRecyclerView() {
        binding.rvLista.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorObjeto(listaObjetos, usuario)
        binding.rvLista.adapter = adaptador
    }

    private fun filtar(texto: String) {
        var listaFiltrada = arrayListOf<Objeto>()
        listaObjetos.forEach {
            if (it.nombre.toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(it)
            }
        }
        adaptador.filtar(listaFiltrada)
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora para transcribir tu voz")
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "El reconocimiento de voz no está disponible",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                filtar(it[0])
            }
        }
    }

    private fun establecerImagenMicrofono() {
        if (preferencias.getString("tema", "").equals("claro")) {
            imgMicrofono.setImageResource(R.drawable.microfono_claro)
        } else {
            imgMicrofono.setImageResource(R.drawable.microfono_oscuro)
        }
    }
}