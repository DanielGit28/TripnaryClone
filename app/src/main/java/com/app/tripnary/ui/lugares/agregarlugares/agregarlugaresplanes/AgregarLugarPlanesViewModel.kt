package com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarLugarPlanesViewModel (private val addLugarPlanUseCase: AddLugarPlanUseCase)
    : ViewModel() {

    private val _lugarPlanUpdateLiveData = MutableLiveData<LugarPlanModel?>()
    val lugarplanAddedLiveData: LiveData<LugarPlanModel?>
        get() = _lugarPlanUpdateLiveData

    fun agregarLugarPlan(lugarPlanModel: LugarPlanModel): LiveData<LugarPlanModel?> {
        val addPlanLiveData = MutableLiveData<LugarPlanModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addPlanLugar = addLugarPlanUseCase.execute(lugarPlanModel)
            withContext(Dispatchers.Main) {
                _lugarPlanUpdateLiveData.value = addPlanLugar
            }
        }
        return addPlanLiveData
    }
}