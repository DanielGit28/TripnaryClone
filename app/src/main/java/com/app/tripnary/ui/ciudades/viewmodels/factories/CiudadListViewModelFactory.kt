package com.app.tripnary.ui.ciudades.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.ciudades.viewmodels.CiudadListViewModel
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel

@Suppress("UNCHECKED_CAST")
class CiudadListViewModelFactory (private val getListCiudadesPaisUseCase: GetListCiudadesPaisUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CiudadListViewModel(
            getListCiudadesPaisUseCase = getListCiudadesPaisUseCase
        ) as T
}