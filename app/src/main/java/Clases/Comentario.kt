package Clases

data class Comentario(
    var id_usuario: Int,
    var id_objeto: Int?,
    var id_enemigo: Int?,
    var comentario: String,
    var fecha: Long
)
