package com.example.isaacsarchive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorObjeto(
    var listaObjetos: ArrayList<Objeto>
): RecyclerView.Adapter<AdaptadorObjeto.ViewHolderObjeto>() {

    class ViewHolderObjeto(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.twNombre)
        val img = itemView.findViewById<ImageView>(R.id.imgLogoLista)
        val descripcion = itemView.findViewById<TextView>(R.id.twDescripcion)
        val cbDesbloqueado = itemView.findViewById<TextView>(R.id.cbDesbloqueado)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderObjeto {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_objeto, parent, false)
        return ViewHolderObjeto(vista)
    }

    override fun onBindViewHolder(holder: ViewHolderObjeto, position: Int) {
        val objeto = listaObjetos[position]

        holder.nombre.text = objeto.nombre
        holder.descripcion.text = objeto.descripcion
        holder.cbDesbloqueado.isSelected = objeto.desbloqueado

        val nombreImagen = obtenerRuta(objeto.nombre)
        val idImagen = holder.img.context.resources.getIdentifier(nombreImagen, "drawable", holder.img.context.packageName)
        holder.img.setImageResource(idImagen)

    }

    override fun getItemCount(): Int {
        return listaObjetos.size
    }

    fun obtenerRuta(nombre: String): String {
        var nombreMinusculas = nombre.toLowerCase()
        var nombreSeparado = nombreMinusculas.replace(" ", "_")
        var nombreFinal = nombreSeparado.replace("'", "")
        return nombreFinal
    }

    fun filtar(listaFiltrada: ArrayList<Objeto>) {
        this.listaObjetos = listaFiltrada
        notifyDataSetChanged()

    }
}