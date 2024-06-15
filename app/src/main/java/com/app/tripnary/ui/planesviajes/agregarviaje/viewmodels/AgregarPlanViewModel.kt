package com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.domain.usecases.AddTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarPlanViewModel (private val addPlanUseCase: AddPlanViajeUseCase)
    : ViewModel() {

    private val _planLiveData = MutableLiveData<PlanViajeModel?>()
    val planAddedLiveData: LiveData<PlanViajeModel?>
        get() = _planLiveData

    fun addPlan(nombre: String, idUsuario: String, idPais: String, fechaInicio: String, fechaFin: String)
    : LiveData<PlanViajeModel?> {
        val planModel = PlanViajeModel(
            reference = "",
            nombre = nombre,
            imagenPortada = "",
            idUsuario = idUsuario,
            idPromptHotel = "",
            idPais = idPais,
            idInteresRestaurante = "",
            idInteresLugar = "",
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            estado = "Activo"
        )
        val addPlanLiveData = MutableLiveData<PlanViajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addPlanLugar = addPlanUseCase.execute(planModel)
            withContext(Dispatchers.Main) {
                _planLiveData.value = addPlanLugar
            }
        }
        return addPlanLiveData
    }


}