package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.VuelosService
import com.app.tripnary.domain.models.VuelosModel
import retrofit2.Call
import retrofit2.Response

class VuelosServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(VuelosService::class.java)

    fun getVuelosRecomendados(vuelosRecomendados:Boolean, originLocationCode:String,
                              destinationLocationCode: String, departureDate:String, adults:Int,
                              returnDate:String, max:Int, callback: (List<VuelosModel>?) -> Unit) {

        val call = serviceBuilder.getVuelosRecomendados(vuelosRecomendados, originLocationCode,
            destinationLocationCode, departureDate, adults, returnDate, max)


        call.enqueue(object : retrofit2.Callback<List<VuelosModel>> {
            override fun onResponse(
                call: Call<List<VuelosModel>>,
                response: Response<List<VuelosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<VuelosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}