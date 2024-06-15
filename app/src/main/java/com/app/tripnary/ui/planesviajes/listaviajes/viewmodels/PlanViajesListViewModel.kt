package com.app.tripnary.ui.planesviajes.listaviajes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PlanViajesListViewModel (private val getListViajesReferenceUseCase: GetListViajesReferenceUseCase,) : ViewModel() {

    private val _viajesListLiveData = MutableLiveData<List<PlanViajeModel>>()
    val viajesListLiveData: LiveData<List<PlanViajeModel>>
        get() = _viajesListLiveData


    fun onViewReady(reference: String) {
        viewModelScope.launch {
            getListViajesReferenceUseCase.execute(reference)
                .flowOn(Dispatchers.IO )
                .collect { planes ->
                    _viajesListLiveData.value = planes
                }
        }
    }
}