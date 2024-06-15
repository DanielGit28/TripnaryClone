package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.repositories.ListaDeseosRepository

class DeleteListaDeseosUseCase(private val repository: ListaDeseosRepository) {
    suspend fun execute(listaDeseos: ListaDeseosModel): ListaDeseosModel {
        val listaDeseos = repository.deleteListaDeseos(listaDeseos)
        return listaDeseos
    }
}