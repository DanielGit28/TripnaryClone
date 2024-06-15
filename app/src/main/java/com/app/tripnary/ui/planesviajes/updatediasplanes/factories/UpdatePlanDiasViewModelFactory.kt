package com.app.tripnary.ui.planesviajes.updatediasplanes.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import com.app.tripnary.ui.planesviajes.updatediasplanes.UpdatePlanDiasViewModel

@Suppress("UNCHECKED_CAST")
class UpdatePlanDiasViewModelFactory (private val useCase: UpdatePlanDiasUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UpdatePlanDiasViewModel(useCase) as T
}