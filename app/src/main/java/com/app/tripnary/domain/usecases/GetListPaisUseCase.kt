package com.app.tripnary.domain.usecases

import android.util.Log
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.PaisRepository
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListPaisUseCase(private val paisRepository: PaisRepository) {

    fun execute(): Flow<List<PaisModel>> =
        paisRepository.getAllPaises()
            .map { pais -> pais.sortedBy { pais -> pais.reference } }
}