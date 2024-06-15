package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.TaskRepository
import com.app.tripnary.domain.repositories.UsuariosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListUsuarioUseCase (private val usuariosRepository: UsuariosRepository) {

    fun execute(): Flow<List<UsuariosModel>> =
        usuariosRepository.getAllUsersLocal()
            .map { usuario -> usuario.sortedBy { usuario -> usuario.reference } }
}