package com.app.tripnary.ui.planesviajes.listacolaboradores.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetColaboradoresByViajeUseCase
import com.app.tripnary.ui.lugareshome.viewmodels.LugaresRecomendadosByPopularidadListViewModel
import com.app.tripnary.ui.planesviajes.listacolaboradores.viewmodels.ColaboradoresListViewModel

class ColaboradoresListViewModelFactory(private val getColaboradoresByViajeUseCase: GetColaboradoresByViajeUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ColaboradoresListViewModel (
            getColaboradoresByViajeUseCase = getColaboradoresByViajeUseCase
        ) as T

}