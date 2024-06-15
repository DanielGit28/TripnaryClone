package com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.InteresesGeneralesModel
import com.app.tripnary.domain.usecases.AddInteresesGeneralesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddInteresesGeneralesViewModel(private val addInteresesGeneralesUseCase: AddInteresesGeneralesUseCase) : ViewModel() {
    private val _displayErrorMessageLiveData = MutableLiveData<Int>()
    private val _noteAddedLiveData = MutableLiveData<Unit>()
    fun addInteresesGenerales(categoriaViaje: String, destinoPreferido: String, estado: String, temporadaPreferida: String) {
        val interesesGeneralesModel = InteresesGeneralesModel(
            reference = "",
            categoriaViaje = categoriaViaje,
            destinoPreferido = destinoPreferido,
            estado = estado,
            temporadaPreferida = temporadaPreferida
        )
        viewModelScope.launch(Dispatchers.IO) {
            addInteresesGeneralesUseCase.execute(interesesGeneralesModel)
            withContext(Dispatchers.Main) {
                _noteAddedLiveData.value = Unit
            }
        }
    }
}