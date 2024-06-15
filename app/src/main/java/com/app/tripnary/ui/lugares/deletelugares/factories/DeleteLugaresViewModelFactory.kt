package com.app.tripnary.ui.lugares.deletelugares.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.DeleteLugarPlanUseCase
import com.app.tripnary.ui.lugares.deletelugares.DeleteLugaresViewModel

class DeleteLugaresViewModelFactory (private val deleteLugarPlanUseCase: DeleteLugarPlanUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DeleteLugaresViewModel(deleteLugarPlanUseCase) as T
}