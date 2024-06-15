package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares_propios")
data class LugarPropioEntity(
    @PrimaryKey() var reference: String,
    var nombre: String,
    var direccion: String,
    var estado: String,
    var idCiudad: String,
    var imagen: String,
    var latitud: String,
    var longitud: String,
    var url: String
)
