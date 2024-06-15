package com.app.tripnary.ui.planesviajes.listaviajes.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.GetPlanesViajesByCorreoColaboradorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PlanViajeColaboradorListViewModel(private val getPlanesViajesByCorreoColaboradorUseCase: GetPlanesViajesByCorreoColaboradorUseCase) : ViewModel() {

    private val _viajesColaboradorListLiveData = MutableLiveData<List<PlanViajeModel>>()

    val viajesColaboradorListLiveData: LiveData<List<PlanViajeModel>>
        get() = _viajesColaboradorListLiveData

    fun onViewReady(correo: String) {
        viewModelScope.launch{
            getPlanesViajesByCorreoColaboradorUseCase.execute(correo)
                .flowOn(Dispatchers.IO)
                .collect { planes ->
                    _viajesColaboradorListLiveData.value = planes
                }
        }
    }
}