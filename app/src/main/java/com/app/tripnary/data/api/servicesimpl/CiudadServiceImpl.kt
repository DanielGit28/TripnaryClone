package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.CiudadesService
import com.app.tripnary.data.api.services.ContinentesService
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PlanViajeModel
import retrofit2.Call
import retrofit2.Response

class CiudadServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(CiudadesService::class.java)

    fun getAllCiudades(callback: (List<CiudadModel>?) -> Unit) {
        val call = serviceBuilder.getAllCiudades()


        call.enqueue(object : retrofit2.Callback<List<CiudadModel>> {
            override fun onResponse(
                call: Call<List<CiudadModel>>,
                response: Response<List<CiudadModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<CiudadModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun getAllCiudadesByPais(idPais: String, callback: (List<CiudadModel>?) -> Unit) {
        val call = serviceBuilder.getAllCiudadesByPais(idPais)

        call.enqueue(object : retrofit2.Callback<List<CiudadModel>> {
            override fun onResponse(
                call: Call<List<CiudadModel>>,
                response: Response<List<CiudadModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<CiudadModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

}