package com.app.tripnary.data.datasources

import com.app.tripnary.data.database.daos.InteresesGeneralesDao
import com.app.tripnary.data.database.entities.InteresesGeneralesEntity

class DatabaseInteresesGeneralesDataSource (private val interesesGeneralesDao: InteresesGeneralesDao) {

    suspend fun addInteresesGenerales(interesesGeneralesEntity: InteresesGeneralesEntity): Long =
        interesesGeneralesDao.insert(interesesGeneralesEntity)
}