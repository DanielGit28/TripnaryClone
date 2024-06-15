package com.app.tripnary.ui.lugareshome.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByPopularidadUseCase
import kotlinx.coroutines.launch

class LugaresRecomendadosByPopularidadListViewModel(private val getLugaresRecomendadosByPopularidadUseCase: GetLugaresRecomendadosByPopularidadUseCase) : ViewModel() {
    private val _lugaresListLiveData = MutableLiveData<List<LugaresRecomendadosModel>>()

    val lugaresLiveData: LiveData<List<LugaresRecomendadosModel>>
        get() = _lugaresListLiveData

    fun onViewReady() {
        viewModelScope.launch {
            try {
                val lugares = getLugaresRecomendadosByPopularidadUseCase.execute()
                _lugaresListLiveData.value = lugares
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}