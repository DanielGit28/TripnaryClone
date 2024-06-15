package com.app.tripnary.data.api.servicesimpl

import com.app.tripnary.data.ServiceBuilder
import com.app.tripnary.data.api.services.DocumentosService
import com.app.tripnary.domain.models.DocumentosModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class DocumentosServiceImpl {
    private val serviceBuilder = ServiceBuilder.buildService(DocumentosService::class.java)

    fun postDocumentos(documentosModel: DocumentosModel, callback: (DocumentosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(documentosModel)
        val documentosData = gson.fromJson(json, DocumentosModel::class.java)

        val call = serviceBuilder.postDocumentos(documentosData)

        call.enqueue(object : retrofit2.Callback<DocumentosModel> {
            override fun onResponse(call: Call<DocumentosModel>, response: Response<DocumentosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newDocumento = gson.fromJson(jsonBody, DocumentosModel::class.java)
                    callback(newDocumento)
                }
            }

            override fun onFailure(call: Call<DocumentosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }

    fun getDocumentosByIdPlanViaje(idPlanViaje: String, callback: (List<DocumentosModel>?, Any?) -> Unit) {
        val call = serviceBuilder.getDocumentosByIdPlanViaje(idPlanViaje)

        call.enqueue(object : retrofit2.Callback<List<DocumentosModel>> {
            override fun onResponse(
                call: Call<List<DocumentosModel>>,
                response: Response<List<DocumentosModel>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody, null)

                }
            }

            override fun onFailure(call: Call<List<DocumentosModel>>, t: Throwable) {
                t.printStackTrace()
                callback(null, null)
            }
        })
    }

    fun getDocumentosByReference(reference: String, callback: (DocumentosModel?, Any?) -> Unit) {
        val call = serviceBuilder.getDocumentosByReference(reference)

        call.enqueue(object : retrofit2.Callback<DocumentosModel> {
            override fun onResponse(
                call: Call<DocumentosModel>,
                response: Response<DocumentosModel>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody, null)

                }
            }

            override fun onFailure(call: Call<DocumentosModel>, t: Throwable) {
                t.printStackTrace()
                callback(null, null)
            }
        })
    }

    fun updateDocumentos(documentosModel: DocumentosModel, callback: (DocumentosModel?) -> Unit) {
        val gson = Gson()
        val json = gson.toJson(documentosModel)
        val documentosData = gson.fromJson(json, DocumentosModel::class.java)
        val call = serviceBuilder.updateDocumentos(documentosData)

        call.enqueue(object : retrofit2.Callback<DocumentosModel> {
            override fun onResponse(call: Call<DocumentosModel>, response: Response<DocumentosModel>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val jsonBody = gson.toJson(responseBody)
                    val newDocumento = gson.fromJson(jsonBody, DocumentosModel::class.java)
                    callback(newDocumento)
                }
            }

            override fun onFailure(call: Call<DocumentosModel>, t: Throwable) {
                t.printStackTrace()
                println(t.message.toString())
            }
        })
    }
}