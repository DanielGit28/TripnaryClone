package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListLugaresRecomendadosPerfilUseCase (private val lugaresRecomendadosRepository: LugaresRecomendadosRepository){

    fun execute(idPlanViajeLugarRecomendado: String): Flow<List<LugaresRecomendadosModel>> =
        lugaresRecomendadosRepository.getAllPlanesLugaresByRecomendados(idPlanViajeLugarRecomendado)
            .map { plan -> plan.sortedBy { plan -> plan.reference } }
}