package com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.planesviajes.updatediasplanes.UpdatePlanDiasViewModel

class AgregarLugarPlanesViewModelFactory (private val getAddLugarPlanUseCase: AddLugarPlanUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarLugarPlanesViewModel(getAddLugarPlanUseCase) as T
}