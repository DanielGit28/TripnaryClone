package com.app.tripnary.ui.hoteles.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddListaDeseosUseCase
import com.app.tripnary.domain.usecases.DeleteListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.ui.hoteles.viewmodels.ListaCiudadesViewModel
import com.app.tripnary.ui.listaDeseos.viewModels.ListaDeseosViewModel

@Suppress("UNCHECKED_CAST")
class ListaCiudadesViewModelFactory(private val getListCiudadesUseCase: GetListCiudadesUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaCiudadesViewModel(
            getListCiudadesUseCase = getListCiudadesUseCase
        ) as T
}