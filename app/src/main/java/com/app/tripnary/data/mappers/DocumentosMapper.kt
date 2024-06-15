package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.DocumentosEntity
import com.app.tripnary.domain.models.DocumentosModel

object DocumentosMapper {

    fun DocumentosModel.documentoEntityFromModel(): DocumentosEntity =
        DocumentosEntity(
            reference = reference,
            estado = estado,
            idLugar = idLugar,
            idLugarPlan = idLugarPlan,
            idPlanViaje = idPlanViaje,
            nombre = nombre,
            url = url
        )

    fun List<DocumentosEntity>.toDocumentoModelList(): List<DocumentosModel> = this.map { documentosEntity ->
        DocumentosModel(
            reference = documentosEntity.reference,
            estado = documentosEntity.estado,
            idLugar = documentosEntity.idLugar,
            idLugarPlan = documentosEntity.idLugarPlan,
            idPlanViaje = documentosEntity.idPlanViaje,
            nombre = documentosEntity.nombre,
            url = documentosEntity.url
        )
    }

    fun List<DocumentosModel>.toDocumentoEntityList(): List<DocumentosEntity> = this.map { documentosModel ->
        DocumentosEntity(
            reference = documentosModel.reference,
            estado = documentosModel.estado,
            idLugar = documentosModel.idLugar,
            idLugarPlan = documentosModel.idLugarPlan,
            idPlanViaje = documentosModel.idPlanViaje,
            nombre = documentosModel.nombre,
            url = documentosModel.url
        )
    }
}