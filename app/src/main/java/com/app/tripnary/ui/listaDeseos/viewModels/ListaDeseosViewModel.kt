package com.app.tripnary.ui.listaDeseos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.usecases.AddListaDeseosUseCase
import com.app.tripnary.domain.usecases.DeleteListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaDeseosViewModel (private val getListaDeseosUseCase: GetListaDeseosUseCase, private val addListaDeseosUseCase: AddListaDeseosUseCase, private val deleteListaDeseosUseCase: DeleteListaDeseosUseCase) : ViewModel() {

    private val _listaDeseosLiveData = MutableLiveData<List<ListaDeseosModel>>()
    private val _listaDeseosUnitLiveData = MutableLiveData<Unit>()

    val listaDeseosLiveData: LiveData<List<ListaDeseosModel>>
        get() = _listaDeseosLiveData


    fun onViewReady(referenceUsuario:String?) {
        viewModelScope.launch {
            getListaDeseosUseCase.execute(referenceUsuario)
                .flowOn(Dispatchers.IO )
                .collect { listaDeseos ->
                    _listaDeseosLiveData.value = listaDeseos
                }
        }
    }

    fun addLugar(referenceUsuario: String?, referenceLugar: String?){
        val listaDeseos = ListaDeseosModel(
            idUsuario = referenceUsuario.toString(),
            idLugar = referenceLugar.toString(),
            descripcion = "",
            direccion = "",
            estado = "Activo",
            imagen = "",
            nombre = "",
            reference = "",
            categoriaViaje = ""
        )

        viewModelScope.launch(Dispatchers.IO) {
            addListaDeseosUseCase.execute(listaDeseos)
            withContext(Dispatchers.Main) {
                _listaDeseosUnitLiveData.value = Unit
            }
        }
    }

    fun deleteLugar(reference: String){
        val listaDeseos = ListaDeseosModel(
            idUsuario = "",
            idLugar = "",
            descripcion = "",
            direccion = "",
            estado = "Activo",
            imagen = "",
            nombre = "",
            reference = reference,
            categoriaViaje = ""
        )

        viewModelScope.launch(Dispatchers.IO) {
            deleteListaDeseosUseCase.execute(listaDeseos)
            withContext(Dispatchers.Main) {
                _listaDeseosUnitLiveData.value = Unit
            }
        }
    }
}