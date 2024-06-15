package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.repositories.LugarPlanesRepository
import com.app.tripnary.domain.repositories.LugarPropioRepository
import com.app.tripnary.domain.repositories.LugaresRecomendadosRepository

class AddLugarPropioUseCase (private val lugarPropioRepository: LugarPropioRepository) {

    suspend fun execute(lugarPropioModel: LugarPropioModel) : LugarPropioModel {
        var lugarPropio = lugarPropioRepository.addLugarPropio(lugarPropioModel)
        return lugarPropio
    }
}