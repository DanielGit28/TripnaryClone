package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.ContinentesService
import com.app.tripnary.data.api.services.PaisService
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PaisModel
import retrofit2.Call
import retrofit2.Response

class ContinenteServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(ContinentesService::class.java)

    fun getAllContinentes(callback: (List<ContinenteModel>?) -> Unit) {
        val call = serviceBuilder.getAllContinentes()


        call.enqueue(object : retrofit2.Callback<List<ContinenteModel>> {
            override fun onResponse(
                call: Call<List<ContinenteModel>>,
                response: Response<List<ContinenteModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<ContinenteModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}