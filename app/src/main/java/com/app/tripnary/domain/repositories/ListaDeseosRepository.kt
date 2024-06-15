package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.ListaDeseosModel
import kotlinx.coroutines.flow.Flow

interface ListaDeseosRepository {
    fun getAllListaDeseos(referenceUsuario:String?): Flow<List<ListaDeseosModel>>

    suspend fun addListaDeseos(listaDeseos: ListaDeseosModel): ListaDeseosModel

    suspend fun deleteListaDeseos(listaDeseos: ListaDeseosModel): ListaDeseosModel
}