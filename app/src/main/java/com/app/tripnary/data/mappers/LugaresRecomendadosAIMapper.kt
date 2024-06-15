package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.InteresesLugaresAIEntity
import com.app.tripnary.domain.models.LugarRecomendadoAIModel
import com.app.tripnary.domain.models.InteresLugaresAIModel


object LugaresRecomendadosAIMapper {
    fun InteresLugaresAIModel.noteEntityFromModel(): InteresesLugaresAIEntity =
        InteresesLugaresAIEntity(
            reference = reference,
            nombreCiudad = nombreCiudad,
            lugarPreferido = lugarPreferido,
            estado = estado,
            idPrompts = idPrompts,
            cantidadDias = cantidadDias,
            presupuesto = presupuesto
        )

    fun List<InteresesLugaresAIEntity>.toLugaresRecomendadosAIModelList(): List<InteresLugaresAIModel> = this.map { lugaresRecomendadosAIEntity ->
        InteresLugaresAIModel(
            reference = lugaresRecomendadosAIEntity.reference,
            nombreCiudad = lugaresRecomendadosAIEntity.nombreCiudad,
            presupuesto = lugaresRecomendadosAIEntity.presupuesto,
            lugarPreferido = lugaresRecomendadosAIEntity.lugarPreferido,
            cantidadDias = lugaresRecomendadosAIEntity.cantidadDias,
            estado = lugaresRecomendadosAIEntity.estado,
            idPrompts = lugaresRecomendadosAIEntity.idPrompts
        )
    }

    fun List<InteresLugaresAIModel>.toLugaresRecomendadosAIEntityList(): List<InteresesLugaresAIEntity> = this.map { lugaresRecomendadosAIModel ->
        InteresesLugaresAIEntity(
            reference = lugaresRecomendadosAIModel.reference,
            nombreCiudad = lugaresRecomendadosAIModel.nombreCiudad,
            lugarPreferido = lugaresRecomendadosAIModel.lugarPreferido,
            presupuesto = lugaresRecomendadosAIModel.presupuesto,
            cantidadDias = lugaresRecomendadosAIModel.cantidadDias,
            estado = lugaresRecomendadosAIModel.estado,
            idPrompts = lugaresRecomendadosAIModel.idPrompts
        )
    }
}