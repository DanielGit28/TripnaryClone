package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.PlanDiasEntity
import com.app.tripnary.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PlanDiasDao {

    @Query("SELECT * FROM dias_planes")
    abstract fun getDias(): Flow<List<PlanDiasEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(dia: PlanDiasEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(dias: List<PlanDiasEntity>)

    @Query("DELETE FROM dias_planes")
    abstract suspend fun clearDias()
}