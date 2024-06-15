package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LugarPlanesDao {

    @Query("SELECT * FROM lugares_planes")
    abstract fun getLugaresPlanes(): Flow<List<LugarPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(lugarPlan: LugarPlanEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(lugarPlan: List<LugarPlanEntity>)

    @Query("DELETE FROM lugares_planes")
    abstract suspend fun clearAll()
}