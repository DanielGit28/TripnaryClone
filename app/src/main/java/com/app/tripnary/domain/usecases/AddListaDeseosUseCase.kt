package com.app.tripnary.domain.usecases

import android.util.Log
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.repositories.ListaDeseosRepository

class AddListaDeseosUseCase(private val repository: ListaDeseosRepository) {
    suspend fun execute(listaDeseos: ListaDeseosModel): ListaDeseosModel {
        val listaDeseos = repository.addListaDeseos(listaDeseos)
        return listaDeseos
    }
}