package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.PlanViajeEntity
import com.app.tripnary.data.database.entities.PlanesViajesColaboradorEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PlanesViajesColaboradorDao {
    @Query("SELECT * FROM planes_viajes_colaborador")
    abstract fun getPlanesViajes(): Flow<List<PlanesViajesColaboradorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(planViaje: PlanesViajesColaboradorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(planes: List<PlanesViajesColaboradorEntity>)

    @Query("DELETE FROM planes_viajes_colaborador")
    abstract suspend fun clearPlanes()
}