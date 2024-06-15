package com.app.tripnary.ui.vuelos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import com.app.tripnary.ui.listaDeseos.viewModels.ListaDeseosViewModel
import com.app.tripnary.ui.vuelos.viewModels.BusquedaVuelosViewModel

@Suppress("UNCHECKED_CAST")
class BusquedaVuelosViewModelFactory (private val getVuelosRecomendadosUseCase: GetVuelosRecomendadosUseCase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BusquedaVuelosViewModel(
            getVuelosRecomendadosUseCase = getVuelosRecomendadosUseCase,
        ) as T
}