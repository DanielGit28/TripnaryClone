package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.TaskService
import com.app.tripnary.data.mappers.TaskMapper.toTaskEntityList
import com.app.tripnary.domain.models.TaskModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class TaskServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(TaskService::class.java)

    fun insertAllTask(callback: (List<TaskModel>?) -> Unit) {
        val call = serviceBuilder.getAllTasks()


        call.enqueue(object : retrofit2.Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun addTaskPost(taskModel: TaskModel?, callback: (TaskModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(taskModel)
        val taskData = gson.fromJson(json, TaskModel::class.java)

        val call = serviceBuilder.postTask(taskData)

        call.enqueue(object : retrofit2.Callback<TaskModel> {
            override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newTask = gson.fromJson(jsonBody, TaskModel::class.java)
                    callback(newTask)
                }
            }

            override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

}