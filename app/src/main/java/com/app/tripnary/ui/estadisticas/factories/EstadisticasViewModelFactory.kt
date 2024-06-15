package com.app.tripnary.ui.estadisticas.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.DeletePlanViajeUseCase
import com.app.tripnary.domain.usecases.GetEstadisticasUseCase
import com.app.tripnary.ui.estadisticas.EstadisticasViewModel
import com.app.tripnary.ui.planesviajes.eliminarviaje.EliminarViajeViewModel

class EstadisticasViewModelFactory (private val getEstadisticasUseCase: GetEstadisticasUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EstadisticasViewModel(getEstadisticasUseCase) as T
}