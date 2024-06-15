package com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.GetPerfilUsuarioByEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetPerfilUsuarioByEmailViewModel(private val getPerfilUsuarioByEmailUseCase: GetPerfilUsuarioByEmailUseCase) : ViewModel() {
    private val _usuarioLiveData = MutableLiveData<List<UsuariosModel>>()
    val usuarioLiveData: LiveData<List<UsuariosModel>> = _usuarioLiveData

    fun getUsuarioByEmail(correoElectronico: String) {
        viewModelScope.launch {
            try {
                val usuario = getPerfilUsuarioByEmailUseCase.execute(correoElectronico)
                _usuarioLiveData.value = usuario
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}