package com.app.tripnary.ui.lugares.listalugares.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LugarListViewModel (private val getListLugaresRecomendadosUseCase: GetListLugaresRecomendadosUseCase,) : ViewModel() {

    private val _lugarListLiveData = MutableLiveData<List<LugaresRecomendadosModel>>()
    val lugarListLiveData: LiveData<List<LugaresRecomendadosModel>>
        get() = _lugarListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListLugaresRecomendadosUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { lugares ->
                    _lugarListLiveData.value = lugares
                }
        }
    }
}