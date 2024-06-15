package com.app.tripnary.ui.lugareshome.adapters.models

class LugaresItemModel (val reference: String, val imagen:String, val nombre: String, val categoriaViaje: String, val puntuacion: String) {
    override fun toString(): String {
        return "LugaresRecomendadosItemModel(imagen='$imagen', nombre='$nombre', categoriaViaje='$categoriaViaje')"
    }
}