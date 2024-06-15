package com.app.tripnary.ui.usuarios.registrousuario.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.RegistroUsuarioUseCase
import com.app.tripnary.ui.usuarios.registrousuario.viewmodels.RegistroUsuarioViewModel

class RegistroUsuarioViewModelFactory(private val useCase: RegistroUsuarioUseCase) : ViewModelProvider.Factory {
    override fun  <T: ViewModel> create(modelClass: Class<T>) : T =
        RegistroUsuarioViewModel(useCase) as T
}