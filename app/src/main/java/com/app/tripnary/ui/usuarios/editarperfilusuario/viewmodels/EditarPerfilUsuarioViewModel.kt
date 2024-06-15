package com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.EditarPerfilUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarPerfilUsuarioViewModel(private val editarPerfilUsuarioUseCase: EditarPerfilUsuarioUseCase) : ViewModel() {
    private val _usuarioAddedLiveData = MutableLiveData<Unit>()

    fun updatePerfilUsuario(reference: String, nombre: String, correoElectronico: String, fechaNacimiento: String?, latitud: String, longitud: String, telefono: String?) {
        val usuariosModel =
            UsuariosModel (
                reference = reference,
                nombre = nombre,
                correoElectronico = correoElectronico,
                contrasennia = "",
                estado = "",
                fechaNacimiento = fechaNacimiento,
                fotoPerfil = "",
                idInteresesGenerales = "",
                latitudDireccion = latitud,
                longitudDireccion = longitud,
                roles = arrayOf("Usuario"),
                telefono = telefono
            )

        viewModelScope.launch(Dispatchers.IO) {
            editarPerfilUsuarioUseCase.execute(usuariosModel)
            withContext(Dispatchers.Main) {
                _usuarioAddedLiveData.value = Unit
            }
        }
    }
}
