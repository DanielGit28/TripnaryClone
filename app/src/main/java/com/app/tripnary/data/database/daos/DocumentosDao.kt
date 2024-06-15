package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.DocumentosEntity
import com.app.tripnary.data.database.entities.LugarPropioEntity
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DocumentosDao {
    @Query("SELECT * FROM documentos")
    abstract fun getDocumentos(): Flow<List<DocumentosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(documentosModel: DocumentosEntity) : Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<DocumentosEntity>)

    @Query("DELETE FROM documentos")
    abstract suspend fun clearDocumentos()
}