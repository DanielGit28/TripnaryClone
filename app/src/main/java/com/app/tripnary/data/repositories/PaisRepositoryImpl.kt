package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.PaisServiceImpl
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.repositories.PaisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PaisRepositoryImpl : PaisRepository {

    private val paisService = PaisServiceImpl()

    override fun getAllPaises(): Flow<List<PaisModel>> = flow {
        val paisesModels = suspendCoroutine<List<PaisModel>?> { continuation ->
            paisService.getAllPaises { paises ->
                continuation.resume(paises)
            }
        }

        if (paisesModels != null) {
            emit(paisesModels)
        }
    }.flowOn(Dispatchers.IO)


}