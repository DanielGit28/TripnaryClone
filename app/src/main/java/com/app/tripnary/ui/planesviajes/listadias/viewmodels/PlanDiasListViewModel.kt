package com.app.tripnary.ui.planesviajes.listadias.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PlanDiasListViewModel (private val getListDiasPlanes: GetListDiasPlanesUseCase,) : ViewModel() {

    private val _diasListLiveData = MutableLiveData<List<PlanDiasModel>>()
    val diasListLiveData: LiveData<List<PlanDiasModel>>
        get() = _diasListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListDiasPlanes.execute()
                .flowOn(Dispatchers.IO )
                .collect { dias ->
                    _diasListLiveData.value = dias
                }
        }
    }
}