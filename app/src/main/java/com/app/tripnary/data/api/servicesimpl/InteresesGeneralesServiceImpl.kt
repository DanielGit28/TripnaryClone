package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.InteresesGeneralesService
import com.app.tripnary.domain.models.InteresesGeneralesModel
import com.app.tripnary.domain.models.UsuariosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class InteresesGeneralesServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(InteresesGeneralesService::class.java)

    fun addInteresesGenerales(interesesGeneralesModel: InteresesGeneralesModel?, callback: (UsuariosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(interesesGeneralesModel)
        val interesesGeneralesData = gson.fromJson(json, InteresesGeneralesModel::class.java)

        val call = serviceBuilder.postInteresesGenerales(interesesGeneralesData)

        call.enqueue(object : retrofit2.Callback<UsuariosModel> {
            override fun onResponse(call: Call<UsuariosModel>, response: Response<UsuariosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newUsuario = gson.fromJson(jsonBody, UsuariosModel::class.java)
                    callback(newUsuario)
                }
            }

            override fun onFailure(call: Call<UsuariosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}