package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.RestaurantesRecomendadosAIService
import com.app.tripnary.data.api.services.TiquetesService
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.models.TiquetesModel
import retrofit2.Call
import retrofit2.Response

class TiquetesServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(TiquetesService::class.java)

    fun addTiquete(
        tiquete: TiquetesModel,
        callback: (String) -> Unit
    ) {

        val call = serviceBuilder.addTiquete(tiquete)

        call.enqueue(object : retrofit2.Callback<String?> {

            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        callback(responseBody)
                    }

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
                Log.d("Error creando tiquete ",t.message.toString())
            }
        })

    }
}