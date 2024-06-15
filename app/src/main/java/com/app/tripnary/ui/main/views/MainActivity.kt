package com.app.tripnary.ui.main.views

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.ui.usuarios.listusuarios.views.PerfilUsuarioFragment
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.ui.documentos.views.AgregarDocumentosPlanViajeFragment
import com.app.tripnary.ui.documentos.views.ListaDocumentosFragment
import com.app.tripnary.ui.documentos.views.PerfilDocumentosFragment
import com.app.tripnary.ui.equipaje.listaequipaje.ListaEquipajeFragment
import com.app.tripnary.ui.general.HomeFragment
import com.app.tripnary.ui.general.InfoInitialFragment
import com.app.tripnary.ui.general.LoginFragment
import com.app.tripnary.ui.general.InternetFragment
import com.app.tripnary.ui.general.MainFragment
import com.app.tripnary.ui.general.OlvidoContrasennaFragment
import com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.views.RegistroInteresesGeneralesFragment
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.general.SplashFragment
import com.app.tripnary.ui.hoteles.BusquedaHotelesFragment
import com.app.tripnary.ui.hoteles.ListaHotelesFragment
import com.app.tripnary.ui.listaDeseos.AgregarListaDeseosPlanViajeFragment
import com.app.tripnary.ui.listaDeseos.ListaDeseosFragment
import com.app.tripnary.ui.lugares.actualizarlugares.UpdateLugaresFragment
import com.app.tripnary.ui.lugares.agregarlugares.AgregarLugarFragment
import com.app.tripnary.ui.lugares.listalugares.ListaLugaresRecomendadosFragment
import com.app.tripnary.ui.lugares.mapas.MapsLugaresFragment
import com.app.tripnary.ui.lugares.menu.MenuLugarFragment
import com.app.tripnary.ui.lugaresRecomendadosAI.ListaLugaresRecomendadosAIFragment
import com.app.tripnary.ui.lugaresRecomendadosAI.RegistroPromptFragment
import com.app.tripnary.ui.lugaresrecomendados.views.PerfilLugarFragment
import com.app.tripnary.ui.mensajes.CanalesFragment
import com.app.tripnary.ui.mensajes.MensajeFragment
import com.app.tripnary.ui.paises.ListaPaisesFragment
import com.app.tripnary.ui.planesviajes.actualizarplanviaje.UpdatePlanViajeFragment
import com.app.tripnary.ui.planesviajes.agregarcolaborador.AgregarColaboradorFragment
import com.app.tripnary.ui.planesviajes.agregarviaje.views.RegistrarPlanViajeFragment
import com.app.tripnary.ui.planesviajes.listacolaboradores.ColaboradoresListFragment
import com.app.tripnary.ui.planesviajes.listaviajes.PlanViajeListFragment
import com.app.tripnary.ui.planesviajes.menu.MenuPlanViajeFragment
import com.app.tripnary.ui.planesviajes.perfilcolaborador.views.PerfilColaboradorFragment
import com.app.tripnary.ui.planesviajes.perfilplanviaje.PerfilGeneralViajeFragment
import com.app.tripnary.ui.restaurantesRecomendadosAI.ListaRestaurantesRecomendadosAIFragment
import com.app.tripnary.ui.restaurantesRecomendadosAI.RegistroRestaurantesAIFragment
import com.app.tripnary.ui.tasks.TaskListFragment
import com.app.tripnary.ui.tasks.addtasks.views.AddTaskFragment
import com.app.tripnary.ui.tiquetes.AgregarTiquetesFragment
import com.app.tripnary.ui.tiquetes.viewmodel.AgregarTiqueteViewModel
import com.app.tripnary.ui.usuarios.editarperfilusuario.views.EditarPerfilUsuarioFragment
import com.app.tripnary.ui.usuarios.registrousuario.views.RegistroUsuarioFragment
import com.app.tripnary.ui.vuelos.BusquedaVuelosFragment
import com.app.tripnary.ui.vuelos.ListaVuelosFragment

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentListener, ConnectivityListener {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            //navigateToFragment(TaskListFragment(), true)
            navigateToFragment(SplashFragment(), true)
        }
        connectivityReceiver.setListener(this)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        observe()
    }

    private fun observe() {
        viewModel.navigationEvent.observe(this) { event ->
            when (event) {
                NavigationScreen.AddTask -> navigateToFragment(AddTaskFragment())
                NavigationScreen.TaskList -> navigateToFragment(TaskListFragment())
                NavigationScreen.Splash -> navigateToFragment(SplashFragment())
                NavigationScreen.InfoInitial -> navigateToFragment(InfoInitialFragment())
                NavigationScreen.Main -> navigateToFragment(MainFragment())
                NavigationScreen.Home -> navigateToFragment(HomeFragment())
                NavigationScreen.Login -> navigateToFragment(LoginFragment())
                NavigationScreen.OlvidoContrasenna -> navigateToFragment(OlvidoContrasennaFragment())
                NavigationScreen.RegistroInteresesGenerales -> navigateToFragment(
                    RegistroInteresesGeneralesFragment()
                )
                NavigationScreen.PaisList -> navigateToFragment(ListaPaisesFragment())
                NavigationScreen.ListaDeseos -> navigateToFragment(ListaDeseosFragment())
                NavigationScreen.BusquedaVuelos -> navigateToFragment(BusquedaVuelosFragment())
                NavigationScreen.AgregarTiquete -> navigateToFragment(AgregarTiquetesFragment())
                NavigationScreen.BusquedaHoteles -> navigateToFragment(BusquedaHotelesFragment())
                NavigationScreen.ListaVuelos -> navigateToFragment(ListaVuelosFragment())
                NavigationScreen.ListaHoteles -> navigateToFragment(ListaHotelesFragment())
                NavigationScreen.RegistroPlanViaje -> navigateToFragment(RegistrarPlanViajeFragment())
                NavigationScreen.RegistroUsuario -> navigateToFragment(RegistroUsuarioFragment())
                NavigationScreen.PerfilGeneralViaje -> navigateToFragment(PerfilGeneralViajeFragment())
                NavigationScreen.ListaPlanesViajes -> navigateToFragment(PlanViajeListFragment())
                NavigationScreen.PerfilUsuario -> navigateToFragment(PerfilUsuarioFragment())
                NavigationScreen.EditarPerfilUsuario -> navigateToFragment(EditarPerfilUsuarioFragment())
                NavigationScreen.PerfilLugarRecomendado -> navigateToFragment(PerfilLugarFragment())
                NavigationScreen.ListaLugares -> navigateToFragment(ListaLugaresRecomendadosFragment())
                NavigationScreen.RegistrarLugar -> navigateToFragment(AgregarLugarFragment())
                NavigationScreen.MenuPlanViaje -> navigateToFragment(MenuPlanViajeFragment())
                NavigationScreen.MenuLugar -> navigateToFragment(MenuLugarFragment())
                NavigationScreen.EditarLugar -> navigateToFragment(UpdateLugaresFragment())
                NavigationScreen.ListaColaboradores -> navigateToFragment(ColaboradoresListFragment())
                NavigationScreen.AgregarColaboradores -> navigateToFragment(AgregarColaboradorFragment())
                NavigationScreen.PerfilColaborador -> navigateToFragment(PerfilColaboradorFragment())
                NavigationScreen.NoInternet -> navigateToFragment(InternetFragment())
                NavigationScreen.EditarPlanViaje -> navigateToFragment(UpdatePlanViajeFragment())
                NavigationScreen.AgregarListaDeseosPlanViaje -> navigateToFragment(AgregarListaDeseosPlanViajeFragment())
                NavigationScreen.AgregarDocumentosPlanViaje -> navigateToFragment(AgregarDocumentosPlanViajeFragment())
                NavigationScreen.ListaDocumentosPlanViaje -> navigateToFragment(ListaDocumentosFragment())
                NavigationScreen.ListaEquipaje -> navigateToFragment(ListaEquipajeFragment())
                NavigationScreen.MapaLugares -> navigateToFragment(MapsLugaresFragment())
                NavigationScreen.PerfilDocumento -> navigateToFragment(PerfilDocumentosFragment())
                NavigationScreen.RegistroLugaresAI -> navigateToFragment(RegistroPromptFragment())
                NavigationScreen.ListaLugaresRecomendadosAI -> navigateToFragment(
                    ListaLugaresRecomendadosAIFragment()
                )
                NavigationScreen.RegistroRestaurantesAI -> navigateToFragment(RegistroRestaurantesAIFragment())
                NavigationScreen.ListaRestaurantesAI -> navigateToFragment(
                    ListaRestaurantesRecomendadosAIFragment()
                )
                NavigationScreen.Canales -> navigateToFragment(CanalesFragment())
                NavigationScreen.Mensaje -> navigateToFragment(MensajeFragment())
                else -> Unit
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment, isInitialFragment: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
        if (!isInitialFragment) {
            transaction.addToBackStack(fragment.javaClass.name)
        }
        transaction
            .commit()
    }

    override fun openCloseNavigationDrawer() {
        // Getting drawer layout by its id from the fragment's view
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout_home)

        // Comparing
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Closing drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Opening drawer
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private val connectivityReceiver = ConnectivityReceiver()


    override fun onInternetStatusChanged(fragment: Fragment?, isConnected: Boolean) {
        if (fragment is PerfilGeneralViajeFragment) {
            fragment.updateInternetTitleVisibility(isConnected)
        } else if (fragment is PlanViajeListFragment) {
            fragment.updateInternetTitleVisibility(isConnected)
        } else if (fragment is HomeFragment) {
            fragment.updateInternetTitleVisibility(isConnected)
        }else if (fragment is ListaDeseosFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if (fragment is BusquedaVuelosFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if (fragment is ListaVuelosFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if(fragment is ListaHotelesFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if(fragment is AgregarTiquetesFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if(fragment is BusquedaHotelesFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }else if(fragment is AgregarListaDeseosPlanViajeFragment){
            fragment.updateInternetTitleVisibility(isConnected)
        }
    }

    override fun onResume() {
        super.onResume()
        // Registra el BroadcastReceiver
        registerReceiver( connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        // Desregistra el BroadcastReceiver
        unregisterReceiver(connectivityReceiver)
    }
}