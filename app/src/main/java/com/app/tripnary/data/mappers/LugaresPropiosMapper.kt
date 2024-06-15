package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.LugarPropioEntity
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.TaskModel

object LugaresPropiosMapper {

    fun LugarPropioModel.lugarEntityFromModel(): LugarPropioEntity =
        LugarPropioEntity(
            reference = reference,
            estado = estado,
            idCiudad = idCiudad,
            imagen = imagen,
            latitud = latitud,
            longitud = longitud,
            nombre = nombre,
            direccion = direccion,
            url = url
        )

    fun List<LugarPropioEntity>.toLugarPropioModelList(): List<LugarPropioModel> = this.map { lugarEntity ->
        LugarPropioModel(
            reference = lugarEntity.reference,
            estado = lugarEntity.estado,
            idCiudad = lugarEntity.idCiudad,
            imagen = lugarEntity.imagen,
            latitud = lugarEntity.latitud,
            longitud = lugarEntity.longitud,
            nombre = lugarEntity.nombre,
            direccion = lugarEntity.direccion,
            url = lugarEntity.url
        )
    }

    fun List<LugarPropioModel>.toLugarEntityList(): List<LugarPropioEntity> = this.map { lugarModel ->
        LugarPropioEntity(
            reference = lugarModel.reference,
            estado = lugarModel.estado,
            idCiudad = lugarModel.idCiudad,
            imagen = lugarModel.imagen,
            latitud = lugarModel.latitud,
            longitud = lugarModel.longitud,
            nombre = lugarModel.nombre,
            direccion = lugarModel.direccion,
            url = lugarModel.url
        )
    }

}