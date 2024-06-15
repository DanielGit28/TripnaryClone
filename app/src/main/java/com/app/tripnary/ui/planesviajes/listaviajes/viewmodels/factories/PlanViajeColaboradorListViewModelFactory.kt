package com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetPlanesViajesByCorreoColaboradorUseCase
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajeColaboradorListViewModel

class PlanViajeColaboradorListViewModelFactory(private val getPlanesViajesByCorreoColaboradorUseCase: GetPlanesViajesByCorreoColaboradorUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlanViajeColaboradorListViewModel(
            getPlanesViajesByCorreoColaboradorUseCase = getPlanesViajesByCorreoColaboradorUseCase
        ) as T
}