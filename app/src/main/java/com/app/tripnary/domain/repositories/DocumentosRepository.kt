package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.DocumentosModel
import kotlinx.coroutines.flow.Flow

interface DocumentosRepository {

    suspend fun postDocumentos(documentosModel: DocumentosModel)
    suspend fun getDocumentosByIdPlanViaje(idPlanViaje: String) : Flow<List<DocumentosModel>>
    suspend fun getDocumentosByReference(reference: String) : DocumentosModel
    suspend fun updateDocumentos(documentosModel: DocumentosModel)
}