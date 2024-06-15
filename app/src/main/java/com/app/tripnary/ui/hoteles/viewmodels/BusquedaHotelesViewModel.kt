package com.app.tripnary.ui.hoteles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.usecases.GetHotelesRecomendadoUseCase
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class BusquedaHotelesViewModel (private val getHotelesRecomendadoUseCase: GetHotelesRecomendadoUseCase) : ViewModel(){

    private val _hotelesRecomendadosLiveData = MutableLiveData<List<HotelesModel>>()

    val listaHotelesLiveData: LiveData<List<HotelesModel>>
        get() = _hotelesRecomendadosLiveData

    fun getHotelesRecomendados(hotelesRecomendados:Boolean, latitud: String, longitud: String, radio: Number) {
        viewModelScope.launch {
            getHotelesRecomendadoUseCase.execute(hotelesRecomendados, latitud, longitud, radio)
                .flowOn(Dispatchers.IO )
                .collect { hoteles ->
                    _hotelesRecomendadosLiveData.value = hoteles
                }
        }
    }
}