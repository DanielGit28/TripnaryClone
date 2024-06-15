package com.app.tripnary.ui.restaurantesRecomendadosAI.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.usecases.restaurantesRecomendadosAI.SaveRestaurantesRecomendadosAIUseCase
import kotlinx.coroutines.launch

class RestauranteRecomendadoAIListViewModel (private val saveRestaurantesRecomendadosAIUseCase: SaveRestaurantesRecomendadosAIUseCase) : ViewModel() {

    private val _restaurantesRecomendadosAIListLiveData = MutableLiveData<String>()
    val restaurantesRecomendadosAIListLiveData: LiveData<String>
        get() = _restaurantesRecomendadosAIListLiveData


    fun onViewReady(interes: InteresesRestaurantesAIModel,
                    referencePlanViaje: String,
                    tipo: String) {
        viewModelScope.launch {
            try {
                val response = saveRestaurantesRecomendadosAIUseCase.execute(interes, referencePlanViaje,tipo)
                Log.d("Live data update restaurantes ai", response)
                _restaurantesRecomendadosAIListLiveData.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}