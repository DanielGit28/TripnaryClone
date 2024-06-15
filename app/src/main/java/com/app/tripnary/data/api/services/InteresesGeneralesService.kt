package com.app.tripnary.data.api.services
import com.app.tripnary.domain.models.InteresesGeneralesModel
import com.app.tripnary.domain.models.UsuariosModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface InteresesGeneralesService {
    @Headers("Content-Type: application/json")
    @POST("interesesGenerales")
    fun postInteresesGenerales(@Body interesesGeneralesData: InteresesGeneralesModel) : Call<UsuariosModel>
}