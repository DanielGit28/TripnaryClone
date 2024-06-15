package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.PaisService
import com.app.tripnary.data.api.services.TaskService
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TaskModel
import retrofit2.Call
import retrofit2.Response

class PaisServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(PaisService::class.java)

    fun getAllPaises(callback: (List<PaisModel>?) -> Unit) {
        val call = serviceBuilder.getAllPaises()


        call.enqueue(object : retrofit2.Callback<List<PaisModel>> {
            override fun onResponse(
                call: Call<List<PaisModel>>,
                response: Response<List<PaisModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<PaisModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}