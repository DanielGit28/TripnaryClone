package com.app.tripnary.ui.usuarios.listusuarios.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListUsuarioUseCase
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.UsuarioListViewModel

@Suppress("UNCHECKED_CAST")
class UsuarioListViewModelFactory (private val getListUsuarioUseCase: GetListUsuarioUseCase)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UsuarioListViewModel(
            getListUsuarioUseCase = getListUsuarioUseCase
        ) as T
}