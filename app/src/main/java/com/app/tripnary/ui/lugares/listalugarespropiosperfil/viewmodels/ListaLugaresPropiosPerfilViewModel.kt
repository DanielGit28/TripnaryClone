package com.app.tripnary.ui.lugares.listalugarespropiosperfil.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.GetListLugaresPropiosPerfilUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosPerfilUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListaLugaresPropiosPerfilViewModel
    (private val getListLugaresPropiosPerfilUseCase: GetListLugaresPropiosPerfilUseCase,)
    : ViewModel() {

    private val _viajesListLiveData = MutableLiveData<List<LugarPropioModel>>()
    val lugaresPropiosListLiveData: LiveData<List<LugarPropioModel>>
        get() = _viajesListLiveData


    fun onViewReady(idPlanViajeLugar: String) {
        viewModelScope.launch {
            getListLugaresPropiosPerfilUseCase.execute(idPlanViajeLugar)
                .flowOn(Dispatchers.IO )
                .collect { planes ->
                    _viajesListLiveData.value = planes
                }
        }
    }
}