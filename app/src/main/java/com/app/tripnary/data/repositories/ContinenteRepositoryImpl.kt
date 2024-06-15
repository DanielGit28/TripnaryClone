package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.ContinenteServiceImpl
import com.app.tripnary.data.api.servicesimpl.PaisServiceImpl
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.repositories.ContinenteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContinenteRepositoryImpl :  ContinenteRepository{

    private val continenteService = ContinenteServiceImpl()


    override fun getAllContinentes(): Flow<List<ContinenteModel>> = flow {
        val continentesModel = suspendCoroutine<List<ContinenteModel>?> { continuation ->
            continenteService.getAllContinentes { continente ->
                continuation.resume(continente)
            }
        }

        if (continentesModel != null) {
            emit(continentesModel)
        }
    }.flowOn(Dispatchers.IO)
}