package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.ListaDeseosService
import com.app.tripnary.domain.models.ListaDeseosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaDeseosServiceImpl {

    private val serviceBuilder = ServiceBuilder.buildService(ListaDeseosService::class.java)

    fun getAllListaDeseos(callback: (List<ListaDeseosModel>?) -> Unit, referenceUsuario:String?) {
        val call = serviceBuilder.getAllListaDeseos(referenceUsuario)

        call.enqueue(object : retrofit2.Callback<List<ListaDeseosModel>> {
            override fun onResponse(
                call: Call<List<ListaDeseosModel>>,
                response: Response<List<ListaDeseosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)

                    Log.w("Lista Deseos", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<ListaDeseosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun addListaDeseos(listadeseosModel: ListaDeseosModel, callback: (ListaDeseosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(listadeseosModel)
        val data = gson.fromJson(json, ListaDeseosModel::class.java)

        val call = serviceBuilder.postListaDeseos(data)

        call.enqueue(object : retrofit2.Callback<ListaDeseosModel> {
            override fun onResponse(call: Call<ListaDeseosModel>, response: Response<ListaDeseosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val listaDeseos = gson.fromJson(jsonBody, ListaDeseosModel::class.java)
                    callback(listaDeseos)
                }
            }

            override fun onFailure(call: Call<ListaDeseosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun deleteListaDeseos(listadeseosModel: ListaDeseosModel, callback: (ListaDeseosModel?) -> Unit){
        val gson = Gson()
        val json = gson.toJson(listadeseosModel)
        val data = gson.fromJson(json, ListaDeseosModel::class.java)

        val call = serviceBuilder.deleteListaDeseos(data.reference)

        call.enqueue(object : retrofit2.Callback<ListaDeseosModel> {
            override fun onResponse(call: Call<ListaDeseosModel>, response: Response<ListaDeseosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val listaDeseos = gson.fromJson(jsonBody, ListaDeseosModel::class.java)
                    callback(listaDeseos)
                }
            }

            override fun onFailure(call: Call<ListaDeseosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}