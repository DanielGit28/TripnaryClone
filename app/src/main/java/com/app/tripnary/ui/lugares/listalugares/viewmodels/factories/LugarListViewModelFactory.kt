package com.app.tripnary.ui.lugares.listalugares.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.lugares.listalugares.viewmodels.LugarListViewModel
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel

@Suppress("UNCHECKED_CAST")
class LugarListViewModelFactory (private val getListLugaresRecomendadosUseCase: GetListLugaresRecomendadosUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LugarListViewModel(
            getListLugaresRecomendadosUseCase = getListLugaresRecomendadosUseCase
        ) as T
}