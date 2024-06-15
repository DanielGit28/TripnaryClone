package com.app.tripnary.ui.equipaje.agregarequipaje.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddEquipajeUseCase
import com.app.tripnary.domain.usecases.DeleteEquipajeUseCase
import com.app.tripnary.domain.usecases.UpdateEquipajeUseCase
import com.app.tripnary.ui.equipaje.agregarequipaje.EquipajeViewModel

@Suppress("UNCHECKED_CAST")
class AgregarEquipajeViewModelFactory (private val addEquipajeUseCase: AddEquipajeUseCase,
                                       private val updateEquipajeUseCase: UpdateEquipajeUseCase,
                                       private val deleteEquipajeUseCase: DeleteEquipajeUseCase) :
    ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EquipajeViewModel(addEquipajeUseCase, updateEquipajeUseCase, deleteEquipajeUseCase) as T

}