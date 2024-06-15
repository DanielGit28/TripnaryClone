package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.data.database.entities.UsuariosEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsuariosDao {
    @Query("SELECT * FROM usuarios")
    abstract fun getUsuarios(): Flow<List<UsuariosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(usuariosEntity: UsuariosEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<UsuariosEntity>)

    @Query("DELETE FROM usuarios")
    abstract suspend fun clearTasks()
}