package com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetColaboradoresByReferenceUseCase
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.PerfilColaboradorViewModel

class PerfilColaboradorViewModelFactory(private val getColaboradoresByReferenceUseCase: GetColaboradoresByReferenceUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PerfilColaboradorViewModel(getColaboradoresByReferenceUseCase) as T

}