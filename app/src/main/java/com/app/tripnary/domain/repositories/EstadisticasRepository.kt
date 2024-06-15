package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.EstadisticasModel

interface EstadisticasRepository {

    suspend fun getEstadisticasUsuario(reference: String) : EstadisticasModel
}