package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.PlanViajeRepository
import com.app.tripnary.domain.repositories.UsuariosRepository

class AddUsuarioUseCase (private val repository: UsuariosRepository) {

    suspend fun execute(usuariosModel: UsuariosModel) : UsuariosModel {
        return repository.addUsuario(usuariosModel)
    }

}