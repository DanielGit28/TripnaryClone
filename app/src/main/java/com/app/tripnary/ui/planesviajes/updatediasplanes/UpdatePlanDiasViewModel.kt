package com.app.tripnary.ui.planesviajes.updatediasplanes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdatePlanDiasViewModel (private val updatePlanDiasUseCase: UpdatePlanDiasUseCase)
    : ViewModel() {

    private val _diaPlanUpdateLiveData = MutableLiveData<PlanViajeModel?>()
    val planAddedLiveData: LiveData<PlanViajeModel?>
        get() = _diaPlanUpdateLiveData

    fun updateDiasPlan(reference: String, planViajeModel: PlanViajeModel): LiveData<PlanViajeModel?> {
        val updatedPlanLiveData = MutableLiveData<PlanViajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val updatedPlanViaje = updatePlanDiasUseCase.execute(reference, planViajeModel)
            withContext(Dispatchers.Main) {
                _diaPlanUpdateLiveData.value = updatedPlanViaje
            }
        }
        return updatedPlanLiveData
    }
}