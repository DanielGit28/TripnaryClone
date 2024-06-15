package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.ColaboradoresEntity

import kotlinx.coroutines.flow.Flow

@Dao
abstract class ColaboradoresDao {
    @Query("SELECT * FROM colaboradores_viajes")
    abstract fun getColaboradores(): Flow<List<ColaboradoresEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(colaboradoresEntity: ColaboradoresEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<ColaboradoresEntity>)

    @Query("DELETE FROM colaboradores_viajes")
    abstract suspend fun clearColaboradores()
}