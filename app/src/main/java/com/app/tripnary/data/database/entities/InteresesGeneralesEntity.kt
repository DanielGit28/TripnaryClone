package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intereses_generales")
data class InteresesGeneralesEntity (
    @PrimaryKey() var reference: String,
    var categoriaViaje: String,
    var destinoPreferido: String,
    var estado: String,
    var temporadaPreferida: String
)