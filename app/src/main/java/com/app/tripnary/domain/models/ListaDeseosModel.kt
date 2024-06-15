package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListaDeseosModel (
    var reference: String,
    var nombre: String,
    var descripcion: String,
    var direccion: String,
    var imagen: String,
    var estado: String,
    var idUsuario: String,
    var idLugar: String,
    var categoriaViaje: String,
) : Parcelable