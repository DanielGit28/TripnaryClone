package com.app.tripnary.ui.lugareshome.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByInteresesUseCase
import com.app.tripnary.ui.lugareshome.viewmodels.LugaresRecomendadosByInteresesListViewModel

class LugaresRecomendadosByInteresesListViewModelFactory (private val getLugaresRecomendadosByInteresesUseCase: GetLugaresRecomendadosByInteresesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LugaresRecomendadosByInteresesListViewModel(
            getLugaresRecomendadosByInteresesUseCase = getLugaresRecomendadosByInteresesUseCase
        ) as T
}