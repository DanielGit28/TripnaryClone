package com.app.tripnary.ui.usuarios.agregarusuario.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddLugarPropioUseCase
import com.app.tripnary.domain.usecases.AddUsuarioUseCase
import com.app.tripnary.ui.lugares.agregarlugarespropios.AgregarLugarPropioViewModel
import com.app.tripnary.ui.usuarios.agregarusuario.AgregarUsuarioViewModel

class AgregarUsuarioViewModelFactory (private val addUsuarioUseCase: AddUsuarioUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AgregarUsuarioViewModel(addUsuarioUseCase) as T
}