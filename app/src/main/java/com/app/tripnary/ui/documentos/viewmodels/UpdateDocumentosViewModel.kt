package com.app.tripnary.ui.documentos.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.UpdateDocumentosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateDocumentosViewModel(private val updateDocumentosUseCase: UpdateDocumentosUseCase) : ViewModel() {
    private val _documentoLiveData = MutableLiveData<Unit>()

    fun updateDocumento(reference: String, nombre: String) {
        val documentoModel =
            DocumentosModel(
                reference = reference,
                nombre = nombre,
                estado = "",
                idLugar = "",
                idLugarPlan = "",
                idPlanViaje = "",
                url = ""
            )

        viewModelScope.launch(Dispatchers.IO) {
            updateDocumentosUseCase.execute(documentoModel)
            withContext(Dispatchers.Main) {
                _documentoLiveData.value = Unit
            }
        }
    }
}