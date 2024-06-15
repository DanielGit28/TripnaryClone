package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.RestaurantesRecomendadosAIService
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import retrofit2.Call
import retrofit2.Response

class RestaurantesRecomendadosAIServiceImpl {
    private val serviBuilder = ServiceBuilder.buildService(RestaurantesRecomendadosAIService::class.java)

    fun postPrompt(
        interes: InteresesRestaurantesAIModel,
        referencePlanViaje: String,
        tipo: String,
        callback: (String) -> Unit
    ) {

        val call = serviBuilder.postPrompt(interes, referencePlanViaje,tipo)



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
                Log.d("Error restaurantes ai service ",t.message.toString())
                //callback(null, t)
            }
        })

    }
}