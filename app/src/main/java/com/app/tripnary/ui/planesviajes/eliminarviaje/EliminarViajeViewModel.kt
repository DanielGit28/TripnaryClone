package com.app.tripnary.ui.planesviajes.eliminarviaje

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.DeleteLugarPlanUseCase
import com.app.tripnary.domain.usecases.DeletePlanViajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EliminarViajeViewModel (private val deletePlanViajeUseCase: DeletePlanViajeUseCase)
    : ViewModel() {

    private val _planDeleteLiveData = MutableLiveData<PlanViajeModel?>()
    val planDeleteLiveData: LiveData<PlanViajeModel?>
        get() = _planDeleteLiveData

    fun deleteLugarPlan(reference: String): LiveData<PlanViajeModel?> {
        val deleteLiveData = MutableLiveData<PlanViajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val deletePlan = deletePlanViajeUseCase.execute(reference)
            withContext(Dispatchers.Main) {
                _planDeleteLiveData.value = deletePlan
            }
        }
        return deleteLiveData
    }
}