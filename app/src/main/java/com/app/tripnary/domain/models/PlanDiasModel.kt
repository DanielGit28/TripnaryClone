package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanDiasModel(
    var reference: String,
    var dia: Int,
    var fecha: String,
    var idPlanViaje: String,
    var estado: String
) : Parcelable
