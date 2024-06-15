package com.app.tripnary.ui.lugares.listalugaresplanes.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import com.app.tripnary.ui.lugares.listalugaresplanes.ListaLugaresPlanesViewModel

@Suppress("UNCHECKED_CAST")
class ListaLugaresPlanesViewModelFactory (private val getListLugarPlanByPlanViajeUseCase: GetListLugarPlanByPlanViajeUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaLugaresPlanesViewModel(
            getListLugarPlanByPlanViajeUseCase = getListLugarPlanByPlanViajeUseCase
        ) as T
}