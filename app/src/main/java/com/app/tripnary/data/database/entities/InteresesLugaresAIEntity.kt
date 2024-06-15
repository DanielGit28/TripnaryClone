package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares_recomendados_ai")
class InteresesLugaresAIEntity (
    @PrimaryKey() var reference: String,
    var nombreCiudad : String,
    var presupuesto : String,
    var lugarPreferido : String,
    var cantidadDias : String,
    var estado : String,
    var idPrompts : String
)