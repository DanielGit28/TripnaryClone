package com.app.tripnary.ui.lugares.actualizarlugares.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateLugarPlanesViewModel (private val updateLugarPlanesUseCase: UpdateLugarPlanesUseCase)
    : ViewModel() {

    private val _lugarPlanUpdateLiveData = MutableLiveData<LugarPlanModel?>()
    val lugarplanAddedLiveData: LiveData<LugarPlanModel?>
        get() = _lugarPlanUpdateLiveData

    fun updateLugarPlan(lugarPlanModel: LugarPlanModel): LiveData<LugarPlanModel?> {
        val addPlanLiveData = MutableLiveData<LugarPlanModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addPlanLugar = updateLugarPlanesUseCase.execute(lugarPlanModel)
            withContext(Dispatchers.Main) {
                _lugarPlanUpdateLiveData.value = addPlanLugar
            }
        }
        return addPlanLiveData
    }
}