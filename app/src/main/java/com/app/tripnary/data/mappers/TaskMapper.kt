package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.domain.models.TaskModel

object TaskMapper {
    fun TaskModel.noteEntityFromModel(): TaskEntity =
        TaskEntity(
            id = id,
            reference = reference,
            text = text,
            done = done
        )


    fun List<TaskEntity>.toTaskModelList(): List<TaskModel> = this.map { taskEntity ->
        TaskModel(
            id = taskEntity.id,
            reference = taskEntity.reference,
            text = taskEntity.text,
            done = taskEntity.done
        )
    }

    fun List<TaskModel>.toTaskEntityList(): List<TaskEntity> = this.map { taskModel ->
        TaskEntity(
            id = taskModel.id,
            reference = taskModel.reference,
            text = taskModel.text,
            done = taskModel.done
        )
    }




}