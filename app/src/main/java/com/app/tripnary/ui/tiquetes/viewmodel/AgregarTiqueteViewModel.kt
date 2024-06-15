package com.app.tripnary.ui.tiquetes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.TiquetesModel
import com.app.tripnary.domain.usecases.AddTiqueteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarTiqueteViewModel(private val agregarTiqueteUseCase: AddTiqueteUseCase,) : ViewModel() {

    fun addTiquete(referenceUsuario: String, categoria: String, mensaje: String){
        val tiquete = TiquetesModel(
            idUsuario = referenceUsuario,
            reference = "",
            estado = "Activo",
            categoria = categoria,
            correoElectronico = "",
            fechaDeCreacion = "",
            mensajeAdmin = "",
            mensajeUsuario = mensaje,
            nombreCompleto = ""
        )

        viewModelScope.launch(Dispatchers.IO) {
            agregarTiqueteUseCase.execute(tiquete)
            withContext(Dispatchers.Main) {

            }
        }
    }
}