package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InteresesRestaurantesAIModel (
    var reference: String,
    var nombreCiudad : String,
    var cantidadDias : String,
    var tipoComida : String,
    var estado : String,
    var idPrompts : String,
): Parcelable