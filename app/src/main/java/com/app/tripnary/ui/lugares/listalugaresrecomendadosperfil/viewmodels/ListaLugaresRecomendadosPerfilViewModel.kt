package com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosPerfilUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListaLugaresRecomendadosPerfilViewModel
    (private val getListLugaresRecomendadosPerfilUseCase: GetListLugaresRecomendadosPerfilUseCase,)
    : ViewModel() {

    private val _viajesListLiveData = MutableLiveData<List<LugaresRecomendadosModel>>()
    val lugaresRecomendadosListLiveData: LiveData<List<LugaresRecomendadosModel>>
        get() = _viajesListLiveData


    fun onViewReady(idPlanViajeLugarRecomendado: String) {
        viewModelScope.launch {
            getListLugaresRecomendadosPerfilUseCase.execute(idPlanViajeLugarRecomendado)
                .flowOn(Dispatchers.IO )
                .collect { planes ->
                    _viajesListLiveData.value = planes
                }
        }
    }
}