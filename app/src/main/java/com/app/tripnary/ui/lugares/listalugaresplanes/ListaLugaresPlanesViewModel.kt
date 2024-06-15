package com.app.tripnary.ui.lugares.listalugaresplanes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListaLugaresPlanesViewModel (private val getListLugarPlanByPlanViajeUseCase: GetListLugarPlanByPlanViajeUseCase,) : ViewModel() {

    private val _viajesListLiveData = MutableLiveData<List<LugarPlanModel>>()
    val lugaresPlanesListLiveData: LiveData<List<LugarPlanModel>>
        get() = _viajesListLiveData


    fun onViewReady(idPlanViaje: String) {
        viewModelScope.launch {
            getListLugarPlanByPlanViajeUseCase.execute(idPlanViaje)
                .flowOn(Dispatchers.IO )
                .collect { planes ->
                    _viajesListLiveData.value = planes
                }
        }
    }
}