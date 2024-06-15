package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.repositories.ColaboradoresRepository
import com.app.tripnary.domain.repositories.EquipajeRepository
import com.app.tripnary.domain.repositories.LugarPropioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListEquipajeUseCase (private val equipajeRepository: EquipajeRepository){

    fun execute(idPlanViaje: String): Flow<List<EquipajeModel>> =
        equipajeRepository.getAllEquipajeByPlan(idPlanViaje)
            .map { equipaje -> equipaje.sortedBy { equipaje -> equipaje.reference } }
}