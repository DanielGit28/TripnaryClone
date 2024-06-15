package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTask(): Flow<List<TaskModel>>

    suspend fun addTask(task: TaskModel)



}