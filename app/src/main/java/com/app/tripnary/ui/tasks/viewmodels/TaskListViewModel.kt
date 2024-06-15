package com.app.tripnary.ui.tasks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.usecases.GetListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TaskListViewModel (private val getListUseCase: GetListUseCase,) : ViewModel() {

    private val _taskListLiveData = MutableLiveData<List<TaskModel>>()
    val taskListLiveData: LiveData<List<TaskModel>>
        get() = _taskListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { tasks ->
                    _taskListLiveData.value = tasks
                }
        }
    }

}