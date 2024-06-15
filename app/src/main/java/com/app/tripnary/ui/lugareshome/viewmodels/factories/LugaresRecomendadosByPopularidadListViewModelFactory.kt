package com.app.tripnary.ui.lugareshome.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByPopularidadUseCase
import com.app.tripnary.ui.lugareshome.viewmodels.LugaresRecomendadosByPopularidadListViewModel

class LugaresRecomendadosByPopularidadListViewModelFactory(private val getLugaresRecomendadosByPopularidadUseCase: GetLugaresRecomendadosByPopularidadUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LugaresRecomendadosByPopularidadListViewModel (
            getLugaresRecomendadosByPopularidadUseCase = getLugaresRecomendadosByPopularidadUseCase
        ) as T

}