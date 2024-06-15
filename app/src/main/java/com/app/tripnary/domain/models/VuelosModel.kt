package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VuelosModel (
    var reference: String,
    val hora:String,
    val fecha:String,
    val fechaLlegada:String,
    val fechaSalida:String,
    val cantidadMaletas:Int,
    val categoriaVuela:String,
    val codigoAerolinea:String,
    val destino:String,
    val duracion:String,
    val estado:String,
    val idPlanViaje:String,
    val moneda:String,
    val origen:String,
    val paradas:String,
    val precio:String,
    val tipoViajero:String
): Parcelable