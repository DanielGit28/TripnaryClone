package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.PlanDiasServiceImpl
import com.app.tripnary.data.api.servicesimpl.VuelosServiceImpl
import com.app.tripnary.data.mappers.PlanDiasMapper.toPlanDiasEntityList
import com.app.tripnary.data.mappers.PlanDiasMapper.toPlanDiasModelList
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.repositories.VuelosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class VuelosRepositoryImpl : VuelosRepository {
    private val vuelosService = VuelosServiceImpl()

    override fun getVuelosRecomendados(
        vuelosRecomendados: Boolean, originLocationCode: String,
        destinationLocationCode: String, departureDate: String, adults: Int,
        returnDate: String, max: Int
    ): Flow<List<VuelosModel>> = flow {
        val vuelosModel = suspendCoroutine<List<VuelosModel>?> { continuation ->
            vuelosService.getVuelosRecomendados(vuelosRecomendados, originLocationCode,
                destinationLocationCode, departureDate, adults, returnDate, max,
                { vuelo ->
                    continuation.resume(vuelo)
                })
        }

        if (vuelosModel != null) {
            emit(vuelosModel)
        }
    }.flowOn(Dispatchers.IO)
}