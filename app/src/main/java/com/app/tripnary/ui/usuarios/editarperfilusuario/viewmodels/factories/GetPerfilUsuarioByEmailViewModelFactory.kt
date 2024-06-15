package com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetPerfilUsuarioByEmailUseCase
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.GetPerfilUsuarioByEmailViewModel

class GetPerfilUsuarioByEmailViewModelFactory(private val useCase: GetPerfilUsuarioByEmailUseCase) : ViewModelProvider.Factory {

    override fun  <T: ViewModel> create(modelClass: Class<T>) : T =
        GetPerfilUsuarioByEmailViewModel(useCase) as T
}