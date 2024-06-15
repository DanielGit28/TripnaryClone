package com.app.tripnary.ui.paises.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.domain.usecases.GetListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PaisListViewModel (private val getListPaisUseCase: GetListPaisUseCase,) : ViewModel() {

    private val _paisListLiveData = MutableLiveData<List<PaisModel>>()
    val paisListLiveData: LiveData<List<PaisModel>>
        get() = _paisListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListPaisUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { paises ->
                    _paisListLiveData.value = paises
                }
        }
    }

}