package Adaptadores

import Clases.AdminSQLiteOpenHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import Clases.Comentario
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.isaacsarchive.R
import java.text.SimpleDateFormat
import java.util.Date
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

    fun anadirComentario(comentario: Comentario) {
        comentarios.add(comentario)
        notifyItemInserted(comentarios.size - 1)
    }

    class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textoComentario: TextView = itemView.findViewById(R.id.twComentarioItem)
        private val fechaComentario: TextView = itemView.findViewById(R.id.twFechaComentario)
        private val usuarioComentario: TextView = itemView.findViewById(R.id.twUsuarioComentario)
        private val imgEliminar: ImageView = itemView.findViewById(R.id.imgEliminar)


        fun bind(
            comentario: Comentario,
            usuario: String,
            position: Int,
            listaComentarios: MutableList<Comentario>,
            adaptador: RecyclerView.Adapter<*>
        ) {
            usuarioComentario.text = consultarNombreUsuario(comentario.id_usuario)
            textoComentario.text = comentario.comentario
            fechaComentario.text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(comentario.fecha))
            imgEliminar.visibility = View.GONE

            // Mostrar botón eliminar solo si el usuario coincide
            if (usuario == consultarNombreUsuario(comentario.id_usuario)) {
                imgEliminar.visibility = View.VISIBLE
            }

            imgEliminar.setOnClickListener {
                val admin = AdminSQLiteOpenHelper(itemView.context, "IsaacsArchive1.0", null, 1)
                val bd = admin.writableDatabase

                try {
                    val idComentario = consultarIdComentario(comentario)
                    Log.d("EliminarComentario", "ID del comentario: $idComentario")

                    val filasAfectadas = bd.delete("comentarios", "id = ?", arrayOf(idComentario.toString()))
                    Log.d("EliminarComentario", "Filas eliminadas: $filasAfectadas")

                    if (filasAfectadas > 0) {
                        listaComentarios.removeAt(position)
                        adaptador.notifyItemRemoved(position)
                        Toast.makeText(itemView.context, "Comentario eliminado correctamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(itemView.context, "Error: No se pudo eliminar el comentario.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("EliminarComentario", "Error al eliminar el comentario", e)
                    Toast.makeText(itemView.context, "Ocurrió un error al eliminar el comentario.", Toast.LENGTH_SHORT).show()
                } finally {
                    bd.close()
                }
            }
        }


        private fun consultarIdComentario(comentario: Comentario): String {
            val admin = AdminSQLiteOpenHelper(itemView.context, "IsaacsArchive1.0", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select id from comentarios where id_usuario = ? ", arrayOf(comentario.id_usuario.toString()))
            fila.moveToFirst()
            return fila.getString(0)
        }

        fun consultarNombreUsuario(idUsuario: Int): String {
            val admin = AdminSQLiteOpenHelper(itemView.context, "IsaacsArchive1.0", null, 1)
            val db = admin.readableDatabase
            var nombreUsuario = ""

            val cursor = db.rawQuery("SELECT usuario FROM Usuarios WHERE id = ?", arrayOf(idUsuario.toString()))
            if (cursor.moveToFirst()) {
                nombreUsuario = cursor.getString(0)
            }
            cursor.close()
            db.close()

            return nombreUsuario
        }

    }

}
