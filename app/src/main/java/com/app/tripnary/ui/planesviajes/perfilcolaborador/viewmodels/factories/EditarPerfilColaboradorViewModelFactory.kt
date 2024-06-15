package com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.UpdateColaboradorUseCase
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.EditarPerfilColaboradorViewModel

class EditarPerfilColaboradorViewModelFactory(private val useCase: UpdateColaboradorUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EditarPerfilColaboradorViewModel(useCase) as T

}