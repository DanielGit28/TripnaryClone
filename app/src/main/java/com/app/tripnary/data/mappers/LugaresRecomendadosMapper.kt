package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import com.app.tripnary.domain.models.LugaresRecomendadosModel

object LugaresRecomendadosMapper {
    fun LugaresRecomendadosModel.noteEntityFromModel(): LugaresRecomendadosEntity =
        LugaresRecomendadosEntity(
            reference = reference,
            categoriaViaje = categoriaViaje,
            codigoPostal = codigoPostal,
            descripcion = descripcion,
            estado = estado,
            horaFinal = horaFinal,
            horaInicial = horaInicial,
            idCiudad = idCiudad,
            imagen = imagen,
            imagenCover = imagenCover,
            latitud = latitud,
            longitud = longitud,
            nombre = nombre,
            puntuacion = puntuacion,
            temporada = temporada,
            url = url
        )

    fun List<LugaresRecomendadosEntity>.toLugaresRecomendadosModelList(): List<LugaresRecomendadosModel> = this.map { lugaresRecomendadosEntity ->
        LugaresRecomendadosModel(
            reference = lugaresRecomendadosEntity.reference,
            categoriaViaje = lugaresRecomendadosEntity.categoriaViaje,
            codigoPostal = lugaresRecomendadosEntity.codigoPostal,
            descripcion = lugaresRecomendadosEntity.descripcion,
            estado = lugaresRecomendadosEntity.estado,
            horaFinal = lugaresRecomendadosEntity.horaFinal,
            horaInicial = lugaresRecomendadosEntity.horaInicial,
            idCiudad = lugaresRecomendadosEntity.idCiudad,
            imagen = lugaresRecomendadosEntity.imagen,
            imagenCover = lugaresRecomendadosEntity.imagenCover,
            latitud = lugaresRecomendadosEntity.latitud,
            longitud = lugaresRecomendadosEntity.longitud,
            nombre = lugaresRecomendadosEntity.nombre,
            puntuacion = lugaresRecomendadosEntity.puntuacion,
            temporada = lugaresRecomendadosEntity.temporada,
            url = lugaresRecomendadosEntity.url
        )
    }

    fun List<LugaresRecomendadosModel>.toLugaresRecomendadosEntityList(): List<LugaresRecomendadosEntity> = this.map { lugaresRecomendadosModel ->
        LugaresRecomendadosEntity(
            reference = lugaresRecomendadosModel.reference,
            categoriaViaje = lugaresRecomendadosModel.categoriaViaje,
            codigoPostal = lugaresRecomendadosModel.codigoPostal,
            descripcion = lugaresRecomendadosModel.descripcion,
            estado = lugaresRecomendadosModel.estado,
            horaFinal = lugaresRecomendadosModel.horaFinal,
            horaInicial = lugaresRecomendadosModel.horaInicial,
            idCiudad = lugaresRecomendadosModel.idCiudad,
            imagen = lugaresRecomendadosModel.imagen,
            imagenCover = lugaresRecomendadosModel.imagenCover,
            latitud = lugaresRecomendadosModel.latitud,
            longitud = lugaresRecomendadosModel.longitud,
            nombre = lugaresRecomendadosModel.nombre,
            puntuacion = lugaresRecomendadosModel.puntuacion,
            temporada = lugaresRecomendadosModel.temporada,
            url = lugaresRecomendadosModel.url
        )
    }
}