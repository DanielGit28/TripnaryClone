package com.app.tripnary.ui.lugares.agregarlugarespropios

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.AddLugarPropioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarLugarPropioViewModel  (private val addLugarPropioUseCase: AddLugarPropioUseCase)
    : ViewModel() {

    private val _lugarPropioUpdateLiveData = MutableLiveData<LugarPropioModel?>()
    val lugarPropioAddedLiveData: LiveData<LugarPropioModel?>
        get() = _lugarPropioUpdateLiveData

    fun agregarLugarPropio(LugarPropioModel: LugarPropioModel): LiveData<LugarPropioModel?> {
        val addLugarLiveData = MutableLiveData<LugarPropioModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addPlanLugar = addLugarPropioUseCase.execute(LugarPropioModel)
            withContext(Dispatchers.Main) {
                _lugarPropioUpdateLiveData.value = addPlanLugar
            }
        }
        return addLugarLiveData
    }
}