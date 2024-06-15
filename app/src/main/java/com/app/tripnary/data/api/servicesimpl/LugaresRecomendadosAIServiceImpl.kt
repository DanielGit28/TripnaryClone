package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.LugaresRecomendadosAIService
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

class LugaresRecomendadosAIServiceImpl {
    private val serviBuilder = ServiceBuilder.buildService(LugaresRecomendadosAIService::class.java)


    fun getLugaresRecomendadosAIByInteres(
        reference: String,
        callback: (List<InteresLugaresAIModel>?, Any?) -> Unit
    ) {
        val call = serviBuilder.getLugaresRecomendadosAIByInteres(reference)
        call.enqueue(object : retrofit2.Callback<List<InteresLugaresAIModel>> {
            override fun onResponse(
                call: Call<List<InteresLugaresAIModel>>,
                response: Response<List<InteresLugaresAIModel>>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<List<InteresLugaresAIModel>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getLugarRecomendadoById(
        reference: String,
        callback: (InteresLugaresAIModel?, Any?) -> Unit
    ) {
        val call = serviBuilder.getLugarRecomendadoAIById(reference)
        call.enqueue(object : retrofit2.Callback<InteresLugaresAIModel> {
            override fun onResponse(
                call: Call<InteresLugaresAIModel>,
                response: Response<InteresLugaresAIModel>
            ) {
                if (response.isSuccessful) {
                    val lugares = response.body()
                    callback(lugares, null)
                } else {
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<InteresLugaresAIModel>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getAllLugares(callback: (List<InteresLugaresAIModel>?) -> Unit) {
        val call = serviBuilder.getAllLugares()


        call.enqueue(object : retrofit2.Callback<List<InteresLugaresAIModel>> {
            override fun onResponse(
                call: Call<List<InteresLugaresAIModel>>,
                response: Response<List<InteresLugaresAIModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                    Log.d("Lugares recomendados ai", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<InteresLugaresAIModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("error", t.message.toString())
                callback(null)
            }
        })
    }

    fun addLugarRecomendaoAI(interesLugaresAIModel: InteresLugaresAIModel, callback: (InteresLugaresAIModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(interesLugaresAIModel)
        val data = gson.fromJson(json, InteresLugaresAIModel::class.java)

        val call = serviBuilder.postLugarRecomendadoAI(data)

        call.enqueue(object : retrofit2.Callback<InteresLugaresAIModel> {
            override fun onResponse(call: Call<InteresLugaresAIModel>, response: Response<InteresLugaresAIModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val lugarPropio = gson.fromJson(jsonBody, InteresLugaresAIModel::class.java)
                    callback(lugarPropio)

                    Log.d("Lugar recomendado ai", lugarPropio.toString())

                }
            }

            override fun onFailure(call: Call<InteresLugaresAIModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
                Log.e("error", t.message.toString())
            }
        })
    }

    fun postPrompt(
        lugaresRecomendados: InteresLugaresAIModel,
        referencePlanViaje: String,
        tipo: String,
        callback: (String) -> Unit
    ) {

        val call = serviBuilder.postPrompt(lugaresRecomendados, referencePlanViaje,tipo)



        call.enqueue(object : retrofit2.Callback<String?> {

            override fun onResponse(call: Call<String?>, response: Response<String?>) {

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        callback(responseBody)
                    }

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {

                t.printStackTrace()
                println(t.message.toString())
                Log.d("Error lugares ai service ",t.message.toString())
                //callback(null, t)
            }
        })

    }


}