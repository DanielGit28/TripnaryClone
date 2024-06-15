package com.app.tripnary.data.repositories

import android.util.Log
import com.app.tripnary.data.api.servicesimpl.PlanDiasServiceImpl
import com.app.tripnary.data.api.servicesimpl.TaskServiceImpl
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.datasources.DatabaseTaskDataSource
import com.app.tripnary.data.mappers.PlanDiasMapper.toPlanDiasEntityList
import com.app.tripnary.data.mappers.PlanDiasMapper.toPlanDiasModelList
import com.app.tripnary.data.mappers.TaskMapper.toTaskEntityList
import com.app.tripnary.data.mappers.TaskMapper.toTaskModelList
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.repositories.PlanDiasRepository
import com.app.tripnary.domain.repositories.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlanDiasRepositoryImpl (private val planDiasDataSource: DatabasePlanDiasDataSource,)
    : PlanDiasRepository {

    private val planDiasService = PlanDiasServiceImpl()

    override fun getAllDias(): Flow<List<PlanDiasModel>> {
        planDiasService.getAllDias { diasModels ->
            if (diasModels != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    planDiasDataSource.clearDias()
                    planDiasDataSource.insertDias(diasModels.toPlanDiasEntityList())
                }

            } else {

            }
        }
        return planDiasDataSource.getAllDias()
            .map { dias -> dias.toPlanDiasModelList()  }
    }

    override fun getDiasByIdPlanViaje(idPlanViaje: String?): Flow<List<PlanDiasModel>> {
        planDiasService.getDiasByIdPlanViaje (idPlanViaje) { diasModels ->
            if (diasModels != null) {
                Log.e("Dias Model", diasModels.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    planDiasDataSource.clearDias()
                    planDiasDataSource.insertDias(diasModels.toPlanDiasEntityList())
                }

            } else {

            }
        }
        return planDiasDataSource.getAllDias()
            .map { dias -> dias.toPlanDiasModelList()  }
    }
}