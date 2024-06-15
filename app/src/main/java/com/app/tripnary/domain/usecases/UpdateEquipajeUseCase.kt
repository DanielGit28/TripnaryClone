package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.repositories.EquipajeRepository

class UpdateEquipajeUseCase (private val repository: EquipajeRepository) {

    suspend fun execute(equipajeModel: EquipajeModel) : EquipajeModel {
        return repository.updateEquipaje(equipajeModel)
    }

}