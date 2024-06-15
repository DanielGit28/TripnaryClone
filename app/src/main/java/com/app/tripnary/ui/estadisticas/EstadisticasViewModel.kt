package com.app.tripnary.ui.estadisticas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.DeletePlanViajeUseCase
import com.app.tripnary.domain.usecases.GetEstadisticasUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstadisticasViewModel (private val getEstadisticasUseCase: GetEstadisticasUseCase)
    : ViewModel() {

    private val _estadisticasLiveData = MutableLiveData<EstadisticasModel?>()
    val estadisticasLiveData: LiveData<EstadisticasModel?>
        get() = _estadisticasLiveData

    fun obtenerEstadisticas(reference: String): LiveData<EstadisticasModel?> {
        val estadisticasLiveData = MutableLiveData<EstadisticasModel?>()
        viewModelScope.launch(Dispatchers.IO) {
            val estadisticas = getEstadisticasUseCase.execute(reference)
            withContext(Dispatchers.Main) {
                _estadisticasLiveData.value = estadisticas
            }
        }
        return estadisticasLiveData
    }
}