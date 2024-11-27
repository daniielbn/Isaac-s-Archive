package com.example.isaacsarchive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorEnemigo(
    var listaEnemigos: ArrayList<Enemigo>
): RecyclerView.Adapter<AdaptadorEnemigo.ViewHolderEnemigo>() {

    class ViewHolderEnemigo(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.twNombre)
        val img = itemView.findViewById<ImageView>(R.id.imgLogoLista)
        val descripcion = itemView.findViewById<TextView>(R.id.twDescripcion)
        val cbDesbloqueado = itemView.findViewById<TextView>(R.id.cbDesbloqueado)
        val tipo = itemView.findViewById<TextView>(R.id.twTipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderEnemigo {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_enemigo, parent, false)
        return ViewHolderEnemigo(vista)
    }

    override fun getItemCount(): Int {
        return listaEnemigos.size
    }

    override fun onBindViewHolder(holder: ViewHolderEnemigo, position: Int) {
        val enemigo = listaEnemigos[position]

        holder.nombre.text = enemigo.nombre
        holder.descripcion.text = enemigo.descripcion
        holder.cbDesbloqueado.isSelected = enemigo.desbloqueado
        holder.tipo.text = enemigo.tipo

        val nombreImagen = obtenerRuta(enemigo.nombre)
        val idImagen = holder.img.context.resources.getIdentifier(nombreImagen, "drawable", holder.img.context.packageName)
        holder.img.setImageResource(idImagen)
    }


    fun obtenerRuta(nombre: String): String {
        var nombreFormateado = nombre.toLowerCase().replace(" ", "_").replace("'", "").replace(".", "")
        return nombreFormateado
    }

    fun filtar(listaFiltrada: ArrayList<Enemigo>) {
        this.listaEnemigos = listaFiltrada
        notifyDataSetChanged()

    }
}