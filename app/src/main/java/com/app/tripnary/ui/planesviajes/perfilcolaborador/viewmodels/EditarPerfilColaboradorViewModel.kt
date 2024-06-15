package com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.UpdateColaboradorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarPerfilColaboradorViewModel(private val editarColaboradorUseCase: UpdateColaboradorUseCase) : ViewModel() {
    private val _colaboradorLiveData = MutableLiveData<Unit>()

    fun updateColaborador(reference: String, correoElectronico: String, nombre: String, estado: String, idPlanViaje: String, idUsuarioColaborador: String, rol: String){
        val colaboradoresModel =
            ColaboradoresModel (
                reference = reference,
                correoElectronico = correoElectronico,
                nombre = nombre,
                estado = estado,
                idUsuarioColaborador = idUsuarioColaborador,
                idPlanViaje = idPlanViaje,
                rol = rol
            )

        viewModelScope.launch(Dispatchers.IO) {
            editarColaboradorUseCase.execute(colaboradoresModel)
            withContext(Dispatchers.Main) {
                _colaboradorLiveData.value = Unit
            }
        }
    }
}