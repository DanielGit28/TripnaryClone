package com.app.tripnary.ui.hoteles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.usecases.GetColaboradoresByReferenceUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListaCiudadesViewModel(private val getListCiudadesUseCase: GetListCiudadesUseCase) : ViewModel() {
    private val _ciudadesLiveData = MutableLiveData<List<CiudadModel>>()

    val listaCiudadsLiveData: LiveData<List<CiudadModel>>
        get() = _ciudadesLiveData

    fun getCidadesList() {
        viewModelScope.launch {
            getListCiudadesUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { listaCiudades ->
                    _ciudadesLiveData.value = listaCiudades
                }
        }
    }
}