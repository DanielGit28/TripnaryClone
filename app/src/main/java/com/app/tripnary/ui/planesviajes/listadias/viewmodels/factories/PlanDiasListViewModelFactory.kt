package com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel

@Suppress("UNCHECKED_CAST")
class PlanDiasListViewModelFactory (private val getListDiasPlanes: GetListDiasPlanesUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlanDiasListViewModel(
            getListDiasPlanes = getListDiasPlanes
        ) as T
}