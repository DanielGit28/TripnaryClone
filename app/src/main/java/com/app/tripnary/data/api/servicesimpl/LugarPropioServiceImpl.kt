package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.LugaresPlanesService
import com.app.tripnary.data.api.services.LugaresPropiosService
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class LugarPropioServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(LugaresPropiosService::class.java)

    fun addLugarPropio(lugarPropioModel: LugarPropioModel, callback: (LugarPropioModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(lugarPropioModel)
        val data = gson.fromJson(json, LugarPropioModel::class.java)

        val call = serviceBuilder.postLugaresPropios(data)

        call.enqueue(object : retrofit2.Callback<LugarPropioModel> {
            override fun onResponse(call: Call<LugarPropioModel>, response: Response<LugarPropioModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarPropio = gson.fromJson(jsonBody, LugarPropioModel::class.java)
                    callback(lugarPropio)

                }
            }

            override fun onFailure(call: Call<LugarPropioModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun getAllLugarPlanByPropios(idPlanViajeLugar: String, callback: (List<LugarPropioModel>?) -> Unit) {
        val call = serviceBuilder.getAllByLugaresPropios(idPlanViajeLugar)

        call.enqueue(object : retrofit2.Callback<List<LugarPropioModel>> {
            override fun onResponse(
                call: Call<List<LugarPropioModel>>,
                response: Response<List<LugarPropioModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<LugarPropioModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}