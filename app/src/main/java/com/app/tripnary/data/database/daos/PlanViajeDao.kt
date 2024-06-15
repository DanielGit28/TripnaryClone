package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.tripnary.data.database.entities.PlanViajeEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

@Dao
abstract class PlanViajeDao {

    @Query("SELECT * FROM planes_viajes")
    abstract fun getPlanesViajes(): Flow<List<PlanViajeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(planViaje: PlanViajeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(planes: List<PlanViajeEntity>)

    @Query("DELETE FROM planes_viajes")
    abstract suspend fun clearPlanes()
}