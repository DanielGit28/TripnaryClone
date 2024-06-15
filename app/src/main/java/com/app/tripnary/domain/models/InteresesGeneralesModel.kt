package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InteresesGeneralesModel (
    var reference: String,
    var categoriaViaje: String,
    var destinoPreferido: String,
    var estado: String,
    var temporadaPreferida: String
) : Parcelable