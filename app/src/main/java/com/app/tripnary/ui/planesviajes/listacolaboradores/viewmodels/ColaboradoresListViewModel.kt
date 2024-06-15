package com.app.tripnary.ui.planesviajes.listacolaboradores.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.GetColaboradoresByViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class ColaboradoresListViewModel(private val getColaboradoresByViajeUseCase: GetColaboradoresByViajeUseCase) : ViewModel() {
    private val _colaboradoresLiveData = MutableLiveData<List<ColaboradoresModel>>()

    val colaboradoresLiveData: LiveData<List<ColaboradoresModel>>
        get() = _colaboradoresLiveData

    fun onViewReady(idPlanViaje: String) {
        try {
            viewModelScope.launch{
                getColaboradoresByViajeUseCase.execute(idPlanViaje)
                    .flowOn(Dispatchers.IO)
                    .collect{ colaboradores ->
                        _colaboradoresLiveData.value = colaboradores
                    }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}