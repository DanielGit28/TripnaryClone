package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.LugarPropioEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LugarPropioDao {

    @Query("SELECT * FROM lugares_propios")
    abstract fun getLugaresPropios(): Flow<List<LugarPropioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(lugarPropioEntity: LugarPropioEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(lugarPropio: List<LugarPropioEntity>)

    @Query("DELETE FROM lugares_propios")
    abstract suspend fun clearAll()
}