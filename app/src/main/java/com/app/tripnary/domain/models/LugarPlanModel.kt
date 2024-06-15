package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LugarPlanModel (
    var reference: String,
    var completado: Boolean,
    var horaFin: String,
    var horaInicio: String,
    var idDia: String,
    var idLugarPropio: String,
    var idLugarRecomendado: String,
    var idPlanViaje: String,
    var notas: String,
    var estado: String,
) : Parcelable
