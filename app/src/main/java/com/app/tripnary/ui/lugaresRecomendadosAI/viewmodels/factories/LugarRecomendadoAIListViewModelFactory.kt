package com.app.tripnary.ui.lugaresRecomendadosAI.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.lugaresRecomendadosAI.GetLugaresRecomendadosAIByInteresUseCase
import com.app.tripnary.ui.lugaresRecomendadosAI.viewmodels.LugarRecomendadoAIListViewModel

@Suppress("UNCHECKED_CAST")
class LugarRecomendadoAIListViewModelFactory (private val getLugaresRecomendadosAIByInteresUseCase: GetLugaresRecomendadosAIByInteresUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LugarRecomendadoAIListViewModel(getLugaresRecomendadosAIByInteresUseCase) as T
}