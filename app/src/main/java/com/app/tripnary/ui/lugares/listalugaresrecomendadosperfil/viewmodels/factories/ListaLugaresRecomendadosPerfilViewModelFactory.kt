package com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosPerfilUseCase
import com.app.tripnary.ui.lugares.listalugaresplanes.ListaLugaresPlanesViewModel
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels.ListaLugaresRecomendadosPerfilViewModel

@Suppress("UNCHECKED_CAST")
class ListaLugaresRecomendadosPerfilViewModelFactory (private val getListLugaresRecomendadosPerfilUseCase: GetListLugaresRecomendadosPerfilUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaLugaresRecomendadosPerfilViewModel(
            getListLugaresRecomendadosPerfilUseCase = getListLugaresRecomendadosPerfilUseCase
        ) as T
}