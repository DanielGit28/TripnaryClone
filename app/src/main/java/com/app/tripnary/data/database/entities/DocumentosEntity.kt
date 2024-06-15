package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documentos")
data class DocumentosEntity (
    @PrimaryKey() var reference: String,
    var estado: String,
    var idLugar: String,
    var idLugarPlan: String,
    var idPlanViaje: String,
    var nombre: String,
    var url: String
)