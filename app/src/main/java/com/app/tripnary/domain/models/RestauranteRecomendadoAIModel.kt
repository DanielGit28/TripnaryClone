package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestauranteRecomendadoAIModel (
    var reference: String,
    var nombre : String,
    var direccion : String,
    var horario : String,
    var idInteres : String,
    var rating : String,
    var estado : String,
    var comida : String,
    var dia : String
): Parcelable