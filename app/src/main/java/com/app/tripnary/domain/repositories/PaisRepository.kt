package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface PaisRepository {

    fun getAllPaises(): Flow<List<PaisModel>>
}