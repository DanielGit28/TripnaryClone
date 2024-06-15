package com.app.tripnary.ui.lugaresrecomendados.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByIdUseCase
import kotlinx.coroutines.launch

class PerfilLugarViewModel(private val getLugaresRecomendadosByIdUseCase: GetLugaresRecomendadosByIdUseCase) : ViewModel() {
    private val _lugarLiveData = MutableLiveData<LugaresRecomendadosModel>()
    val lugarLiveData : LiveData<LugaresRecomendadosModel> = _lugarLiveData

    fun getLugarRecomendadoById(reference: String) {
        viewModelScope.launch {
            try {
                val lugar = getLugaresRecomendadosByIdUseCase.execute(reference)
                _lugarLiveData.value = lugar
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}