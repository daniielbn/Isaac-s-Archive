package Clases

import java.io.Serializable


data class Objeto(
    var nombre: String,
    var rareza: String,
    var descripcion: String,
    var desbloqueado: Boolean = false
): Serializable
