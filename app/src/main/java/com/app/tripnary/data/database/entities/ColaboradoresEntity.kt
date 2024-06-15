package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colaboradores_viajes")
data class ColaboradoresEntity(
    @PrimaryKey() var reference: String,
    var correoElectronico: String,
    var estado: String,
    var idPlanViaje: String,
    var idUsuarioColaborador: String,
    var rol: String,
    var nombre: String
)