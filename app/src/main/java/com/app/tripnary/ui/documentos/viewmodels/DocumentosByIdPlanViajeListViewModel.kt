package com.app.tripnary.ui.documentos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.GetDocumentosByIdPlanViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DocumentosByIdPlanViajeListViewModel(private val getDocumentosByIdPlanViajeUseCase: GetDocumentosByIdPlanViajeUseCase) : ViewModel() {
    private val _documentosLisLiveData = MutableLiveData<List<DocumentosModel>>()

    val documentosLiveData : LiveData<List<DocumentosModel>>
        get() = _documentosLisLiveData

    fun onViewReady(idPlanViaje: String) {
        try {
            viewModelScope.launch {
                getDocumentosByIdPlanViajeUseCase.execute(idPlanViaje)
                    .flowOn(Dispatchers.IO)
                    .collect { documentos ->
                        _documentosLisLiveData.value = documentos
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}