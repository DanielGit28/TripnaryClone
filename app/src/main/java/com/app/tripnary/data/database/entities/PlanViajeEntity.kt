package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.tripnary.data.converter.array.ArrayStringConverter

@Entity(tableName = "planes_viajes")
@TypeConverters(ArrayStringConverter::class)
data class PlanViajeEntity(
    @PrimaryKey() var reference: String,
    var nombre: String,
    var imagenPortada: String,
    var idUsuario: String,
    var idPromptHotel: String,
    var idPais: String,
    var idInteresRestaurante: String,
    var idInteresLugar: String,
    var fechaInicio: String,
    var fechaFin: String,
    var estado: String
)
