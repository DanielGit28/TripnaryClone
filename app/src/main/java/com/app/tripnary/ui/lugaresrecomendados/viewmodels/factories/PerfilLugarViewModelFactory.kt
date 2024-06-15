package com.app.tripnary.ui.lugaresrecomendados.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByIdUseCase
import com.app.tripnary.ui.lugaresrecomendados.viewmodels.PerfilLugarViewModel

class PerfilLugarViewModelFactory(private val getLugaresRecomendadosByIdUseCase: GetLugaresRecomendadosByIdUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PerfilLugarViewModel(getLugaresRecomendadosByIdUseCase) as T
}