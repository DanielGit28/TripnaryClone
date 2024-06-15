package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.InteresesGeneralesModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.InteresesGeneralesRepository
import kotlinx.coroutines.flow.Flow

class AddInteresesGeneralesUseCase(private val interesesGeneralesRepository: InteresesGeneralesRepository) {

    suspend fun execute(interesesGeneralesModel: InteresesGeneralesModel) {
        interesesGeneralesRepository.addInteresesGenerales(interesesGeneralesModel)
    }
}