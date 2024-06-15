package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.tripnary.data.converter.array.ArrayStringConverter

@Entity(tableName = "dias_planes")
@TypeConverters(ArrayStringConverter::class)
data class PlanDiasEntity(
    @PrimaryKey() var reference: String,
    var dia: Int,
    var fecha: String,
    var idPlanViaje: String,
    var estado: String
)
