package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.ColaboradoresService
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.UsuariosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class ColaboradoresServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(ColaboradoresService::class.java)

    fun getColaboradoresByViaje(idPlanViaje: String, callback: (List<ColaboradoresModel>?, Any?) -> Unit) {
        val call = serviceBuilder.getColaboradoresByViaje(idPlanViaje)


        call.enqueue(object : retrofit2.Callback<List<ColaboradoresModel>> {
            override fun onResponse(call: Call<List<ColaboradoresModel>>, response: Response<List<ColaboradoresModel>>) {
                if (response.isSuccessful) {
                    val colaboradores = response.body()
                    callback(colaboradores, null)
                } else {
                    callback(null , null)
                }
            }

            override fun onFailure(call: Call<List<ColaboradoresModel>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun addColaboradores(colaboradoresModel: ColaboradoresModel, callback: (ColaboradoresModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(colaboradoresModel)
        val colaboradoresData = gson.fromJson(json, ColaboradoresModel::class.java)

        val call = serviceBuilder.postColaboradores(colaboradoresData)

        call.enqueue(object : retrofit2.Callback<ColaboradoresModel> {
            override fun onResponse(call: Call<ColaboradoresModel>, response: Response<ColaboradoresModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newColaborador = gson.fromJson(jsonBody, ColaboradoresModel::class.java)
                    callback(newColaborador)
                }
            }

            override fun onFailure(call: Call<ColaboradoresModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })

    }

    fun getColaboradoresByReference(reference: String, callback: (ColaboradoresModel?, Any?) -> Unit) {
        val call = serviceBuilder.getColaboradoresByReference(reference)
        call.enqueue(object : retrofit2.Callback<ColaboradoresModel> {
            override fun onResponse(
                call: Call<ColaboradoresModel>,
                response: Response<ColaboradoresModel>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<ColaboradoresModel>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun updateColaborador(colaboradoresModel: ColaboradoresModel, callback: (ColaboradoresModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(colaboradoresModel)
        val colaboradoresData = gson.fromJson(json, ColaboradoresModel::class.java)

        val call = serviceBuilder.updateColaborador(colaboradoresData)

        call.enqueue(object : retrofit2.Callback<ColaboradoresModel> {
            override fun onResponse(call: Call<ColaboradoresModel>, response: Response<ColaboradoresModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newColaborador = gson.fromJson(jsonBody, ColaboradoresModel::class.java)
                    callback(newColaborador)
                }
            }

            override fun onFailure(call: Call<ColaboradoresModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}