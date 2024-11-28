package Adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import Clases.AdminSQLiteOpenHelper
import Clases.Enemigo
import androidx.core.content.ContextCompat.startActivity
import com.example.isaacsarchive.ItemEnemigoActivity
import com.example.isaacsarchive.PrincipalObjetosActivity
import com.example.isaacsarchive.R

class AdaptadorEnemigo(
    var listaEnemigos: ArrayList<Enemigo>,
    var usuario: String
): RecyclerView.Adapter<AdaptadorEnemigo.ViewHolderEnemigo>() {
    private lateinit var ventanaItemEnemigo: Intent

    class ViewHolderEnemigo(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.twUsuarioComentario)
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

        holder.img.setOnClickListener {
            ventanaItemEnemigo = Intent(holder.img.context, ItemEnemigoActivity::class.java)
            ventanaItemEnemigo.putExtra("usuario", usuario)
            ventanaItemEnemigo.putExtra("enemigo", enemigo)
            holder.img.context.startActivity(ventanaItemEnemigo)
        }

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