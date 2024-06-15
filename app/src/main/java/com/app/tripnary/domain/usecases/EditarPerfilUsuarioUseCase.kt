package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.UsuariosRepository

class EditarPerfilUsuarioUseCase (private val usuariosRepository: UsuariosRepository) {
    suspend fun execute(usuariosModel: UsuariosModel) {
        usuariosRepository.updateUsuarios(usuariosModel)
    }
}