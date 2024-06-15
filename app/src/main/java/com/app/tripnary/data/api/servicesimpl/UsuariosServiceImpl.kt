package com.app.tripnary.data.api.servicesimpl

import android.util.Log
import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.UsuariosService
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.UsuariosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuariosServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(UsuariosService::class.java)

    fun insertAllUsuarios (callback: (List<UsuariosModel>?) -> Unit) {
        val call = serviceBuilder.getAllUsuarios()

        call.enqueue(object : retrofit2.Callback<List<UsuariosModel>> {
            override fun onResponse(
                call: Call<List<UsuariosModel>>,
                response: Response<List<UsuariosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody)
                }
            }

            override fun onFailure(call: Call<List<UsuariosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }

    fun updateUsuario (usuariosModel: UsuariosModel?, callback: (UsuariosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(usuariosModel)
        val usuariosData = gson.fromJson(json, UsuariosModel::class.java)
        val call = serviceBuilder.updateUsuario(usuariosData)

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

    fun getUsuarioByEmail(correoElectronico: String, callback: (List<UsuariosModel>?, Any?) -> Unit) {
        val call = serviceBuilder.getUsuarioByEmail(correoElectronico)
        call.enqueue(object : Callback<List<UsuariosModel>> {
            override fun onResponse(call: Call<List<UsuariosModel>>, response: Response<List<UsuariosModel>>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    callback(usuario, null)
                } else {
                    callback(null , null)
                }
            }

            override fun onFailure(call: Call<List<UsuariosModel>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun addUsuarios(usuariosModel: UsuariosModel?, callback: (UsuariosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(usuariosModel)
        val data = gson.fromJson(json, UsuariosModel::class.java)

        val call = serviceBuilder.postUsuarios(data)

        call.enqueue(object : retrofit2.Callback<UsuariosModel> {
            override fun onResponse(call: Call<UsuariosModel>, response: Response<UsuariosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val usuario = gson.fromJson(jsonBody, UsuariosModel::class.java)
                    callback(usuario)

                }
            }

            override fun onFailure(call: Call<UsuariosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}