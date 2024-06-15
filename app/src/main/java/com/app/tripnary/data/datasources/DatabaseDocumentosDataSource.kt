package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.DocumentosDao
import com.app.tripnary.data.database.entities.DocumentosEntity
import com.app.tripnary.data.database.entities.LugarPropioEntity
import kotlinx.coroutines.flow.Flow

class DatabaseDocumentosDataSource(private val documentosDao: DocumentosDao) {

    suspend fun addDocumentos(documentosEntity: DocumentosEntity): Long =
        documentosDao.insert(documentosEntity)
    fun getAllDocumentos(): Flow<List<DocumentosEntity>> = documentosDao.getDocumentos()

    suspend fun insertDocumentos(documentos: List<DocumentosEntity>) {
        documentosDao.insertAll(documentos)
    }
    suspend fun clearDocumentos(){
        documentosDao.clearDocumentos()
    }
}