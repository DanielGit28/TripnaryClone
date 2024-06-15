package com.app.tripnary.ui.planesviajes.eliminarviaje.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.DeleteLugarPlanUseCase
import com.app.tripnary.domain.usecases.DeletePlanViajeUseCase
import com.app.tripnary.ui.lugares.deletelugares.DeleteLugaresViewModel
import com.app.tripnary.ui.planesviajes.eliminarviaje.EliminarViajeViewModel

class EliminarViajeViewModelFactory (private val deletePlanViajeUseCase: DeletePlanViajeUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EliminarViajeViewModel(deletePlanViajeUseCase) as T
}