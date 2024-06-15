package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.LugaresRecomendadosService
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class LugaresRecomendadosServiceImpl {
    private val serviBuilder = ServiceBuilder.buildService(LugaresRecomendadosService::class.java)

    fun getLugaresRecomendadosByIntereses(
        reference: String,
        callback: (List<LugaresRecomendadosModel>?, Any?) -> Unit
    ) {
        val call = serviBuilder.getLugaresRecomendadosByIntereses(reference)
        call.enqueue(object : retrofit2.Callback<List<LugaresRecomendadosModel>> {
            override fun onResponse(
                call: Call<List<LugaresRecomendadosModel>>,
                response: Response<List<LugaresRecomendadosModel>>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<List<LugaresRecomendadosModel>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getLugaresRecomendadosById(
        reference: String,
        callback: (LugaresRecomendadosModel?, Any?) -> Unit
    ) {
        val call = serviBuilder.getLugaresRecomendadosById(reference)
        call.enqueue(object : retrofit2.Callback<LugaresRecomendadosModel> {
            override fun onResponse(
                call: Call<LugaresRecomendadosModel>,
                response: Response<LugaresRecomendadosModel>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<LugaresRecomendadosModel>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getLugaresRecomendadosByPopularidad(callback: (List<LugaresRecomendadosModel>?, Any?) -> Unit) {
        val call = serviBuilder.getLugaresRecomendadosByPopularidad()
        call.enqueue(object : retrofit2.Callback<List<LugaresRecomendadosModel>> {
            override fun onResponse(
                call: Call<List<LugaresRecomendadosModel>>,
                response: Response<List<LugaresRecomendadosModel>>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<List<LugaresRecomendadosModel>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getAllLugares(callback: (List<LugaresRecomendadosModel>?) -> Unit) {
        val call = serviBuilder.getAllLugares()


        call.enqueue(object : retrofit2.Callback<List<LugaresRecomendadosModel>> {
            override fun onResponse(
                call: Call<List<LugaresRecomendadosModel>>,
                response: Response<List<LugaresRecomendadosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<LugaresRecomendadosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun addLugarPropio(lugaresRecomendadosModel: LugaresRecomendadosModel, callback: (LugaresRecomendadosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(lugaresRecomendadosModel)
        val data = gson.fromJson(json, LugaresRecomendadosModel::class.java)

        val call = serviBuilder.postLugaresPropios(data)

        call.enqueue(object : retrofit2.Callback<LugaresRecomendadosModel> {
            override fun onResponse(call: Call<LugaresRecomendadosModel>, response: Response<LugaresRecomendadosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarPropio = gson.fromJson(jsonBody, LugaresRecomendadosModel::class.java)
                    callback(lugarPropio)

                }
            }

            override fun onFailure(call: Call<LugaresRecomendadosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun getAllLugarPlanByRecomendados(idPlanViajeLugarRecomendado: String, callback: (List<LugaresRecomendadosModel>?) -> Unit) {
        val call = serviBuilder.getAllByLugaresRecomendados(idPlanViajeLugarRecomendado)

        call.enqueue(object : retrofit2.Callback<List<LugaresRecomendadosModel>> {
            override fun onResponse(
                call: Call<List<LugaresRecomendadosModel>>,
                response: Response<List<LugaresRecomendadosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                }
            }

            override fun onFailure(call: Call<List<LugaresRecomendadosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}