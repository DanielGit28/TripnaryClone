package com.app.tripnary.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class InteresLugaresAIModel (
    var reference: String,
    var nombreCiudad : String,
    var presupuesto : String,
    var lugarPreferido : String,
    var cantidadDias : String,
    var estado : String,
    var idPrompts : String
): Parcelable