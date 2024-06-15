package com.app.tripnary.domain.usecases

import android.util.Log
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListUseCase (private val taskRepository: TaskRepository) {

    fun execute(): Flow<List<TaskModel>> =
        taskRepository.getAllTask()
            .map { task -> task.sortedBy { task -> task.id } }



}