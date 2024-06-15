package com.app.tripnary.ui.documentos.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddDocumentosUseCase
import com.app.tripnary.ui.documentos.viewmodels.AddDocumentosPlanViajeViewModel

class AddDocumentosPlanViajeViewModelFactory(private val useCase: AddDocumentosUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddDocumentosPlanViajeViewModel(useCase) as T

}