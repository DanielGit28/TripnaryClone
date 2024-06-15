package com.app.tripnary.ui.planesviajes.actualizarplanviaje.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.domain.usecases.UpdatePlanViajeUseCase
import com.app.tripnary.ui.planesviajes.actualizarplanviaje.viewmodels.UpdatePlanViajeViewModel
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.AgregarPlanViewModel

@Suppress("UNCHECKED_CAST")
class UpdatePlanViajeViewModelFactory (private val updatePlanViajeUseCase: UpdatePlanViajeUseCase) :
    ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UpdatePlanViajeViewModel(updatePlanViajeUseCase) as T

}