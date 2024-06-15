package com.app.tripnary.ui.tasks.addtasks.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddTaskUseCase
import com.app.tripnary.ui.tasks.addtasks.viewmodels.AddTaskViewModel

@Suppress("UNCHECKED_CAST")
class AddTaskViewModelFactory (private val useCase: AddTaskUseCase) :
    ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddTaskViewModel(useCase) as T

}