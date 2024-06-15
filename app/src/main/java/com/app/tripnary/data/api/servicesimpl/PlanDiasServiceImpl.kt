package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.ContinentesService
import com.app.tripnary.data.api.services.PlanDiasService
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PlanDiasModel
import retrofit2.Call
import retrofit2.Response

class PlanDiasServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(PlanDiasService::class.java)

    fun getAllDias(callback: (List<PlanDiasModel>?) -> Unit) {
        val call = serviceBuilder.getAllDias()


        call.enqueue(object : retrofit2.Callback<List<PlanDiasModel>> {
            override fun onResponse(
                call: Call<List<PlanDiasModel>>,
                response: Response<List<PlanDiasModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<PlanDiasModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun getDiasByIdPlanViaje(idPlanViaje:String?, callback: (List<PlanDiasModel>?) -> Unit) {
        val call = serviceBuilder.getDiasByIdPlanViaje(idPlanViaje)

        call.enqueue(object : retrofit2.Callback<List<PlanDiasModel>> {
            override fun onResponse(
                call: Call<List<PlanDiasModel>>,
                response: Response<List<PlanDiasModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                    Log.e("Dias planes", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<PlanDiasModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString())
                callback(null)
            }
        })
    }
}