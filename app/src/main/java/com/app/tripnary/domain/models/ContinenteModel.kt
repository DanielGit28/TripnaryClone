package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContinenteModel(
    var reference: String,
    var nombre: String,
    var descripcion: String,
    var codigoContinente: String,
    var estado: String
) : Parcelable
