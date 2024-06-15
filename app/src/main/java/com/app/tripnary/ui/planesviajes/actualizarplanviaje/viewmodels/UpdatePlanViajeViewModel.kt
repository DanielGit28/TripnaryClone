package com.app.tripnary.ui.planesviajes.actualizarplanviaje.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import com.app.tripnary.domain.usecases.UpdatePlanViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdatePlanViajeViewModel (private val updatePlanViajeUseCase: UpdatePlanViajeUseCase)
    : ViewModel() {

    private val _lugarPlanUpdateLiveData = MutableLiveData<PlanViajeModel?>()
    val lugarplanAddedLiveData: LiveData<PlanViajeModel?>
        get() = _lugarPlanUpdateLiveData

    fun updatePlanViaje(planViajeModel: PlanViajeModel): LiveData<PlanViajeModel?> {

        val updatePlanLiveData = MutableLiveData<PlanViajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val updatePlanViaje = updatePlanViajeUseCase.execute(planViajeModel)
            withContext(Dispatchers.Main) {
                _lugarPlanUpdateLiveData.value = updatePlanViaje
            }
        }
        return updatePlanLiveData
    }
}