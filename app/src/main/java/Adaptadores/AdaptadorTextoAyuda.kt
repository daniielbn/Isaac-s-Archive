package Adaptadores

import Clases.TextoAyuda
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.isaacsarchive.R

class AdaptadorTextoAyuda(
    var listaTextosAyuda: ArrayList<TextoAyuda>
): RecyclerView.Adapter<AdaptadorTextoAyuda.ViewHolderTextoAyuda>() {
    class ViewHolderTextoAyuda(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.findViewById<TextView>(R.id.twTituloAyuda)
        val texto = itemView.findViewById<TextView>(R.id.twDescripcionAyuda)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTextoAyuda {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_texto_ayuda, parent, false)
        return ViewHolderTextoAyuda(vista)
    }

    override fun getItemCount(): Int {
        return listaTextosAyuda.size
    }

    override fun onBindViewHolder(holder: ViewHolderTextoAyuda, position: Int) {
        val textoAyuda = listaTextosAyuda[position]

        holder.titulo.text = textoAyuda.titulo
        holder.texto.text = textoAyuda.texto
    }
}