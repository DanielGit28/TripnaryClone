package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LugarPropioModel (
    var reference: String,
    var nombre: String,
    var direccion: String,
    var estado: String,
    var idCiudad: String,
    var imagen: String,
    var latitud: String,
    var longitud: String,
    var url: String
): Parcelable
