package com.app.tripnary.ui.lugares.listalugarespropiosperfil.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetListLugaresPropiosPerfilUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosPerfilUseCase
import com.app.tripnary.ui.lugares.listalugarespropiosperfil.viewmodels.ListaLugaresPropiosPerfilViewModel
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels.ListaLugaresRecomendadosPerfilViewModel

@Suppress("UNCHECKED_CAST")
class ListaLugaresPropiosPerfilViewModelFactory (private val getListLugaresPropiosPerfilUseCase: GetListLugaresPropiosPerfilUseCase)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListaLugaresPropiosPerfilViewModel(
            getListLugaresPropiosPerfilUseCase = getListLugaresPropiosPerfilUseCase
        ) as T
}