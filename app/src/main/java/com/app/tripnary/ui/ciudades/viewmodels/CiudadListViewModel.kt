package com.app.tripnary.ui.ciudades.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CiudadListViewModel (private val getListCiudadesPaisUseCase: GetListCiudadesPaisUseCase,) : ViewModel() {

    private val _ciudadListLiveData = MutableLiveData<List<CiudadModel>>()
    val ciudadListLiveData: LiveData<List<CiudadModel>>
        get() = _ciudadListLiveData


    fun onViewReady(idPais: String) {
        viewModelScope.launch {
            getListCiudadesPaisUseCase.execute(idPais)
                .flowOn(Dispatchers.IO )
                .collect { ciudades ->
                    _ciudadListLiveData.value = ciudades
                }
        }
    }
}