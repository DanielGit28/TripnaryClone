package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.ColaboradoresEntity
import com.app.tripnary.domain.models.ColaboradoresModel

object ColaboradoresMapper {

    fun ColaboradoresModel.noteEntityFromModel(): ColaboradoresEntity =
        ColaboradoresEntity(
            reference = reference,
            correoElectronico = correoElectronico,
            estado = estado,
            idPlanViaje = idPlanViaje,
            idUsuarioColaborador = idUsuarioColaborador,
            rol = rol,
            nombre = nombre
        )

    fun List<ColaboradoresEntity>.toColaboradoresModelList(): List<ColaboradoresModel> = this.map { colaboradoresEntity ->
        ColaboradoresModel(
            reference = colaboradoresEntity.reference,
            correoElectronico = colaboradoresEntity.correoElectronico,
            estado = colaboradoresEntity.estado,
            idPlanViaje = colaboradoresEntity.idPlanViaje,
            idUsuarioColaborador = colaboradoresEntity.idUsuarioColaborador,
            rol = colaboradoresEntity.rol,
            nombre = colaboradoresEntity.nombre
        )
    }

    fun List<ColaboradoresModel>.toColaboradoresEntityList(): List<ColaboradoresEntity> = this.map { colaboradoresModel ->
        ColaboradoresEntity(
            reference = colaboradoresModel.reference,
            correoElectronico = colaboradoresModel.correoElectronico,
            estado = colaboradoresModel.estado,
            idPlanViaje = colaboradoresModel.idPlanViaje,
            idUsuarioColaborador = colaboradoresModel.idUsuarioColaborador,
            rol = colaboradoresModel.rol,
            nombre = colaboradoresModel.nombre
        )
    }
}