package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.InteresesGeneralesEntity
import com.app.tripnary.domain.models.InteresesGeneralesModel

object InteresesGeneralesMapper {

    fun InteresesGeneralesModel.noteEntityFromModel(): InteresesGeneralesEntity =
        InteresesGeneralesEntity(
            reference = reference,
            categoriaViaje = categoriaViaje,
            destinoPreferido = destinoPreferido,
            estado = estado,
            temporadaPreferida = temporadaPreferida
        )
}