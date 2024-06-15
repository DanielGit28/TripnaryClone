package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ColaboradoresModel(
    var reference: String,
    var correoElectronico: String,
    var estado: String,
    var idPlanViaje: String,
    var idUsuarioColaborador: String,
    var rol: String,
    var nombre: String
): Parcelable