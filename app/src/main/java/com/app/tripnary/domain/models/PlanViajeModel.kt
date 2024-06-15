package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanViajeModel(
    var reference: String,
    var nombre: String,
    var imagenPortada: String,
    var idUsuario: String,
    var idPromptHotel: String,
    var idPais: String,
    var idInteresRestaurante: String,
    var idInteresLugar: String,
    var fechaInicio: String,
    var fechaFin: String,
    var estado: String
) : Parcelable