package com.app.tripnary.ui.equipaje.listaequipaje.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListEquipajeUseCase
import com.app.tripnary.ui.equipaje.listaequipaje.viewmodels.ListaEquipajePlanesViewModel

@Suppress("UNCHECKED_CAST")
class ListaEquipajePlanesViewModelFactory (private val getListEquipajeUseCase: GetListEquipajeUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaEquipajePlanesViewModel(
            getListEquipajeUseCase = getListEquipajeUseCase
        ) as T
}