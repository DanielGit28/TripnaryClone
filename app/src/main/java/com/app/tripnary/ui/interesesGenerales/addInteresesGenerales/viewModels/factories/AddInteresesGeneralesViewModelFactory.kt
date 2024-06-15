package com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.domain.usecases.AddInteresesGeneralesUseCase
import com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.viewmodels.AddInteresesGeneralesViewModel

@Suppress("UNCHECKED_CAST")
class AddInteresesGeneralesViewModelFactory(private val useCase: AddInteresesGeneralesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddInteresesGeneralesViewModel(useCase) as T

}