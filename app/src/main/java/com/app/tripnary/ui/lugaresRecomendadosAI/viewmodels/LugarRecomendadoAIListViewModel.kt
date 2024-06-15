package com.app.tripnary.ui.lugaresRecomendadosAI.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.usecases.lugaresRecomendadosAI.GetLugaresRecomendadosAIByInteresUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LugarRecomendadoAIListViewModel(private val getLugaresRecomendadosAIByInteresUseCase: GetLugaresRecomendadosAIByInteresUseCase) : ViewModel() {

    private val _lugaresRecomendadosAIListLiveData = MutableLiveData<String>()
    val lugaresRecomendadosAIListLiveData: LiveData<String>
        get() = _lugaresRecomendadosAIListLiveData


    fun onViewReady(lugaresRecomendados: InteresLugaresAIModel,
                    referencePlanViaje: String,
                    tipo: String) {
        viewModelScope.launch {
            try {
                val response = getLugaresRecomendadosAIByInteresUseCase.execute(lugaresRecomendados, referencePlanViaje,tipo)
                Log.d("Live data update ai", response)
                _lugaresRecomendadosAIListLiveData.value = response

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}