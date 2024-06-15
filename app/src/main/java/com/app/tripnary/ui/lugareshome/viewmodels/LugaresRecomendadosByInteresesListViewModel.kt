package com.app.tripnary.ui.lugareshome.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByInteresesUseCase
import kotlinx.coroutines.launch

class LugaresRecomendadosByInteresesListViewModel(private val getLugaresRecomendadosByInteresesUseCase: GetLugaresRecomendadosByInteresesUseCase) : ViewModel() {
    private val _lugaresListLiveData = MutableLiveData<List<LugaresRecomendadosModel>>()

    val lugaresLiveData: LiveData<List<LugaresRecomendadosModel>>
        get() = _lugaresListLiveData

    fun onViewReady(reference: String) {
        viewModelScope.launch {
            try {
                val lugares = getLugaresRecomendadosByInteresesUseCase.execute(reference)
                _lugaresListLiveData.value = lugares
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}