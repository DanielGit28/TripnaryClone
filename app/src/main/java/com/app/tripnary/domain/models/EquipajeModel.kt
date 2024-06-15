package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EquipajeModel(
    var reference: String,
    var nombre: String,
    var cantidad: String,
    var idPlanViaje: String,
    var completado: Boolean,
    var estado: String
) : Parcelable
