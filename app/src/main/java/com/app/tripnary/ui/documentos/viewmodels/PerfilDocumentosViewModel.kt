package com.app.tripnary.ui.documentos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.GetDocumentosByReferenceUseCase
import kotlinx.coroutines.launch

class PerfilDocumentosViewModel(private val getDocumentosByReferenceUseCase: GetDocumentosByReferenceUseCase) : ViewModel() {
    private val _documentoLiveData = MutableLiveData<DocumentosModel>()
    val documentoLiveData : LiveData<DocumentosModel> = _documentoLiveData

    fun getDocumentoByReference(reference: String) {
        viewModelScope.launch {
            try {
                val documento = getDocumentosByReferenceUseCase.execute(reference)
                _documentoLiveData.value = documento
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}