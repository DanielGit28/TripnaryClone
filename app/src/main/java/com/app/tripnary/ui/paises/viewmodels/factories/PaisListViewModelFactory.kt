package com.app.tripnary.ui.paises.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.domain.usecases.GetListUseCase
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel
import com.app.tripnary.ui.tasks.viewmodels.TaskListViewModel

@Suppress("UNCHECKED_CAST")
class PaisListViewModelFactory (private val getListPaisUseCase: GetListPaisUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PaisListViewModel(
            getListPaisUseCase = getListPaisUseCase
        ) as T
}