package com.app.tripnary.ui.documentos.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetDocumentosByReferenceUseCase
import com.app.tripnary.ui.documentos.viewmodels.DocumentosByIdPlanViajeListViewModel
import com.app.tripnary.ui.documentos.viewmodels.PerfilDocumentosViewModel

class PerfilDocumentosViewModelFactory(private val getDocumentosByReferenceUseCase: GetDocumentosByReferenceUseCase) :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PerfilDocumentosViewModel(getDocumentosByReferenceUseCase) as T

}