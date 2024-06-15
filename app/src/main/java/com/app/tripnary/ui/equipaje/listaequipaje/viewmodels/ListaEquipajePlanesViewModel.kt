package com.app.tripnary.ui.equipaje.listaequipaje.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.GetListEquipajeUseCase
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListaEquipajePlanesViewModel (private val getListEquipajeUseCase: GetListEquipajeUseCase,) : ViewModel() {

    private val _equipajeListLiveData = MutableLiveData<List<EquipajeModel>>()
    val equipajePlanesListLiveData: LiveData<List<EquipajeModel>>
        get() = _equipajeListLiveData


    fun onViewReady(idPlanViaje: String) {
        viewModelScope.launch {
            getListEquipajeUseCase.execute(idPlanViaje)
                .flowOn(Dispatchers.IO )
                .collect { planes ->
                    _equipajeListLiveData.value = planes
                }
        }
    }
}