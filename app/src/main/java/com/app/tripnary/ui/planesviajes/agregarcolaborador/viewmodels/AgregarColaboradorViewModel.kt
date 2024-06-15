package com.app.tripnary.ui.planesviajes.agregarcolaborador.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.AddColaboradorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarColaboradorViewModel(private val agregarColaboradorUseCase: AddColaboradorUseCase) : ViewModel() {
    private val _colaboradorLiveData = MutableLiveData<Unit>()

    fun addColaborador(nombre: String, correoElectronico: String, idPlanViaje: String, rol: String) {
        val colaboradoresModel = ColaboradoresModel(
            reference = "",
            correoElectronico = correoElectronico,
            nombre = nombre,
            idPlanViaje = idPlanViaje,
            rol = rol,
            idUsuarioColaborador = "",
            estado = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            agregarColaboradorUseCase.execute(colaboradoresModel)
            withContext(Dispatchers.Main) {
                _colaboradorLiveData.value = Unit
            }
        }
    }
}