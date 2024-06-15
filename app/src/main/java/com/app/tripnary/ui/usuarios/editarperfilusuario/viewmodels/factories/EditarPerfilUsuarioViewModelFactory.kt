package com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.EditarPerfilUsuarioUseCase
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.EditarPerfilUsuarioViewModel

class EditarPerfilUsuarioViewModelFactory(private val useCase: EditarPerfilUsuarioUseCase) :ViewModelProvider.Factory {
    override fun  <T: ViewModel> create(modelClass: Class<T>) : T =
        EditarPerfilUsuarioViewModel(useCase) as T
}