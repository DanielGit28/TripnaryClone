package com.app.tripnary.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class LugarRecomendadoAIModel (
    var reference: String,
    var nombre : String,
    var direccion : String,
    var horario : String,
    var idInteresLugar : String,
    var precio : String,
    var estado : String,
    var dia : String
): Parcelable