package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TiquetesModel(
    var reference: String,
    var categoria: String,
    var correoElectronico: String,
    var estado: String,
    var fechaDeCreacion: String,
    var idUsuario: String,
    var mensajeAdmin: String,
    var mensajeUsuario: String,
    var nombreCompleto: String,
) : Parcelable