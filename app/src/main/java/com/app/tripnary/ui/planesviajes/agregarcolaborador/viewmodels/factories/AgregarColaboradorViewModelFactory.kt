package com.app.tripnary.ui.planesviajes.agregarcolaborador.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddColaboradorUseCase
import com.app.tripnary.ui.planesviajes.agregarcolaborador.viewmodels.AgregarColaboradorViewModel

class AgregarColaboradorViewModelFactory(private val addColaboradorUseCase: AddColaboradorUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarColaboradorViewModel(addColaboradorUseCase) as T

}