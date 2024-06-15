package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.EstadisticasService
import com.app.tripnary.data.api.services.LugaresPlanesService
import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class EstadisticasServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(EstadisticasService::class.java)


    fun getEstadisticasUsuario(reference: String, callback: (EstadisticasModel?) -> Unit){
        val gson = Gson()

        val call = serviceBuilder.getEstadisticas(reference)

        call.enqueue(object : retrofit2.Callback<EstadisticasModel> {
            override fun onResponse(call: Call<EstadisticasModel>, response: Response<EstadisticasModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val estadisticas = gson.fromJson(jsonBody, EstadisticasModel::class.java)
                    callback(estadisticas)
                }
            }

            override fun onFailure(call: Call<EstadisticasModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}