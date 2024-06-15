package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LugaresRecomendadosDao {

    @Query("SELECT * FROM lugares_recomendados")
    abstract fun getLugaresRecomendados(): Flow<List<LugaresRecomendadosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(lugaresRecomendadosEntity: LugaresRecomendadosEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<LugaresRecomendadosEntity>)

    @Query("DELETE FROM lugares_recomendados")
    abstract suspend fun clearLugaresRecomendados()
}