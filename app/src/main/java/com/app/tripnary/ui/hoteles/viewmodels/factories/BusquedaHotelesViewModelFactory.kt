package com.app.tripnary.ui.hoteles.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetHotelesRecomendadoUseCase
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import com.app.tripnary.ui.hoteles.viewmodels.BusquedaHotelesViewModel
import com.app.tripnary.ui.vuelos.viewModels.BusquedaVuelosViewModel

class BusquedaHotelesViewModelFactory (private val getHotelesRecomendadoUseCase: GetHotelesRecomendadoUseCase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BusquedaHotelesViewModel(
            getHotelesRecomendadoUseCase = getHotelesRecomendadoUseCase,
        ) as T
}