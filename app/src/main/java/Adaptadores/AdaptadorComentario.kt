package Adaptadores

import Clases.AdminSQLiteOpenHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import Clases.Comentario
import android.widget.ImageView
import android.widget.Toast
import com.example.isaacsarchive.R
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdaptadorComentario(private val comentarios: MutableList<Comentario>, private val usuario: String) :
    RecyclerView.Adapter<AdaptadorComentario.ComentarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comentario, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = comentarios[position]
        holder.bind(comentario, usuario, position, comentarios, this)
    }

    override fun getItemCount(): Int = comentarios.size

    class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textoComentario: TextView = itemView.findViewById(R.id.twComentarioItem)
        private val fechaComentario: TextView = itemView.findViewById(R.id.twFechaComentario)
        private val usuarioComentario: TextView = itemView.findViewById(R.id.twUsuarioComentario)
        private val imgEliminar: ImageView = itemView.findViewById(R.id.imgEliminar)
        private val db = AdminSQLiteOpenHelper(itemView.context, "IsaacsArchive", null, 8)


        fun bind(
            comentario: Comentario,
            usuario: String,
            position: Int,
            listaComentarios: MutableList<Comentario>,
            adaptador: RecyclerView.Adapter<*>
        ) {
            usuarioComentario.text = db.consultarNombreUsuario(comentario.id_usuario)
            textoComentario.text = comentario.comentario
            fechaComentario.text = getFechaFormateada(comentario.fecha)
            imgEliminar.visibility = View.GONE

            // Mostrar botón eliminar solo si el usuario coincide
            if (usuario == db.consultarNombreUsuario(comentario.id_usuario)) {
                imgEliminar.visibility = View.VISIBLE
            }

            imgEliminar.setOnClickListener {
                if (db.eliminarComentario(comentario)) {
                    listaComentarios.removeAt(position)
                    adaptador.notifyItemRemoved(position)
                    Toast.makeText(itemView.context, "Comentario eliminado correctamente.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(itemView.context, "Error: No se pudo eliminar el comentario.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getFechaFormateada(fecha: String): String {
            val formato =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return fecha.format(formato)
        }
    }
}
