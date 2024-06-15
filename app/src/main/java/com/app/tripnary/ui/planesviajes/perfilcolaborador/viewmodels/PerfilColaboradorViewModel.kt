package com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.GetColaboradoresByReferenceUseCase
import kotlinx.coroutines.launch

class PerfilColaboradorViewModel(private val getColaboradoresByReferenceUseCase: GetColaboradoresByReferenceUseCase) : ViewModel() {
    private val _colaboradorLiveData = MutableLiveData<ColaboradoresModel>()
    val colaboradorLiveData : LiveData<ColaboradoresModel> = _colaboradorLiveData

    fun getColaboradorByReference(reference: String) {
        viewModelScope.launch {
            try {
                val colaborador = getColaboradoresByReferenceUseCase.execute(reference)
                _colaboradorLiveData.value = colaborador
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}