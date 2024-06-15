package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.LugaresPlanesService
import com.app.tripnary.data.api.services.PlanViajeService
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class LugarPlanServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(LugaresPlanesService::class.java)

    fun addLugarPlanViaje(lugarPlanModel: LugarPlanModel, callback: (LugarPlanModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(lugarPlanModel)
        val data = gson.fromJson(json, LugarPlanModel::class.java)

        val call = serviceBuilder.postPlanViaje(data)

        call.enqueue(object : retrofit2.Callback<LugarPlanModel> {
            override fun onResponse(call: Call<LugarPlanModel>, response: Response<LugarPlanModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarPlan = gson.fromJson(jsonBody, LugarPlanModel::class.java)
                    callback(lugarPlan)

                }
            }

            override fun onFailure(call: Call<LugarPlanModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun getAllPlanesByReference(idPlanViaje: String, callback: (List<LugarPlanModel>?) -> Unit) {
        val call = serviceBuilder.getAllPlanesByReference(idPlanViaje)

        call.enqueue(object : retrofit2.Callback<List<LugarPlanModel>> {
            override fun onResponse(
                call: Call<List<LugarPlanModel>>,
                response: Response<List<LugarPlanModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<LugarPlanModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun updateLugarPlanViaje(lugarPlanModel: LugarPlanModel, callback: (LugarPlanModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(lugarPlanModel)
        val data = gson.fromJson(json, LugarPlanModel::class.java)

        val call = serviceBuilder.updateLugarPlan(data)

        call.enqueue(object : retrofit2.Callback<LugarPlanModel> {
            override fun onResponse(call: Call<LugarPlanModel>, response: Response<LugarPlanModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarPlan = gson.fromJson(jsonBody, LugarPlanModel::class.java)
                    callback(lugarPlan)



                }
            }

            override fun onFailure(call: Call<LugarPlanModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun deleteLugarPlanByReference(reference: String, callback: (LugarPlanModel?) -> Unit){
        val gson = Gson()

        val call = serviceBuilder.deleteLugarPlan(reference)

        call.enqueue(object : retrofit2.Callback<LugarPlanModel> {
            override fun onResponse(call: Call<LugarPlanModel>, response: Response<LugarPlanModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarDelete = gson.fromJson(jsonBody, LugarPlanModel::class.java)
                    callback(lugarDelete)
                }
            }

            override fun onFailure(call: Call<LugarPlanModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}