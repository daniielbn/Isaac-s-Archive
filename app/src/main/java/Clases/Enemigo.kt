package Clases

import java.io.Serializable

data class Enemigo(
    var nombre: String,
    var tipo: String,
    var descripcion: String,
    var desbloqueado: Boolean = false
): Serializable
