package com.app.tripnary.domain.usecases

import com.app.tripnary.data.repositories.TiquetesRepositoryImpl
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.models.TiquetesModel
import com.app.tripnary.domain.repositories.TaskRepository
import com.app.tripnary.domain.repositories.TiquetesRepository

class AddTiqueteUseCase (private val repository: TiquetesRepositoryImpl) {
    suspend fun execute(tiquete: TiquetesModel) {
        repository.addTiquete(tiquete)
    }
}