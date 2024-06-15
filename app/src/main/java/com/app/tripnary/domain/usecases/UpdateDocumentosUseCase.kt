package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.repositories.DocumentosRepository

class UpdateDocumentosUseCase(private val documentosRepository: DocumentosRepository) {

    suspend fun execute(documentosModel: DocumentosModel) {
        documentosRepository.updateDocumentos(documentosModel)
    }
}