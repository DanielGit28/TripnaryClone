package com.app.tripnary.ui.lugares.deletelugares

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.DeleteLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteLugaresViewModel (private val deleteLugarPlanUseCase: DeleteLugarPlanUseCase)
    : ViewModel() {

    private val _lugarPlanUpdateLiveData = MutableLiveData<LugarPlanModel?>()
    val lugarplanAddedLiveData: LiveData<LugarPlanModel?>
        get() = _lugarPlanUpdateLiveData

    fun deleteLugarPlan(reference: String): LiveData<LugarPlanModel?> {
        val addPlanLiveData = MutableLiveData<LugarPlanModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val deleteLugar = deleteLugarPlanUseCase.execute(reference)
            withContext(Dispatchers.Main) {
                _lugarPlanUpdateLiveData.value = deleteLugar
            }
        }
        return addPlanLiveData
    }
}