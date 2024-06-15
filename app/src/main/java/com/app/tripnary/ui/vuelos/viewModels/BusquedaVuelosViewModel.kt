package com.app.tripnary.ui.vuelos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class BusquedaVuelosViewModel(private val getVuelosRecomendadosUseCase: GetVuelosRecomendadosUseCase) : ViewModel(){

    private val _vuelosRecomendadosLiveData = MutableLiveData<List<VuelosModel>>()

    val listaVuelosLiveData: LiveData<List<VuelosModel>>
        get() = _vuelosRecomendadosLiveData

    fun getVuelosRecomendados(vuelosRecomendados:Boolean, originLocationCode:String,
                              destinationLocationCode: String, departureDate:String, adults:Int,
                              returnDate:String, max:Int) {
        viewModelScope.launch {
            getVuelosRecomendadosUseCase.execute(vuelosRecomendados, originLocationCode,
                destinationLocationCode, departureDate, adults, returnDate, max)
                .flowOn(Dispatchers.IO )
                .collect { vuelos ->
                    _vuelosRecomendadosLiveData.value = vuelos
                }
        }
    }
}