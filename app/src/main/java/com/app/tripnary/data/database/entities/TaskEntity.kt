package com.app.tripnary.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    val id: String,
    @PrimaryKey() var reference: String,
    var text: String,
    var done: Boolean
)
