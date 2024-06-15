package com.app.tripnary.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    private val _navigationEvent = MutableLiveData<NavigationScreen>()
    val navigationEvent: LiveData<NavigationScreen>
        get() = _navigationEvent

    fun navigateTo(screen: NavigationScreen) {
        _navigationEvent.value = screen
    }
}

enum class NavigationScreen {
    AddTask,
    TaskList,
    Splash,
    InfoInitial,
    OlvidoContrasenna,
    Main,
    Login,
    Home,
    PaisList,
    RegistroInteresesGenerales,
    ListaDeseos,
    RegistroPlanViaje,
    RegistroUsuario,
    PerfilGeneralViaje,
    ListaPlanesViajes,
    PerfilUsuario,
    EditarPerfilUsuario,
    PerfilLugarRecomendado,
    ListaLugares,
    RegistrarLugar,
    MenuPlanViaje,
    NoInternet,
    MenuLugar,
    EditarLugar,
    ListaColaboradores,
    AgregarColaboradores,
    PerfilColaborador,
    EditarPlanViaje,
    AgregarListaDeseosPlanViaje,
    AgregarDocumentosPlanViaje,
    ListaDocumentosPlanViaje,
    ListaEquipaje,
    MapaLugares,
    BusquedaVuelos,
    AgregarTiquete,
    BusquedaHoteles,
    ListaVuelos,
    ListaHoteles,
    PerfilDocumento,
    RegistroLugaresAI,
    ListaLugaresRecomendadosAI,
    RegistroRestaurantesAI,
    ListaRestaurantesAI,
    Mensaje,
    Canales
}