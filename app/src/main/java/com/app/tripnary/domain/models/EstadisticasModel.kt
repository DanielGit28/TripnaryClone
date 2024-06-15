package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstadisticasModel(
    var cantidadPlanes: String,
    var cantidadPaises: String,
    var cantidadCiudades: String,
    var cantidadLugares: String
) : Parcelable
