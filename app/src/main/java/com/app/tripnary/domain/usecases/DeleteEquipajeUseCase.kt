package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.repositories.EquipajeRepository

class DeleteEquipajeUseCase (private val repository: EquipajeRepository) {

    suspend fun execute(reference: String) : EquipajeModel {
        return repository.deleteEquipaje(reference)
    }

}