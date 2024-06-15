package com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetDiasByIdPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListDeseosViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel

@Suppress("UNCHECKED_CAST")
class PlanDiasListDeseosViewModelFactory (private val getDiasByIdPlanViaje: GetDiasByIdPlanViajeUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlanDiasListDeseosViewModel(
            getDiasByIdPlanViaje = getDiasByIdPlanViaje
        ) as T
}