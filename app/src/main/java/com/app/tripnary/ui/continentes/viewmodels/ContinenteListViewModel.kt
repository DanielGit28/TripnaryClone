package com.app.tripnary.ui.continentes.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ContinenteModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.usecases.GetListContinenteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ContinenteListViewModel (private val getListContinenteUseCase: GetListContinenteUseCase,)
    : ViewModel()  {

    private val _continenteListLiveData = MutableLiveData<List<ContinenteModel>>()
    val continenteListLiveData: LiveData<List<ContinenteModel>>
        get() = _continenteListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListContinenteUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { continentes ->
                    _continenteListLiveData.value = continentes
                }
        }
    }
}