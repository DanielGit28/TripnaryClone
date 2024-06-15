package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares_planes")
data class LugarPlanEntity(
    @PrimaryKey() var reference: String,
    var completado: Boolean,
    var horaFin: String,
    var horaInicio: String,
    var idDia: String,
    var idLugarPropio: String,
    var idLugarRecomendado: String,
    var idPlanViaje: String,
    var notas: String,
    var estado: String,
)
