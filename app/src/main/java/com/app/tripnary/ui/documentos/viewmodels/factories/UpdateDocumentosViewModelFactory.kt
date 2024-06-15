package com.app.tripnary.ui.documentos.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.UpdateDocumentosUseCase
import com.app.tripnary.ui.documentos.viewmodels.UpdateDocumentosViewModel

class UpdateDocumentosViewModelFactory(private val useCase: UpdateDocumentosUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UpdateDocumentosViewModel(useCase) as T
}