package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.CiudadesService
import com.app.tripnary.data.api.services.EquipajeService
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class EquipajeServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(EquipajeService::class.java)

    fun getAllEquipajeByPlan(idPlanViaje: String, callback: (List<EquipajeModel>?) -> Unit) {
        val call = serviceBuilder.getAllEquipajeByPlan(idPlanViaje)

        call.enqueue(object : retrofit2.Callback<List<EquipajeModel>> {
            override fun onResponse(
                call: Call<List<EquipajeModel>>,
                response: Response<List<EquipajeModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<EquipajeModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }


    fun addEquipaje(equipajeModel: EquipajeModel, callback: (EquipajeModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(equipajeModel)
        val equipajeModelData = gson.fromJson(json, EquipajeModel::class.java)

        val call = serviceBuilder.postEquipaje(equipajeModelData)

        call.enqueue(object : retrofit2.Callback<EquipajeModel> {
            override fun onResponse(call: Call<EquipajeModel>, response: Response<EquipajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newEquipaje = gson.fromJson(jsonBody, EquipajeModel::class.java)
                    callback(newEquipaje)

                }
            }

            override fun onFailure(call: Call<EquipajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }


    fun updateEquipaje(equipajeModel: EquipajeModel, callback: (EquipajeModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(equipajeModel)
        val data = gson.fromJson(json, EquipajeModel::class.java)

        val call = serviceBuilder.updateEquipaje(data)

        call.enqueue(object : retrofit2.Callback<EquipajeModel> {
            override fun onResponse(call: Call<EquipajeModel>, response: Response<EquipajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val equipaje = gson.fromJson(jsonBody, EquipajeModel::class.java)
                    callback(equipaje)



                }
            }

            override fun onFailure(call: Call<EquipajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun deleteEquipaje(reference: String, callback: (EquipajeModel?) -> Unit){
        val gson = Gson()

        val call = serviceBuilder.deleteEquipaje(reference)

        call.enqueue(object : retrofit2.Callback<EquipajeModel> {
            override fun onResponse(call: Call<EquipajeModel>, response: Response<EquipajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val equipajeDelete = gson.fromJson(jsonBody, EquipajeModel::class.java)
                    callback(equipajeDelete)
                }
            }

            override fun onFailure(call: Call<EquipajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}