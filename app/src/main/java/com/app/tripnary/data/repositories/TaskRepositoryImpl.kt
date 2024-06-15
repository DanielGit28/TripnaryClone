package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.TaskServiceImpl
import com.app.tripnary.data.datasources.DatabaseTaskDataSource
import com.app.tripnary.data.mappers.TaskMapper.noteEntityFromModel
import com.app.tripnary.data.mappers.TaskMapper.toTaskEntityList
import com.app.tripnary.data.mappers.TaskMapper.toTaskModelList
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskRepositoryImpl ( private val taskDataSource: DatabaseTaskDataSource,)
    :TaskRepository
{

    private val taskService = TaskServiceImpl()


    override fun getAllTask(): Flow<List<TaskModel>> {
        taskService.insertAllTask { taskModels ->
            if (taskModels != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    taskDataSource.clearTasks()
                    taskDataSource.insertTasks(taskModels.toTaskEntityList())
                }

            } else {

            }
        }
        return taskDataSource.getAllTasks()
            .map { tasks -> tasks.toTaskModelList()  }

    }

    override suspend fun addTask(task: TaskModel) {
        taskDataSource.addTask(task.noteEntityFromModel())

        val callback: (TaskModel?) -> Unit = { newTask ->
            if (newTask != null) {
            } else {
                // Manejar caso de error o respuesta nula
            }
        }

        taskService.addTaskPost(task, callback)

    }



}