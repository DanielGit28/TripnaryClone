package com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListUseCase
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajesListViewModel
import com.app.tripnary.ui.tasks.viewmodels.TaskListViewModel

class PlanViajeListViewModelFactory (private val getListViajesReferenceUseCase: GetListViajesReferenceUseCase)
    : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlanViajesListViewModel(
            getListViajesReferenceUseCase = getListViajesReferenceUseCase
        ) as T
}