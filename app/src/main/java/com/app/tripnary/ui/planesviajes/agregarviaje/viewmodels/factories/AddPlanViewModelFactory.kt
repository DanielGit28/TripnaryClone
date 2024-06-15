package com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.domain.usecases.AddTaskUseCase
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.AgregarPlanViewModel
import com.app.tripnary.ui.tasks.addtasks.viewmodels.AddTaskViewModel

@Suppress("UNCHECKED_CAST")
class AddPlanViewModelFactory (private val useCase: AddPlanViajeUseCase) :
    ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarPlanViewModel(useCase) as T

}