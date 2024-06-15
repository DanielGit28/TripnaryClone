package com.app.tripnary.data.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.tripnary.data.database.daos.ColaboradoresDao
import com.app.tripnary.data.database.daos.DocumentosDao
import com.app.tripnary.data.database.daos.InteresesGeneralesDao
import com.app.tripnary.data.database.daos.LugarPlanesDao
import com.app.tripnary.data.database.daos.LugarPropioDao
import com.app.tripnary.data.database.daos.LugaresRecomendadosAIDao
import com.app.tripnary.data.database.daos.LugaresRecomendadosDao
import com.app.tripnary.data.database.daos.PlanDiasDao
import com.app.tripnary.data.database.daos.PlanViajeDao
import com.app.tripnary.data.database.daos.PlanesViajesColaboradorDao
import com.app.tripnary.data.database.daos.TaskDao
import com.app.tripnary.data.database.daos.UsuariosDao
import com.app.tripnary.data.database.entities.ColaboradoresEntity
import com.app.tripnary.data.database.entities.DocumentosEntity
import com.app.tripnary.data.database.entities.InteresesGeneralesEntity
import com.app.tripnary.data.database.entities.LugarPlanEntity
import com.app.tripnary.data.database.entities.LugarPropioEntity
import com.app.tripnary.data.database.entities.InteresesLugaresAIEntity
import com.app.tripnary.data.database.entities.LugaresRecomendadosEntity
import com.app.tripnary.data.database.entities.PlanDiasEntity
import com.app.tripnary.data.database.entities.PlanViajeEntity
import com.app.tripnary.data.database.entities.PlanesViajesColaboradorEntity
import com.app.tripnary.data.database.entities.TaskEntity
import com.app.tripnary.data.database.entities.UsuariosEntity

@Database(entities = [
    TaskEntity::class,
    InteresesGeneralesEntity::class,
    UsuariosEntity::class,
    PlanViajeEntity::class,
    PlanDiasEntity::class,
    LugaresRecomendadosEntity::class,
    InteresesLugaresAIEntity::class,
    LugarPlanEntity::class,
    LugarPropioEntity::class,
    ColaboradoresEntity::class,
    DocumentosEntity::class,
    PlanesViajesColaboradorEntity::class],
    version = 1)
abstract class AppDatabase  : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
    abstract fun getInteresesGeneralesDao(): InteresesGeneralesDao
    abstract fun getUsuariosDao(): UsuariosDao

    abstract fun getPlanesViajeDao(): PlanViajeDao

    abstract fun getPlanDiasDao(): PlanDiasDao

    abstract fun getLugaresRecomendadosDao(): LugaresRecomendadosDao

    abstract fun getLugaresRecomendadosAIDao(): LugaresRecomendadosAIDao

    abstract fun getLugaresPlanes(): LugarPlanesDao

    abstract fun getLugaresPropios(): LugarPropioDao

    abstract fun getColaboradoresDao(): ColaboradoresDao

    abstract fun getDocumentosDao(): DocumentosDao

    abstract fun getPlanesViajesColaboradorDao(): PlanesViajesColaboradorDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}