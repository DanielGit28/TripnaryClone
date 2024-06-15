package com.app.tripnary.ui.main.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListUseCase
import com.app.tripnary.ui.tasks.viewmodels.TaskListViewModel

@Suppress("UNCHECKED_CAST")
class TaskListViewModelFactory (private val getListUseCase: GetListUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TaskListViewModel(
            getListUseCase = getListUseCase
        ) as T
}