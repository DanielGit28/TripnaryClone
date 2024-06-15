package com.app.tripnary.data.api.services

import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.models.UsuariosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuariosService {

    @Headers("Content-Type: application/json")
    @POST("user")
    fun postUsuarios(@Body data: UsuariosModel) : Call<UsuariosModel>

    @Headers("Content-Type: application/json")
    @GET("user")
    fun getAllUsuarios() : Call<List<UsuariosModel>>
    @Headers("Content-Type: application/json")
    @PUT("user")
    fun updateUsuario(@Body usuariosModel: UsuariosModel) : Call<UsuariosModel>
    @Headers("Content-Type: application/json")
    @GET("user")
    fun getUsuarioByEmail(@Query("correoElectronico") correoElectronico: String): Call<List<UsuariosModel>>

}