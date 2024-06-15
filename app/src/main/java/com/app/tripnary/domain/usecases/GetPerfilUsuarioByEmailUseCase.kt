package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.UsuariosRepository
import kotlinx.coroutines.flow.Flow

class GetPerfilUsuarioByEmailUseCase(private val usuariosRepository: UsuariosRepository) {

    suspend fun execute(correoElectronico : String) : List<UsuariosModel> {
        return usuariosRepository.getUserByEmail(correoElectronico)
    }
}