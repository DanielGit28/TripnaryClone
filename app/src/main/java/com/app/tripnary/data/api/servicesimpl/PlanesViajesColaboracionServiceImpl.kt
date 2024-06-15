package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.PlanesViajesColaboracionService
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.Response

class PlanesViajesColaboracionServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(PlanesViajesColaboracionService::class.java)
    fun getTipoColaboradorByIdPlanViaje(idPlanViaje: String, callback: (List<ColaboradoresModel>?, Any?) -> Unit) {
        val call = serviceBuilder.getTipoColaboradorByIdPlanViaje(idPlanViaje)

        call.enqueue(object : retrofit2.Callback<List<ColaboradoresModel>> {
            override fun onResponse(
                call: Call<List<ColaboradoresModel>>,
                response: Response<List<ColaboradoresModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody, null)
                }
            }

            override fun onFailure(call: Call<List<ColaboradoresModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null, null)
            }
        })
    }
    fun getPlanesByCorreoColaborador(correoColaborador: String, callback: (List<PlanViajeModel>?) -> Unit) {
        val call = serviceBuilder.getAllPlanesByCorreoColaborador(correoColaborador)

        call.enqueue(object : retrofit2.Callback<List<PlanViajeModel>> {
            override fun onResponse(
                call: Call<List<PlanViajeModel>>,
                response: Response<List<PlanViajeModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<PlanViajeModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}