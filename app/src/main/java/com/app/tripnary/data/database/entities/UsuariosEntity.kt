package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.tripnary.data.converter.array.ArrayStringConverter

@Entity(tableName = "usuarios")
@TypeConverters(ArrayStringConverter::class)
data class UsuariosEntity(
    @PrimaryKey() var reference: String,
    var contrasennia: String,
    var correoElectronico: String,
    var estado: String,
    var fechaNacimiento: String?,
    var fotoPerfil: String,
    var idInteresesGenerales: String,
    var latitudDireccion: String,
    var longitudDireccion: String,
    var nombre: String,
    var roles: Array<String>,
    var telefono: String?
)