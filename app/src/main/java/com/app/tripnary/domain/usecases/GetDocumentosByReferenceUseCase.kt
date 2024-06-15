package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.repositories.DocumentosRepository

class GetDocumentosByReferenceUseCase(private val documentosRepository: DocumentosRepository) {

    suspend fun execute(reference: String) : DocumentosModel {
        return documentosRepository.getDocumentosByReference(reference)
    }
}