package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.PlanViajeEntity
import com.app.tripnary.data.database.entities.PlanesViajesColaboradorEntity
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel

object PlanViajeMapper {

    fun PlanViajeModel.noteEntityFromModel(): PlanViajeEntity =
        PlanViajeEntity(
            reference = reference,
            nombre = nombre,
            imagenPortada = imagenPortada,
            idUsuario = idUsuario,
            idPromptHotel = idPromptHotel,
            idPais = idPais,
            idInteresRestaurante = idInteresRestaurante,
            idInteresLugar = idInteresLugar,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            estado = estado
        )

    fun List<PlanViajeEntity>.toPlanModelList(): List<PlanViajeModel> = this.map { planEntity ->
        PlanViajeModel(
            reference = planEntity.reference,
            nombre = planEntity.nombre,
            imagenPortada = planEntity.imagenPortada,
            idUsuario = planEntity.idUsuario,
            idPromptHotel = planEntity.idPromptHotel,
            idPais = planEntity.idPais,
            idInteresRestaurante = planEntity.idInteresRestaurante,
            idInteresLugar = planEntity.idInteresLugar,
            fechaInicio = planEntity.fechaInicio,
            fechaFin = planEntity.fechaFin,
            estado = planEntity.estado
        )
    }
    fun List<PlanesViajesColaboradorEntity>.toPlanColaboradoresModelList(): List<PlanViajeModel> = this.map { planEntity ->
        PlanViajeModel(
            reference = planEntity.reference,
            nombre = planEntity.nombre,
            imagenPortada = planEntity.imagenPortada,
            idUsuario = planEntity.idUsuario,
            idPromptHotel = planEntity.idPromptHotel,
            idPais = planEntity.idPais,
            idInteresRestaurante = planEntity.idInteresRestaurante,
            idInteresLugar = planEntity.idInteresLugar,
            fechaInicio = planEntity.fechaInicio,
            fechaFin = planEntity.fechaFin,
            estado = planEntity.estado
        )
    }
    fun List<PlanViajeModel>.toPlanEntityList(): List<PlanViajeEntity> = this.map { planModel ->
        PlanViajeEntity(
            reference = planModel.reference,
            nombre = planModel.nombre,
            imagenPortada = planModel.imagenPortada,
            idUsuario = planModel.idUsuario,
            idPromptHotel = planModel.idPromptHotel,
            idPais = planModel.idPais,
            idInteresRestaurante = planModel.idInteresRestaurante,
            idInteresLugar = planModel.idInteresLugar,
            fechaInicio = planModel.fechaInicio,
            fechaFin = planModel.fechaFin,
            estado = planModel.estado
        )
    }

    fun List<PlanViajeModel>.toPlanColaboradoresEntityList(): List<PlanesViajesColaboradorEntity> = this.map { planModel ->
        PlanesViajesColaboradorEntity(
            reference = planModel.reference,
            nombre = planModel.nombre,
            imagenPortada = planModel.imagenPortada,
            idUsuario = planModel.idUsuario,
            idPromptHotel = planModel.idPromptHotel,
            idPais = planModel.idPais,
            idInteresRestaurante = planModel.idInteresRestaurante,
            idInteresLugar = planModel.idInteresLugar,
            fechaInicio = planModel.fechaInicio,
            fechaFin = planModel.fechaFin,
            estado = planModel.estado
        )
    }
}