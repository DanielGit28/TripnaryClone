package com.app.tripnary.ui.tiquetes.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddTiqueteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel
import com.app.tripnary.ui.tiquetes.viewmodel.AgregarTiqueteViewModel

class AgregarTiqueteViewModelFactory(private val agregarTiqueteUseCase: AddTiqueteUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarTiqueteViewModel(
            agregarTiqueteUseCase = agregarTiqueteUseCase
        ) as T
}