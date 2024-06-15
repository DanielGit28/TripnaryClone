package com.app.tripnary.domain.usecases

import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.repositories.ListaDeseosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListaDeseosUseCase(private val listaDeseosRepository: ListaDeseosRepository) {

    fun execute(referenceUsuario:String?): Flow<List<ListaDeseosModel>> =
        listaDeseosRepository.getAllListaDeseos(referenceUsuario)
            .map { listaDeseos -> listaDeseos.sortedBy { listaDeseos -> listaDeseos.reference } }
}