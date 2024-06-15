package com.app.tripnary.ui.documentos.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetDocumentosByIdPlanViajeUseCase
import com.app.tripnary.ui.documentos.viewmodels.DocumentosByIdPlanViajeListViewModel

class DocumentosByIdPlanViajeListViewModelFactory(private val getDocumentosByIdPlanViajeUseCase: GetDocumentosByIdPlanViajeUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DocumentosByIdPlanViajeListViewModel(
            getDocumentosByIdPlanViajeUseCase = getDocumentosByIdPlanViajeUseCase
        ) as T
}