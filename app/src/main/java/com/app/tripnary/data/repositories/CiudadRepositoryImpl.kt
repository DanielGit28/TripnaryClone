package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.services.CiudadesService
import com.app.tripnary.data.api.servicesimpl.CiudadServiceImpl
import com.app.tripnary.data.api.servicesimpl.ContinenteServiceImpl
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.repositories.CiudadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CiudadRepositoryImpl : CiudadRepository {

    private val ciudadService = CiudadServiceImpl()


    override fun getAllCiudades(): Flow<List<CiudadModel>> = flow {
        val ciudadesModel = suspendCoroutine<List<CiudadModel>?> { continuation ->
            ciudadService.getAllCiudades { ciudad ->
                continuation.resume(ciudad)
            }
        }

        if (ciudadesModel != null) {
            emit(ciudadesModel)
        }
    }.flowOn(Dispatchers.IO)


    override fun getAllCiudadesByPais(idPais: String): Flow<List<CiudadModel>> = flow {
        val ciudadesModel = suspendCoroutine<List<CiudadModel>?> { continuation ->
            ciudadService.getAllCiudadesByPais(idPais) { ciudad ->
                continuation.resume(ciudad)
            }
        }


        if (ciudadesModel != null) {
            emit(ciudadesModel)
        }
    }.flowOn(Dispatchers.IO)
}