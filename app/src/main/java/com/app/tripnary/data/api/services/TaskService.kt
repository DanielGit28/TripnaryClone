package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.TaskModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TaskService {
    @Headers("Content-Type: application/json")
    @GET("tasks")
    fun getAllTasks() : Call<List<TaskModel>>

    @Headers("Content-Type: application/json")
    @POST("tasks")
    fun postTask(@Body taskData: TaskModel) : Call<TaskModel>


}