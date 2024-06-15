package com.app.tripnary.ui.planesviajes.listadias.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.usecases.GetDiasByIdPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PlanDiasListDeseosViewModel (private val getDiasByIdPlanViaje: GetDiasByIdPlanViajeUseCase) : ViewModel() {
    private val _diasPlanListLiveData = MutableLiveData<List<PlanDiasModel>>()

    val diasPlanListLiveData: LiveData<List<PlanDiasModel>>
        get() = _diasPlanListLiveData

    fun getDiasByIdPlanViaje(idPlanViaje: String?){
        viewModelScope.launch {
            getDiasByIdPlanViaje.execute(idPlanViaje)
                .flowOn(Dispatchers.IO )
                .collect { dias ->
                    _diasPlanListLiveData.value = dias
                }
        }
    }
}