package com.app.tripnary.data.repositories

import com.app.tripnary.data.api.servicesimpl.DocumentosServiceImpl
import com.app.tripnary.data.datasources.DatabaseDocumentosDataSource
import com.app.tripnary.data.mappers.DocumentosMapper.documentoEntityFromModel
import com.app.tripnary.data.mappers.DocumentosMapper.toDocumentoEntityList
import com.app.tripnary.data.mappers.DocumentosMapper.toDocumentoModelList
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.repositories.DocumentosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DocumentosRepositoryImpl (private val documentosDataSource: DatabaseDocumentosDataSource) :
    DocumentosRepository {

    private val documentosService = DocumentosServiceImpl()

    override suspend fun postDocumentos(documentosModel: DocumentosModel) {
        val callback: (DocumentosModel?) -> Unit = { newDocumento ->
            if (newDocumento != null){
                CoroutineScope(Dispatchers.IO).launch {
                    documentosDataSource.clearDocumentos()
                    documentosDataSource.addDocumentos(newDocumento.documentoEntityFromModel())
                }
            } else {

            }
        }

        documentosService.postDocumentos(documentosModel, callback)
    }

    override suspend fun getDocumentosByIdPlanViaje(idPlanViaje: String): Flow<List<DocumentosModel>> {
        documentosService.getDocumentosByIdPlanViaje(idPlanViaje) { documentosModels, error ->
            if (error != null){

            } else if (documentosModels != null) {
                CoroutineScope(Dispatchers.IO).launch{
                    documentosDataSource.clearDocumentos()
                    documentosDataSource.insertDocumentos(documentosModels.toDocumentoEntityList())
                }

            } else {

            }
        }
        return documentosDataSource.getAllDocumentos()
            .map { documentos -> documentos.toDocumentoModelList() }
    }

    override suspend fun getDocumentosByReference(reference: String): DocumentosModel {
        return suspendCoroutine { continuation ->
            documentosService.getDocumentosByReference(reference) { documentosModel, error ->
                if (error != null){
                    continuation.resumeWithException(error as Throwable)
                } else if (documentosModel != null) {
                    CoroutineScope(Dispatchers.IO).launch{
                        documentosDataSource.clearDocumentos()
                        documentosDataSource.addDocumentos(documentosModel.documentoEntityFromModel())
                    }
                    continuation.resume(documentosModel)

                } else {
                    continuation.resumeWithException(NullPointerException("Documento is null"))
                }
            }
        }
    }

    override suspend fun updateDocumentos(documentosModel: DocumentosModel) {
        val callback: (DocumentosModel?) -> Unit = { newDocumento ->
            if (newDocumento != null){
                CoroutineScope(Dispatchers.IO).launch {
                    documentosDataSource.clearDocumentos()
                    documentosDataSource.addDocumentos(newDocumento.documentoEntityFromModel())
                }
            } else {

            }
        }
        documentosService.updateDocumentos(documentosModel, callback)
    }


}