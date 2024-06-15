package com.app.tripnary.ui.continentes.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListContinenteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.continentes.viewmodels.ContinenteListViewModel
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel

@Suppress("UNCHECKED_CAST")
class ContinenteListViewModelFactory (private val getListContinenteUseCase: GetListContinenteUseCase)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ContinenteListViewModel(
            getListContinenteUseCase = getListContinenteUseCase
        ) as T


}