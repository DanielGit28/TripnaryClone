package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.TaskDao
import com.app.tripnary.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

class DatabaseTaskDataSource (private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getTasks()

    suspend fun addTask(noteEntity: TaskEntity):Long =
        taskDao.insert(noteEntity)

    suspend fun insertTasks(tasks: List<TaskEntity>) {
        taskDao.insertAll(tasks)
    }

    suspend fun clearTasks() {
        taskDao.clearTasks()
    }
}