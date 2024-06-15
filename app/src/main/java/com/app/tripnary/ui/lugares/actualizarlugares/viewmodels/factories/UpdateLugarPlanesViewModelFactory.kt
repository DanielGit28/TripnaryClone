package com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.UpdateLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel

class UpdateLugarPlanesViewModelFactory (private val updateLugarPlanesUseCase: UpdateLugarPlanesUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UpdateLugarPlanesViewModel(updateLugarPlanesUseCase) as T
}