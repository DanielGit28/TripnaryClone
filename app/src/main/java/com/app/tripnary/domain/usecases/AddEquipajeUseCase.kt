package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.EquipajeRepository
import com.app.tripnary.domain.repositories.PlanViajeRepository

class AddEquipajeUseCase (private val repository: EquipajeRepository) {

    suspend fun execute(equipajeModel: EquipajeModel) : EquipajeModel {
        return repository.addEquipaje(equipajeModel)
    }

}