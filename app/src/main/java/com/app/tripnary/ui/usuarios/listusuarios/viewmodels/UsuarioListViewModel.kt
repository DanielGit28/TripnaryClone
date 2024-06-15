package com.app.tripnary.ui.usuarios.listusuarios.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.GetListUseCase
import com.app.tripnary.domain.usecases.GetListUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UsuarioListViewModel (private val getListUsuarioUseCase: GetListUsuarioUseCase,) : ViewModel() {

    private val _usuarioListLiveData = MutableLiveData<List<UsuariosModel>>()
    val usuarioModelListLiveData: LiveData<List<UsuariosModel>>
        get() = _usuarioListLiveData


    fun onViewReady() {
        viewModelScope.launch {
            getListUsuarioUseCase.execute()
                .flowOn(Dispatchers.IO )
                .collect { usuarios ->
                    _usuarioListLiveData.value = usuarios
                }
        }
    }
}