package com.app.tripnary.ui.planesviajes.menu.viewmodels.factories
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.GetTipoColaboradorUseCase
import com.app.tripnary.ui.planesviajes.menu.viewmodels.TipoColaboradorViewModel

class TipoColaboradorViewModelFactory(private val getTipoColaboradorUseCase: GetTipoColaboradorUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TipoColaboradorViewModel(
            getTipoColaboradorUseCase = getTipoColaboradorUseCase
        ) as T
}