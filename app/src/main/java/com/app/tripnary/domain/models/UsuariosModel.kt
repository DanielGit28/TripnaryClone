package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsuariosModel(
    var reference: String,
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
) : Parcelable