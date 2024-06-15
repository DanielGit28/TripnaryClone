package com.app.tripnary.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.tripnary.data.database.entities.InteresesGeneralesEntity

@Dao
abstract class InteresesGeneralesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract  suspend fun insert(interesesGenerales: InteresesGeneralesEntity): Long
}