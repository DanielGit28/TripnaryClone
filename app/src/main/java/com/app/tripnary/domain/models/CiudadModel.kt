package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CiudadModel(
    var reference: String,
    var nombre: String,
    var descripcion: String,
    var codigoCiudad: String,
    var imagen: String,
    var idPais: String,
    var latitud: String,
    var longitud: String,
    var estado: String
) : Parcelable
