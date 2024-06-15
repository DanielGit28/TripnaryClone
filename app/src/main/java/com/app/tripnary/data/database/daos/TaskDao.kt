package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tripnary.data.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {

    @Query("SELECT * FROM tasks")
    abstract fun getTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(task: TaskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("DELETE FROM tasks")
    abstract suspend fun clearTasks()
}