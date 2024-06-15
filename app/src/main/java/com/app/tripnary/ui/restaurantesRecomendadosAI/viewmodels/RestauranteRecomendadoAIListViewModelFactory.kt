package com.app.tripnary.ui.restaurantesRecomendadosAI.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.restaurantesRecomendadosAI.SaveRestaurantesRecomendadosAIUseCase

class RestauranteRecomendadoAIListViewModelFactory (private val saveRestaurantesRecomendadosAIUseCase: SaveRestaurantesRecomendadosAIUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RestauranteRecomendadoAIListViewModel(saveRestaurantesRecomendadosAIUseCase) as T
}