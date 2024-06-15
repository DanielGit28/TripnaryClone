package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.UsuariosEntity
import com.app.tripnary.domain.models.UsuariosModel

object UsuariosMapper {
    fun UsuariosModel.noteEntityFromModel(): UsuariosEntity =
        UsuariosEntity(
            reference = reference,
            contrasennia = contrasennia,
            correoElectronico = correoElectronico,
            estado = estado,
            fechaNacimiento = fechaNacimiento,
            fotoPerfil = fotoPerfil,
            idInteresesGenerales = idInteresesGenerales,
            latitudDireccion = latitudDireccion,
            longitudDireccion = longitudDireccion,
            nombre = nombre,
            roles = roles,
            telefono = telefono
        )
    fun List<UsuariosEntity>.toUsuariosModelList(): List<UsuariosModel> = this.map { usuariosEntity ->
        UsuariosModel(
            reference = usuariosEntity.reference,
            contrasennia = usuariosEntity.contrasennia,
            correoElectronico = usuariosEntity.correoElectronico,
            estado = usuariosEntity.estado,
            fechaNacimiento = usuariosEntity.fechaNacimiento,
            fotoPerfil = usuariosEntity.fotoPerfil,
            idInteresesGenerales = usuariosEntity.idInteresesGenerales,
            latitudDireccion = usuariosEntity.latitudDireccion,
            longitudDireccion = usuariosEntity.longitudDireccion,
            nombre = usuariosEntity.nombre,
            roles = usuariosEntity.roles,
            telefono = usuariosEntity.telefono
        )
    }
    fun List<UsuariosModel>.toUsuariosEntityList(): List<UsuariosEntity> = this.map { usuariosModel ->
        UsuariosEntity(
            reference = usuariosModel.reference,
            contrasennia = usuariosModel.contrasennia,
            correoElectronico = usuariosModel.correoElectronico,
            estado = usuariosModel.estado,
            fechaNacimiento = usuariosModel.fechaNacimiento,
            fotoPerfil = usuariosModel.fotoPerfil,
            idInteresesGenerales = usuariosModel.idInteresesGenerales,
            latitudDireccion = usuariosModel.latitudDireccion,
            longitudDireccion = usuariosModel.longitudDireccion,
            nombre = usuariosModel.nombre,
            roles = usuariosModel.roles,
            telefono = usuariosModel.telefono
        )
    }
}