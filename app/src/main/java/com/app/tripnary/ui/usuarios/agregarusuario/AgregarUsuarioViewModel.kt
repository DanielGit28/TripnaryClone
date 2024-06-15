package com.app.tripnary.ui.usuarios.agregarusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.AddLugarPropioUseCase
import com.app.tripnary.domain.usecases.AddUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarUsuarioViewModel  (private val addUsuarioUseCase: AddUsuarioUseCase)
    : ViewModel() {

    private val _usuarioUpdateLiveData = MutableLiveData<UsuariosModel?>()
    val usuarioAddedLiveData: LiveData<UsuariosModel?>
        get() = _usuarioUpdateLiveData

    fun agregarUsuario(usuariosModel: UsuariosModel): LiveData<UsuariosModel?> {
        val addUsuarioLiveData = MutableLiveData<UsuariosModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addUsuario = addUsuarioUseCase.execute(usuariosModel)
            withContext(Dispatchers.Main) {
                _usuarioUpdateLiveData.value = addUsuario
            }
        }
        return addUsuarioLiveData
    }
}