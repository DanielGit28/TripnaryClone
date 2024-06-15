package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.PlanViajeService
import com.app.tripnary.data.api.services.TaskService
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class PlanViajeServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(PlanViajeService::class.java)

    fun addPlanViaje(planViajeModel: PlanViajeModel, callback: (PlanViajeModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(planViajeModel)
        val planViajeModelData = gson.fromJson(json, PlanViajeModel::class.java)

        val call = serviceBuilder.postPlanViaje(planViajeModelData)

        call.enqueue(object : retrofit2.Callback<PlanViajeModel> {
            override fun onResponse(call: Call<PlanViajeModel>, response: Response<PlanViajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newPlan = gson.fromJson(jsonBody, PlanViajeModel::class.java)
                    callback(newPlan)

                }
            }

            override fun onFailure(call: Call<PlanViajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }


    fun getAllPlanesByReference(reference: String, callback: (List<PlanViajeModel>?) -> Unit) {
        val call = serviceBuilder.getAllPlanesByReference(reference)

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

    fun updateDiasPlanViaje(reference: String, planViajeModel: PlanViajeModel, callback: (PlanViajeModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(planViajeModel)
        val planViajeModelData = gson.fromJson(json, PlanViajeModel::class.java)

        val call = serviceBuilder.updateDiasPlanes(reference,planViajeModelData)

        call.enqueue(object : retrofit2.Callback<PlanViajeModel> {
            override fun onResponse(call: Call<PlanViajeModel>, response: Response<PlanViajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newPlan = gson.fromJson(jsonBody, PlanViajeModel::class.java)
                    callback(newPlan)

                }
            }

            override fun onFailure(call: Call<PlanViajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun updatePlanViaje(planViajeModel: PlanViajeModel, callback: (PlanViajeModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(planViajeModel)
        val planViajeModelData = gson.fromJson(json, PlanViajeModel::class.java)

        val call = serviceBuilder.updatePlanViaje(planViajeModelData)

        call.enqueue(object : retrofit2.Callback<PlanViajeModel> {
            override fun onResponse(call: Call<PlanViajeModel>, response: Response<PlanViajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newPlan = gson.fromJson(jsonBody, PlanViajeModel::class.java)
                    callback(newPlan)

                }
            }

            override fun onFailure(call: Call<PlanViajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun deletePlanViaje(reference: String, callback: (PlanViajeModel?) -> Unit){
        val gson = Gson()

        val call = serviceBuilder.deletePlan(reference)

        call.enqueue(object : retrofit2.Callback<PlanViajeModel> {
            override fun onResponse(call: Call<PlanViajeModel>, response: Response<PlanViajeModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val planDelete = gson.fromJson(jsonBody, PlanViajeModel::class.java)
                    callback(planDelete)
                }
            }

            override fun onFailure(call: Call<PlanViajeModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }


}