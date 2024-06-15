package com.app.tripnary.ui.listaDeseos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddListaDeseosUseCase
import com.app.tripnary.domain.usecases.DeleteListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.ui.listaDeseos.viewModels.ListaDeseosViewModel

@Suppress("UNCHECKED_CAST")
class ListaDeseosViewModelFactory (private val getListaDeseosUseCase: GetListaDeseosUseCase, private val addListaDeseosUseCase: AddListaDeseosUseCase, private val deleteListaDeseosUseCase: DeleteListaDeseosUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaDeseosViewModel(
            getListaDeseosUseCase = getListaDeseosUseCase,
            addListaDeseosUseCase = addListaDeseosUseCase,
            deleteListaDeseosUseCase = deleteListaDeseosUseCase
        ) as T
}