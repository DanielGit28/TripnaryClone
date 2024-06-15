package com.app.tripnary.domain.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentosModel (
    var reference: String,
    var estado: String,
    var idLugar: String,
    var idLugarPlan: String,
    var idPlanViaje: String,
    var nombre: String,
    var url: String
): Parcelable