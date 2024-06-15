package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.InteresesLugaresAIEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LugaresRecomendadosAIDao {

    @Query("SELECT * FROM lugares_recomendados_ai")
    abstract fun getLugaresRecomendadosAI(): Flow<List<InteresesLugaresAIEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(lugaresRecomendadosEntity: InteresesLugaresAIEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<InteresesLugaresAIEntity>)

    @Query("DELETE FROM lugares_recomendados_ai")
    abstract suspend fun clearLugaresRecomendadosAI()
}