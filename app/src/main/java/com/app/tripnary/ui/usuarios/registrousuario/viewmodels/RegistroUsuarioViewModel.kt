package com.app.tripnary.ui.usuarios.registrousuario.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.RegistroUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroUsuarioViewModel(private val registroUsuarioUseCase: RegistroUsuarioUseCase) : ViewModel() {
    private val _usuarioAddedLiveData = MutableLiveData<Unit>()

    fun updateUsuario(reference: String, nombre: String, correoElectronico: String, contrasennia: String) {
        val usuariosModel =
            UsuariosModel (
                reference = reference,
                nombre = nombre,
                correoElectronico = correoElectronico,
                contrasennia = contrasennia,
                estado = "",
                fechaNacimiento = "",
                fotoPerfil = "",
                idInteresesGenerales = "",
                latitudDireccion = "",
                longitudDireccion = "",
                roles = arrayOf("Usuario"),
                telefono = ""
            )

        viewModelScope.launch(Dispatchers.IO) {
            registroUsuarioUseCase.execute(usuariosModel)
            withContext(Dispatchers.Main) {
                _usuarioAddedLiveData.value = Unit
            }
        }
    }
}