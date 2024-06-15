package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.repositories.DocumentosRepository
import kotlinx.coroutines.flow.Flow

class GetDocumentosByIdPlanViajeUseCase(private val documentosRepository: DocumentosRepository) {
    suspend fun execute(idPlanViaje: String) : Flow<List<DocumentosModel>> {
        return documentosRepository.getDocumentosByIdPlanViaje(idPlanViaje)
    }
}
