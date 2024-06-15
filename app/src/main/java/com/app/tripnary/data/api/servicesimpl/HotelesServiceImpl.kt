package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.HotelesService
import com.app.tripnary.domain.models.HotelesModel
import retrofit2.Call
import retrofit2.Response

class HotelesServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(HotelesService::class.java)

    fun getHotelesRecomendados(hotelesRecomendados:Boolean, latitud:String,
                              longitud: String, radio:Number, callback: (List<HotelesModel>?) -> Unit) {

        val call = serviceBuilder.getHotelesRecomendados(hotelesRecomendados, latitud,
            longitud, radio)


        call.enqueue(object : retrofit2.Callback<List<HotelesModel>> {
            override fun onResponse(
                call: Call<List<HotelesModel>>,
                response: Response<List<HotelesModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<HotelesModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}
