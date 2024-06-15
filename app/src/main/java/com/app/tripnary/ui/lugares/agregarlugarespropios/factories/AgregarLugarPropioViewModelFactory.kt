package com.app.tripnary.ui.lugares.agregarlugarespropios.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.AddLugarPropioUseCase
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugarespropios.AgregarLugarPropioViewModel

class AgregarLugarPropioViewModelFactory (private val addLugarPropioUseCase: AddLugarPropioUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarLugarPropioViewModel(addLugarPropioUseCase) as T
}