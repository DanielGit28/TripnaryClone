package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugarPropioRepository
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListLugaresPropiosPerfilUseCase (private val lugaresPropioRepository: LugarPropioRepository){

    fun execute(idPlanViajeLugar: String): Flow<List<LugarPropioModel>> =
        lugaresPropioRepository.getAllPlanesLugaresByPropios(idPlanViajeLugar)
            .map { plan -> plan.sortedBy { plan -> plan.reference } }
}