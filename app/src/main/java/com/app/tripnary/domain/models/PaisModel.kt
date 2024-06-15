package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaisModel(
    var reference: String,
    var nombre: String,
    var descripcion: String,
    var latitud: String,
    var longitud: String,
    var imagen: String,
    var idContinente: String,
    var codigoPais: String,
    var estado: String
) : Parcelable
