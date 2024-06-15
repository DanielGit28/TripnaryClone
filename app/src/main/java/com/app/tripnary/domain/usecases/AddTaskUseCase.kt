package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.TaskRepository

class AddTaskUseCase (private val repository: TaskRepository) {

    suspend fun execute(taskModel: TaskModel) {
        repository.addTask(taskModel)
    }
}