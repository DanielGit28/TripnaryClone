package com.app.tripnary.data.mappers

import com.app.tripnary.data.database.entities.PlanDiasEntity
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.TaskModel

object PlanDiasMapper {

    fun PlanDiasModel.planDiasEntityFromModel(): PlanDiasEntity =
        PlanDiasEntity(
            reference = reference,
            dia = dia,
            fecha = fecha,
            idPlanViaje = idPlanViaje,
            estado = estado
        )


    fun List<PlanDiasEntity>.toPlanDiasModelList(): List<PlanDiasModel> = this.map { planDiasEntity ->
        PlanDiasModel(
            reference = planDiasEntity.reference,
            dia = planDiasEntity.dia,
            fecha = planDiasEntity.fecha,
            idPlanViaje = planDiasEntity.idPlanViaje,
            estado = planDiasEntity.estado
        )
    }

    fun List<PlanDiasModel>.toPlanDiasEntityList(): List<PlanDiasEntity> = this.map { planDiasModel ->
        PlanDiasEntity(
            reference = planDiasModel.reference,
            dia = planDiasModel.dia,
            fecha = planDiasModel.fecha,
            idPlanViaje = planDiasModel.idPlanViaje,
            estado = planDiasModel.estado
        )
    }
}