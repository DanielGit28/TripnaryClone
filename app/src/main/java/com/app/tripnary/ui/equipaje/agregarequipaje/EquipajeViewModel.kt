package com.app.tripnary.ui.equipaje.agregarequipaje

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.AddEquipajeUseCase
import com.app.tripnary.domain.usecases.DeleteEquipajeUseCase
import com.app.tripnary.domain.usecases.UpdateEquipajeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EquipajeViewModel (private val addEquipajeUseCase: AddEquipajeUseCase,
                         private val updateEquipajeUseCase: UpdateEquipajeUseCase,
                         private val deleteEquipajeUseCase: DeleteEquipajeUseCase)
    : ViewModel() {

    private val _equipajePlanUpdateLiveData = MutableLiveData<EquipajeModel?>()
    val equipajeplanAddedLiveData: LiveData<EquipajeModel?>
        get() = _equipajePlanUpdateLiveData

    fun agregarLugarPlan(equipajeModel: EquipajeModel): LiveData<EquipajeModel?> {
        val addEquipajeLiveData = MutableLiveData<EquipajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val addEquipaje = addEquipajeUseCase.execute(equipajeModel)
            withContext(Dispatchers.Main) {
                _equipajePlanUpdateLiveData.value = addEquipaje
            }
        }
        return addEquipajeLiveData
    }

    private val _equipajeUpdateLiveData = MutableLiveData<EquipajeModel?>()
    val equipajeUpdateLiveData: LiveData<EquipajeModel?>
        get() = _equipajeUpdateLiveData


    fun updateEquipaje(equipajeModel: EquipajeModel): LiveData<EquipajeModel?> {
        val updateEquipajeLiveData = MutableLiveData<EquipajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val updateEquipaje = updateEquipajeUseCase.execute(equipajeModel)
            withContext(Dispatchers.Main) {
                _equipajeUpdateLiveData.value = updateEquipaje
            }
        }
        return updateEquipajeLiveData
    }


    private val _deleteEquipajeLiveData = MutableLiveData<EquipajeModel?>()
    val deleteEquipajeLiveData: LiveData<EquipajeModel?>
        get() = _deleteEquipajeLiveData

    fun deleteEquipaje(reference: String): LiveData<EquipajeModel?> {
        val deleteLiveData = MutableLiveData<EquipajeModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val deleteEquipaje = deleteEquipajeUseCase.execute(reference)
            withContext(Dispatchers.Main) {
                _deleteEquipajeLiveData.value = deleteEquipaje
            }
        }
        return deleteLiveData
    }
}