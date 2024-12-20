package com.example.isaacsarchive

import Clases.Objeto
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.isaacsarchive.Items.ItemObjetoActivity

class AdaptadorObjeto(
    var listaObjetos: ArrayList<Objeto>,
    var usuario: String
): RecyclerView.Adapter<AdaptadorObjeto.ViewHolderObjeto>() {
    private lateinit var ventanaObjeto: Intent

    class ViewHolderObjeto(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.twUsuarioComentario)
        val img = itemView.findViewById<ImageView>(R.id.imgLogoLista)
        val rareza = itemView.findViewById<TextView>(R.id.twRareza)
        val descripcion = itemView.findViewById<TextView>(R.id.twDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderObjeto {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_objeto, parent, false)
        return ViewHolderObjeto(vista)
    }

    override fun onBindViewHolder(holder: ViewHolderObjeto, position: Int) {
        val objeto = listaObjetos[position]

        holder.nombre.text = objeto.nombre
        holder.rareza.text = objeto.rareza
        holder.descripcion.text = objeto.descripcion

        val nombreImagen = obtenerRuta(objeto.nombre)
        val idImagen = holder.itemView.context.resources.getIdentifier(nombreImagen, "drawable", holder.img.context.packageName)
        holder.img.setImageResource(idImagen)

        holder.img.setOnClickListener {
            ventanaObjeto = Intent(holder.img.context, ItemObjetoActivity::class.java)
            ventanaObjeto.putExtra("usuario", usuario)
            ventanaObjeto.putExtra("objeto", objeto)
            holder.img.context.startActivity(ventanaObjeto)
        }
    }

    override fun getItemCount(): Int {
        return listaObjetos.size
    }

    fun obtenerRuta(nombre: String): String {
        if (nombre.lowercase().equals("gemini")) {
            return "gemini_objeto"
        } else {
            return nombre.lowercase().replace(" ", "_").replace("'", "").replace(".", "").replace("-", "_")
        }
    }

    fun filtar(listaFiltrada: ArrayList<Objeto>) {
        this.listaObjetos = listaFiltrada
        notifyDataSetChanged()

    }
}