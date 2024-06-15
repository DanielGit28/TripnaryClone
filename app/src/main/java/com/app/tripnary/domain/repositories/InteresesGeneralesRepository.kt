package com.app.tripnary.domain.repositories

import com.app.tripnary.domain.models.InteresesGeneralesModel

interface InteresesGeneralesRepository {
    suspend fun addInteresesGenerales(interesesGenerales: InteresesGeneralesModel)
}