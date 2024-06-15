package com.app.tripnary.ui.documentos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.AddDocumentosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDocumentosPlanViajeViewModel(private val addDocumentosUseCase: AddDocumentosUseCase) : ViewModel() {
    private val _documentoAddedLiveData = MutableLiveData<Unit>()

    fun addDocumentosPlanViaje(nombre: String, idPlanViaje: String, url: String) {

        val documentosModel = DocumentosModel(
            reference = "",
            estado = "Activo",
            idPlanViaje = idPlanViaje,
            idLugarPlan = "",
            idLugar = "",
            nombre = nombre,
            url = url
        )

        viewModelScope.launch(Dispatchers.IO) {
            addDocumentosUseCase.execute(documentosModel)
            withContext(Dispatchers.Main) {
                _documentoAddedLiveData.value = Unit
            }
        }
    }
}