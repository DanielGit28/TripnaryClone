package com.app.tripnary.ui.tasks.addtasks.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.usecases.AddTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskViewModel (private val addTaskUseCase: AddTaskUseCase)
    : ViewModel(){


    private val _displayErrorMessageLiveData = MutableLiveData<Int>()
    val displayErrorMessageLiveData: LiveData<Int>
        get() = _displayErrorMessageLiveData
    private val _noteAddedLiveData = MutableLiveData<Unit>()
    val noteAddedLiveData: LiveData<Unit>
        get() = _noteAddedLiveData


    fun addTask(text: String, id: String, done: Boolean) {
        val taskModel = TaskModel(
            reference = "",
            id = id,
            text = text,
            done = done
        )
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase.execute(taskModel)
            withContext(Dispatchers.Main) {
                _noteAddedLiveData.value = Unit
            }
        }
    }

}