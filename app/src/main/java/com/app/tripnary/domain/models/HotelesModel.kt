package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class HotelesModel (
    var reference: String,
    var nombre: String,
    var estado: String,
    var puntuacion: String,
    var longitud: String,
    var latitud: String,
    var imagen: String,
    var maps: String,
): Parcelable