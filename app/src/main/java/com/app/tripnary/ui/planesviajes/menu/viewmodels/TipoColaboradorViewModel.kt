package com.app.tripnary.ui.planesviajes.menu.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.GetTipoColaboradorUseCase
import kotlinx.coroutines.launch

class TipoColaboradorViewModel(private val getTipoColaboradorUseCase: GetTipoColaboradorUseCase) : ViewModel() {

    private val _tipoLiveData = MutableLiveData<List<ColaboradoresModel>>()

    val tipoLiveData: LiveData<List<ColaboradoresModel>>
        get() = _tipoLiveData

    fun getTipoColaborador(idPlanViaje: String) {
        try {
            viewModelScope.launch{
                val tipo = getTipoColaboradorUseCase.execute(idPlanViaje)
                _tipoLiveData.value = tipo
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshTipo(newValue: List<ColaboradoresModel>) {
        _tipoLiveData.value = newValue
    }
}