package com.app.tripnary.ui.planesviajes.menu

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabasePlanesViajesColaboradorDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.data.repositories.PlanesViajesColaboracionRepositoryImpl
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.DeletePlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import com.app.tripnary.domain.usecases.GetTipoColaboradorUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.eliminarviaje.EliminarViajeViewModel
import com.app.tripnary.ui.planesviajes.eliminarviaje.factories.EliminarViajeViewModelFactory
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajesListViewModel
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories.PlanViajeListViewModelFactory
import com.app.tripnary.ui.planesviajes.menu.viewmodels.TipoColaboradorViewModel
import com.app.tripnary.ui.planesviajes.menu.viewmodels.factories.TipoColaboradorViewModelFactory
import com.google.gson.Gson

class MenuPlanViajeFragment : Fragment() {

    private val planDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planDataSource by lazy { DatabasePlanesViajesDataSource(planDao) }
    private val repository by lazy { PlanViajeRepositoryImpl(planDataSource = planDataSource) }

    private val deleteViajeUseCase by lazy { DeletePlanViajeUseCase(repository) }
    private val planColaboradorDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajesColaboradorDao() }
    private val planColaboradorDataSource by lazy { DatabasePlanesViajesColaboradorDataSource(planColaboradorDao) }
    private val repositoryColaborador by lazy { PlanesViajesColaboracionRepositoryImpl(planesViajesColaboradorDataSource = planColaboradorDataSource) }

    private val getTipoColaboradorUseCase by lazy { GetTipoColaboradorUseCase(repositoryColaborador) }

    private val viewModelFactoryColaborador by lazy {
        TipoColaboradorViewModelFactory(
            getTipoColaboradorUseCase
        )
    }
    private val viewModelFactory by lazy {
        EliminarViajeViewModelFactory(
            deleteViajeUseCase
        )
    }

    private lateinit var viewModelEliminar: EliminarViajeViewModel
    private lateinit var viewModelColaborador: TipoColaboradorViewModel


    private lateinit var mainViewModel: MainViewModel
    private lateinit var buttonPerfilViaje: CardView
    private lateinit var buttonColaboradores: CardView
    private lateinit var buttonUpdateViaje: CardView
    private lateinit var buttonDocumentos: CardView
    private lateinit var buttonModalLugaresPropios: CardView
    private lateinit var buttonModalLugaresRecomendados: CardView
    private lateinit var buttonEquipajeViaje: CardView
    private lateinit var buttonLugaresAI: CardView
    private lateinit var buttonRestaurantesAI: CardView
    private lateinit var buttonEliminarViaje: CardView
    private lateinit var buttonCancelDelete: Button
    private lateinit var buttonEliminar: Button
    private lateinit var buttonCancelarModalColaborador: Button
    private lateinit var buttonInternet: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_plan_viaje, container, false)
        viewModelColaborador = ViewModelProvider(this, viewModelFactoryColaborador)[TipoColaboradorViewModel::class.java]
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelEliminar = ViewModelProvider(this, viewModelFactory)[EliminarViajeViewModel::class.java]
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plan = getPlanViaje()
        if (plan != null){
            viewModelColaborador.getTipoColaborador(getPlanViaje()?.reference.toString())
        }
        initViews(view)
    }

    private fun initViews(view: View) {
        with(view) {
            val tipoViaje = getTipoViaje()
            buttonPerfilViaje = findViewById(R.id.card_link_menu_viaje)
            buttonColaboradores = findViewById(R.id.card_link_menu_colaboradores)
            buttonUpdateViaje = findViewById(R.id.card_link_menu_update_plan)
            buttonDocumentos = findViewById(R.id.card_link_menu_documentos)
            buttonEquipajeViaje = findViewById(R.id.card_link_menu_equipaje_lugar)

            buttonLugaresAI = findViewById(R.id.card_link_menu_recomendacionesAI)
            buttonRestaurantesAI = findViewById(R.id.card_link_menu_restaurantesAI)
            buttonEliminarViaje = findViewById(R.id.card_link_menu_eliminar_plan)

            viewModelColaborador.tipoLiveData.observe(viewLifecycleOwner) { tipo ->
                setBotonesComunes()
                if (tipoViaje.equals("Propio")){
                    buttonColaboradores.setOnClickListener{
                        if(hayConexionInternet()) {
                            mainViewModel.navigateTo(NavigationScreen.ListaColaboradores)
                        } else {
                            showInputModalInternet()
                        }

                    }
                    buttonUpdateViaje.setOnClickListener{
                        if(hayConexionInternet()) {
                            mainViewModel.navigateTo(NavigationScreen.EditarPlanViaje)
                        } else {
                            showInputModalInternet()
                        }

                    }


                    buttonEquipajeViaje.setOnClickListener{
                        if(hayConexionInternet()) {
                            mainViewModel.navigateTo(NavigationScreen.ListaEquipaje)
                        } else {
                            showInputModalInternet()
                        }

                    }

                    buttonEliminarViaje.setOnClickListener{
                        if(hayConexionInternet()) {
                            showInputModalDelete()
                        } else {
                            showInputModalInternet()
                        }

                    }
                } else {
                    setBotonesComunesColaboradores()
                    if (tipo.isNotEmpty() && tipo[0].rol.equals("Lector")) {
                        setTipoRol(tipo[0].rol)
                        buttonEquipajeViaje.setOnClickListener{
                            if(hayConexionInternet()) {
                                showInputModalRestriccionColaborador()
                            } else {
                                showInputModalInternet()
                            }

                        }

                    } else {

                        buttonEquipajeViaje.setOnClickListener{
                            if(hayConexionInternet()) {
                                mainViewModel.navigateTo(NavigationScreen.ListaEquipaje)
                            } else {
                                showInputModalInternet()
                            }

                        }

                    }
                }

            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
            }

        }
    }

    private fun setBotonesComunes() {
        buttonPerfilViaje.setOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
        }
        buttonLugaresAI.setOnClickListener{
            if(hayConexionInternet()) {
                mainViewModel.navigateTo(NavigationScreen.RegistroLugaresAI)
            } else {
                showInputModalInternet()
            }

        }
        buttonRestaurantesAI.setOnClickListener{
            if(hayConexionInternet()) {
                mainViewModel.navigateTo(NavigationScreen.RegistroRestaurantesAI)
            } else {
                showInputModalInternet()
            }

        }
        buttonDocumentos.setOnClickListener{
            showDocumentosInputModal()
        }
    }
    private fun setBotonesComunesColaboradores() {
        buttonColaboradores.setOnClickListener{
            showInputModalRestriccionColaborador()
        }
        buttonUpdateViaje.setOnClickListener{
            if(hayConexionInternet()) {
                showInputModalRestriccionColaborador()
            } else {
                showInputModalInternet()
            }

        }
        buttonDocumentos.setOnClickListener{
            showDocumentosInputModal()
        }
        buttonEliminarViaje.setOnClickListener{
            if(hayConexionInternet()) {
                showInputModalRestriccionColaborador()
            } else {
                showInputModalInternet()
            }

        }
    }
    private fun showDocumentosInputModal() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_opciones_documentos_model, null)


        buttonModalLugaresPropios = view.findViewById(R.id.card_link_4)
        buttonModalLugaresRecomendados = view.findViewById(R.id.card_link_5)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        viewModelColaborador.tipoLiveData.observe(viewLifecycleOwner) { tipo ->
            val tipoViaje = getTipoViaje()
            if (tipoViaje.equals("Propio")){
                buttonModalLugaresPropios.setOnClickListener {

                    dialog.dismiss()
                    mainViewModel.navigateTo(NavigationScreen.AgregarDocumentosPlanViaje)
                }

            } else {
                if (tipo.isNotEmpty() && tipo[0].rol.equals("Lector")) {
                    buttonModalLugaresPropios.setOnClickListener {
                        if(hayConexionInternet()) {
                            showInputModalRestriccionColaborador()
                        } else {
                            showInputModalInternet()
                        }

                    }
                } else {
                    buttonModalLugaresPropios.setOnClickListener {

                        dialog.dismiss()
                        if(hayConexionInternet()) {
                            mainViewModel.navigateTo(NavigationScreen.AgregarDocumentosPlanViaje)
                        } else {
                            showInputModalInternet()
                        }

                    }
                }
            }

        }

        buttonModalLugaresRecomendados.setOnClickListener {

            dialog.dismiss()
            mainViewModel.navigateTo(NavigationScreen.ListaDocumentosPlanViaje)
        }
    }
    private fun showInputModalRestriccionColaborador() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_restriccion_colaborador, null)

        buttonCancelarModalColaborador = view.findViewById(R.id.button_cancelar_restriccion)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonCancelarModalColaborador.setOnClickListener {
            dialog.dismiss()
        }

    }
    private fun showInputModalDelete() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_eliminar_viaje, null)

        buttonCancelDelete = view.findViewById(R.id.button_cancel_viaje_modal)
        buttonEliminar = view.findViewById(R.id.button_eliminar_viaje_modal)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonCancelDelete.setOnClickListener {
            dialog.dismiss()
        }

        buttonEliminar.setOnClickListener {
            deleteLugares()
            dialog.dismiss()
        }

    }

    private fun deleteLugares() {

        val planViaje = getPlanViaje()
        if (planViaje != null) {

            val lugarPlanLiveData = viewModelEliminar.deleteLugarPlan(planViaje.reference)

            viewModelEliminar.planDeleteLiveData.observe(viewLifecycleOwner) { deletePlan ->

                if (deletePlan != null) {
                    mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
                }
            }

        }



    }

    private fun getPlanViaje(): PlanViajeModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("planSelected", null)


        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, PlanViajeModel::class.java)
        } else {
            null
        }
    }
    private fun getTipoViaje(): String? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val tipoViaje = sharedPref.getString("tipoViaje", "")
        return tipoViaje
    }

    private fun setTipoRol(tipo: String) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("tipoRol", tipo)
        editor.apply()
    }

    private fun hayConexionInternet(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun showInputModalInternet() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_no_internet, null)

        buttonInternet = view.findViewById(R.id.button_internet)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonInternet.setOnClickListener {
            dialog.dismiss()
        }

    }
}