package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.TaskModel

object LugarPlanesMapper {

    fun LugarPlanModel.lugarPlanEntityFromModel(): LugarPlanEntity =
        LugarPlanEntity(
            reference = reference,
            completado = completado,
            horaFin = horaFin,
            horaInicio = horaInicio,
            idDia = idDia,
            idLugarPropio = idLugarPropio,
            idLugarRecomendado = idLugarRecomendado,
            idPlanViaje = idPlanViaje,
            notas = notas,
            estado = estado

        )


    fun List<LugarPlanEntity>.toLugarPlanModelList(): List<LugarPlanModel> = this.map { lugarEntity ->
        LugarPlanModel(
            reference = lugarEntity.reference,
            completado = lugarEntity.completado,
            horaFin = lugarEntity.horaFin,
            horaInicio = lugarEntity.horaInicio,
            idDia = lugarEntity.idDia,
            idLugarPropio = lugarEntity.idLugarPropio,
            idLugarRecomendado = lugarEntity.idLugarRecomendado,
            idPlanViaje = lugarEntity.idPlanViaje,
            notas = lugarEntity.notas,
            estado = lugarEntity.estado
        )
    }

    fun List<LugarPlanModel>.toLugarPlanEntityList(): List<LugarPlanEntity> = this.map { lugarModel ->
        LugarPlanEntity(
            reference = lugarModel.reference,
            completado = lugarModel.completado,
            horaFin = lugarModel.horaFin,
            horaInicio = lugarModel.horaInicio,
            idDia = lugarModel.idDia,
            idLugarPropio = lugarModel.idLugarPropio,
            idLugarRecomendado = lugarModel.idLugarRecomendado,
            idPlanViaje = lugarModel.idPlanViaje,
            notas = lugarModel.notas,
            estado = lugarModel.estado
        )
    }
}