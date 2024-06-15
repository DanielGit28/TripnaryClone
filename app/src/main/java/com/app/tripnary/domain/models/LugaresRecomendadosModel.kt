package com.app.tripnary.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LugaresRecomendadosModel (
    var reference: String,
    var categoriaViaje : String,
    var codigoPostal: String,
    var descripcion: String,
    var estado: String,
    var horaFinal: String,
    var horaInicial: String,
    var idCiudad: String,
    var imagen: String,
    var imagenCover: String,
    var latitud: String,
    var longitud: String,
    var nombre: String,
    var puntuacion: String,
    var temporada: String,
    var url: String
): Parcelable