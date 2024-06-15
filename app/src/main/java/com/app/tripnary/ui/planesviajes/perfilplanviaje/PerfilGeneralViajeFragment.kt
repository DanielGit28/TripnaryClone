package com.app.tripnary.ui.planesviajes.perfilplanviaje

import android.app.AlertDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.classes.AdaptiveSpacingItemDecoration
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabaseLugaresPropiosDataSource
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosDataSource
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.LugarPropioRepositoryImpl
import com.app.tripnary.data.repositories.LugaresRecomendadosRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.domain.usecases.GetListLugarPlanByPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListLugaresPropiosPerfilUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosPerfilUseCase
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import com.app.tripnary.ui.lugares.listalugaresplanes.ListaLugaresPlanesViewModel
import com.app.tripnary.ui.lugares.listalugaresplanes.factories.ListaLugaresPlanesViewModelFactory
import com.app.tripnary.ui.lugares.listalugarespropiosperfil.adapters.LugarPropioListPerfilAdapter
import com.app.tripnary.ui.lugares.listalugarespropiosperfil.viewmodels.ListaLugaresPropiosPerfilViewModel
import com.app.tripnary.ui.lugares.listalugarespropiosperfil.viewmodels.factories.ListaLugaresPropiosPerfilViewModelFactory
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.adapters.LugarRecomendadoListPerfilAdapter
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels.ListaLugaresRecomendadosPerfilViewModel
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.viewmodels.factories.ListaLugaresRecomendadosPerfilViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listadias.adapters.PlanDiasListAdapter
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListViewModelFactory
import com.app.tripnary.ui.planesviajes.updatediasplanes.UpdatePlanDiasViewModel
import com.app.tripnary.ui.planesviajes.updatediasplanes.factories.UpdatePlanDiasViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class PerfilGeneralViajeFragment : Fragment(), PlanDiasListAdapter.DiasClickListener,
    LugarRecomendadoListPerfilAdapter.LugarRecomendadoClickListener, ConnectivityListener,
LugarPropioListPerfilAdapter.LugarPropioClickListener {

    private val diaDao by lazy { AppDatabase.getInstance(requireContext()).getPlanDiasDao() }
    private val planDiasDataSource by lazy { DatabasePlanDiasDataSource(diaDao) }
    private val repository by lazy { PlanDiasRepositoryImpl(planDiasDataSource = planDiasDataSource) }
    private val getDiasListUseCase by lazy { GetListDiasPlanesUseCase(repository) }


    private val planDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planDataSource by lazy { DatabasePlanesViajesDataSource(planDao) }
    private val repositoryPlan by lazy { PlanViajeRepositoryImpl(planDataSource = planDataSource) }
    private val updatePlanDiasUseCase by lazy { UpdatePlanDiasUseCase(repositoryPlan) }

    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }
    private val getListLugarPlanByPlanViajeUseCase by lazy {
        GetListLugarPlanByPlanViajeUseCase(
            repositoryLugar
        )
    }

    private val lugarRecomendacionDao by lazy {
        AppDatabase.getInstance(requireContext()).getLugaresRecomendadosDao()
    }
    private val lugaresRecomendadosDataSource by lazy {
        DatabaseLugaresRecomendadosDataSource(
            lugarRecomendacionDao
        )
    }
    private val repositoryLugarRecomendado by lazy {
        LugaresRecomendadosRepositoryImpl(
            lugaresRecomendadosDataSource = lugaresRecomendadosDataSource
        )
    }
    private val getListRecomendadosUseCase by lazy {
        GetListLugaresRecomendadosPerfilUseCase(
            repositoryLugarRecomendado
        )
    }


    private val lugarPropioDao by lazy {
        AppDatabase.getInstance(requireContext()).getLugaresPropios()
    }
    private val databaseLugaresPropiosDataSource by lazy {
        DatabaseLugaresPropiosDataSource(
            lugarPropioDao
        )
    }
    private val repositoryLugaresPropios by lazy {
        LugarPropioRepositoryImpl(
            databaseLugaresPropiosDataSource = databaseLugaresPropiosDataSource
        )
    }
    private val getListPropiosUseCase by lazy {
        GetListLugaresPropiosPerfilUseCase(
            repositoryLugaresPropios
        )
    }


    private val viewModelFactoryUpdateDias by lazy {
        UpdatePlanDiasViewModelFactory(
            updatePlanDiasUseCase
        )
    }

    private val viewModelFactoryListaLugaresPlanes by lazy {
        ListaLugaresPlanesViewModelFactory(
            getListLugarPlanByPlanViajeUseCase
        )
    }

    private val viewModelFactoryListaLugaresRecomendados by lazy {
        ListaLugaresRecomendadosPerfilViewModelFactory(
            getListRecomendadosUseCase
        )
    }

    private val viewModelFactoryListaLugaresPropios by lazy {
        ListaLugaresPropiosPerfilViewModelFactory(
            getListPropiosUseCase
        )
    }

    private var planViaje: PlanViajeModel? = null
    private var updatePlanViaje: PlanViajeModel? = null



    private val viewModelFactory by lazy {
        PlanDiasListViewModelFactory(
            getDiasListUseCase
        )
    }

    private lateinit var viewModel: PlanDiasListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var updatePlanDiasViewModel: UpdatePlanDiasViewModel
    private lateinit var viewModelLugaresPlanes: ListaLugaresPlanesViewModel
    private lateinit var viewModelLugaresRecomendados: ListaLugaresRecomendadosPerfilViewModel
    private lateinit var viewModelLugaresPropios: ListaLugaresPropiosPerfilViewModel


    private lateinit var diasRecyclerView: RecyclerView
    private lateinit var lugaresRecomendadosRecyclerView: RecyclerView
    private lateinit var lugaresPropiosRecyclerView: RecyclerView
    private lateinit var titleToolbar: TextView
    private lateinit var cantidadDias: TextView
    private lateinit var buttonAgregarDia: CardView
    private lateinit var buttonEliminarDia: CardView
    private lateinit var buttonModalLugaresPropios: CardView
    private lateinit var buttonModalLugaresRecomendados: CardView
    private lateinit var placeHolder: ImageView
    private lateinit var titleInternet: TextView
    private lateinit var buttonCancelarModalColaborador: Button
    private lateinit var buttonInternet: Button

    var adapter = PlanDiasListAdapter(this, this)

    var adapterLugaresRecomendados = LugarRecomendadoListPerfilAdapter(this, this)
    var adapterLugaresPropios = LugarPropioListPerfilAdapter(this, this)

    private var lugaresPlanes: List<LugarPlanModel>? = null
    private lateinit var diasPlan: List<PlanDiasModel>
    private lateinit var lugaresRecomendados: List<LugaresRecomendadosModel>
    private lateinit var lugaresPropios: List<LugarPropioModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_general_viaje, container, false)
        getPlanViaje()
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlanDiasListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelLugaresRecomendados = ViewModelProvider(
            this,
            viewModelFactoryListaLugaresRecomendados
        )[ListaLugaresRecomendadosPerfilViewModel::class.java]
        viewModelLugaresPropios = ViewModelProvider(
            this,
            viewModelFactoryListaLugaresPropios
        )[ListaLugaresPropiosPerfilViewModel::class.java]
        viewModelLugaresPlanes = ViewModelProvider(
            this,
            viewModelFactoryListaLugaresPlanes
        )[ListaLugaresPlanesViewModel::class.java]
        updatePlanDiasViewModel =
            ViewModelProvider(this, viewModelFactoryUpdateDias)[UpdatePlanDiasViewModel::class.java]

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        observe()


        return view
    }

    private fun initViews(view: View) {
        with(view) {
            diasRecyclerView = findViewById(R.id.recycler_view_plan_dias)
            diasRecyclerView.adapter = adapter

            diasRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            diasRecyclerView.addItemDecoration(
                AdaptiveSpacingItemDecoration(
                    resources.getDimension(
                        com.intuit.sdp.R.dimen._10sdp
                    ).roundToInt(), false
                )
            )
            titleInternet = findViewById(R.id.title_internet_perfil)
            //recomendados
            cleanLugarPropio()
            lugaresRecomendadosRecyclerView = findViewById(R.id.recycler_view_lugares_perfil)
            lugaresRecomendadosRecyclerView.adapter = adapterLugaresRecomendados

            lugaresRecomendadosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            //propios
            lugaresPropiosRecyclerView = findViewById(R.id.recycler_view_lugares_propios_perfil)
            lugaresPropiosRecyclerView.adapter = adapterLugaresPropios

            lugaresPropiosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            titleToolbar = findViewById(R.id.text_title_perfil_plan)
            cantidadDias = findViewById(R.id.text_quantity_dias)
            buttonAgregarDia = findViewById(R.id.card_plus_icon_dia)
            buttonEliminarDia = findViewById(R.id.card_minus_icon_dia)
            planViaje = getPlanViaje()
            placeHolder = findViewById(R.id.placeholder_perfil_viaje)
            Picasso.get()
                .load(planViaje?.imagenPortada)
                .into(placeHolder)

            titleToolbar.text = planViaje?.nombre
            buttonAgregarDia.setOnClickListener {

                if (hayConexionInternet()) {
                    if(getTipoRol().equals("Lector")) {
                        showInputModalRestriccionColaborador()
                    } else {
                        val updatedPlanLiveData =
                            planViaje?.let { it1 ->
                                updatePlanDiasViewModel.updateDiasPlan(
                                    "AgregarDia",
                                    it1
                                )
                            }
                        updatePlanDiasViewModel.planAddedLiveData.observe(viewLifecycleOwner) { updatedPlan ->

                            if (updatedPlan != null) {
                                planViaje = updatedPlan
                                updatePerfil()
                                updatePlanViaje(updatedPlan)
                            }
                        }
                    }
                } else {
                    showInputModalInternet()
                }



            }

            buttonEliminarDia.setOnClickListener {

                if (hayConexionInternet()) {
                    if(getTipoRol().equals("Lector")) {
                        showInputModalRestriccionColaborador()
                    } else {
                        val updatedPlanLiveData =
                            planViaje?.let { it1 ->
                                updatePlanDiasViewModel.updateDiasPlan(
                                    "EliminarDia",
                                    it1
                                )
                            }
                        updatePlanDiasViewModel.planAddedLiveData.observe(viewLifecycleOwner) { updatedPlan ->

                            if (updatedPlan != null) {
                                planViaje = updatedPlan
                                updatePerfil()
                                updatePlanViaje(updatedPlan)
                            }
                        }
                    }
                } else {
                    showInputModalInternet()
                }




            }


            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }


        }
    }

    private fun observe() {
        viewModel.diasListLiveData.observe(viewLifecycleOwner) { list ->
            diasPlan = validateElements(list)
            cantidadDias.text = diasPlan.size.toString()

            if (diasPlan.isNotEmpty()) {
                adapter.setData(diasPlan)
                if (list.isNotEmpty()) {
                    observeLugaresPlanes()
                }
            }

            if (list.isEmpty()) {
                diasRecyclerView.visibility = View.VISIBLE
                diasRecyclerView.visibility = View.GONE
            } else {
                diasRecyclerView.visibility = View.GONE
                diasRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun updatePerfil() {
        viewModel.onViewReady()
        observe()
    }

    private fun observeLugaresRecomendados() {

        lugaresPlanes?.let { lugares ->
            viewModelLugaresRecomendados.lugaresRecomendadosListLiveData.observe(viewLifecycleOwner) { list ->
                lugaresRecomendados = list

                if(diasPlan.isNotEmpty()) {
                    val filterLugares = validateLugaresDia(lugares, diasPlan[0].reference, list)

                    val filterPlanes = filterLugaresPlanesByDiaAndLugarRecomendado(
                        diasPlan[0].reference,
                        lugaresPlanes!!
                    )
                    adapterLugaresRecomendados.setData(filterLugares)
                    adapterLugaresRecomendados.setDataPlanes(filterPlanes)
                }

                if (list.isEmpty()) {
                    lugaresRecomendadosRecyclerView.visibility = View.VISIBLE
                    lugaresRecomendadosRecyclerView.visibility = View.GONE
                } else {
                    lugaresRecomendadosRecyclerView.visibility = View.GONE
                    lugaresRecomendadosRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeLugaresPropios() {

        lugaresPlanes?.let { lugares ->
            viewModelLugaresPropios.lugaresPropiosListLiveData.observe(viewLifecycleOwner) { list ->
                lugaresPropios = list

                if (list.isNotEmpty()) {

                    if(diasPlan.isNotEmpty()) {
                        val filterLugares = validateLugaresPropiosDia(lugares, diasPlan[0].reference, list)

                        val filterPlanes = filterLugaresPlanesByDiaAndLugarPropio(
                            diasPlan[0].reference,
                            lugaresPlanes!!
                        )
                        adapterLugaresPropios.setData(filterLugares)
                        adapterLugaresPropios.setDataPlanes(filterPlanes)
                    }


                }

                if (list.isEmpty()) {
                    lugaresPropiosRecyclerView.visibility = View.VISIBLE
                    lugaresPropiosRecyclerView.visibility = View.GONE
                } else {
                    lugaresPropiosRecyclerView.visibility = View.GONE
                    lugaresPropiosRecyclerView.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun updatePlanViaje(updatePlan: PlanViajeModel) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val planJson = gson.toJson(updatePlan)

        editor.putString("planSelected", planJson)
        editor.apply()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewReady()
        getPlanViaje()?.let { viewModelLugaresPlanes.onViewReady(it.reference) }
        getPlanViaje()?.let { viewModelLugaresRecomendados.onViewReady(it.reference) }
        getPlanViaje()?.let { viewModelLugaresPropios.onViewReady(it.reference) }
    }


    override fun onDiasClickListener(dia: PlanDiasModel, position: Int) {

        if(lugaresPlanes?.isNotEmpty() == true) {
            val filterLugares =
                lugaresPlanes?.let { validateLugaresDia(it, dia.reference, lugaresRecomendados) }

            val filterPlanes = filterLugaresPlanesByDiaAndLugarRecomendado(
                dia.reference,
                lugaresPlanes!!
            )


            if (filterLugares != null) {
                adapterLugaresRecomendados.setData(filterLugares)
                adapterLugaresRecomendados.setDataPlanes(filterPlanes)
            }

            val filterLugaresPropios =
                lugaresPlanes?.let { validateLugaresPropiosDia(it, dia.reference, lugaresPropios) }

            val filterPlanesPropios = filterLugaresPlanesByDiaAndLugarPropio(
                dia.reference,
                lugaresPlanes!!
            )

            if (filterLugaresPropios != null) {
                adapterLugaresPropios.setData(filterLugaresPropios)
                adapterLugaresPropios.setDataPlanes(filterPlanesPropios)
            }
        }


        agregarDiaSeleccionado(dia.reference)
        agregarLugar()

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


    private fun validateElements(list: List<PlanDiasModel>): List<PlanDiasModel> {
        val planViaje = getPlanViaje()

        val validElements = mutableListOf<PlanDiasModel>()

        for (element in list) {
            if (planViaje != null) {
                if (element.idPlanViaje == planViaje.reference) {
                    validElements.add(element)
                }
            }
        }

        return validElements.sortedBy { it.dia }
    }

    fun agregarLugar() {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("paisPlanViaje", planViaje?.idPais.toString())
        editor.apply()

        if (hayConexionInternet()) {
            if (getTipoViaje().equals("Propio")) {
                showInputModal()
            } else {
                if(getTipoRol().equals("Lector")) {
                    showInputModalRestriccionColaborador()
                } else {
                    showInputModal()
                }
            }

        } else {
            showInputModalInternet()
        }


    }

    fun agregarDiaSeleccionado(dia: String) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("diaSeleccionadoPerfil", dia)
        editor.apply()
    }

    fun agregarLugarMaps(nombre: String, latitud: String, longitud: String, imagen: String) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("nombreLugarMap", nombre)
        editor.putString("latitudLugarMap", latitud)
        editor.putString("longitudLugarMap", longitud)
        editor.putString("imagenLugarMap", imagen)
        editor.apply()
    }

    private fun showInputModal() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_opciones_lugar_model, null)


        buttonModalLugaresPropios = view.findViewById(R.id.card_link_4)
        buttonModalLugaresRecomendados = view.findViewById(R.id.card_link_5)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        buttonModalLugaresPropios.setOnClickListener {

            dialog.dismiss()
            mainViewModel.navigateTo(NavigationScreen.RegistrarLugar)
        }

        buttonModalLugaresRecomendados.setOnClickListener {

            dialog.dismiss()
            mainViewModel.navigateTo(NavigationScreen.ListaLugares)
        }
    }

    private fun observeLugaresPlanes() {
        viewModelLugaresPlanes.lugaresPlanesListLiveData.observe(viewLifecycleOwner) { list ->


            lugaresPlanes = list

            if (list.isNotEmpty()) {
                observeLugaresRecomendados()
                observeLugaresPropios()
            }

        }
    }


    private fun validateLugaresDia(
        list: List<LugarPlanModel>,
        idDia: String,
        lugaresRecomendados: List<LugaresRecomendadosModel>
    ): List<LugaresRecomendadosModel> {
        val validElements = mutableListOf<LugarPlanModel>()

        for (element in list) {
            if (element.idDia == idDia) {
                validElements.add(element)
            }
        }

        val validLugares = mutableListOf<LugaresRecomendadosModel>()

        for (element in validElements) {
            element.idLugarRecomendado?.let { lugarRecomendadoId ->
                val lugarRecomendado =
                    lugaresRecomendados.find { lugar -> lugar.reference == lugarRecomendadoId }
                lugarRecomendado?.let { validLugares.add(it) }
            }
        }

        return validLugares
    }

    private fun validateLugaresPropiosDia(
        list: List<LugarPlanModel>,
        idDia: String,
        lugaresPropios: List<LugarPropioModel>
    ): List<LugarPropioModel> {
        val validElements = mutableListOf<LugarPlanModel>()

        for (element in list) {
            if (element.idDia == idDia) {
                validElements.add(element)
            }
        }

        val validLugares = mutableListOf<LugarPropioModel>()

        for (element in validElements) {
            element.idLugarPropio?.let { lugarPropioId ->
                val lugarPropio = lugaresPropios.find { lugar -> lugar.reference == lugarPropioId }
                lugarPropio?.let { validLugares.add(it) }
            }
        }
        return validLugares
    }


    override fun onLugarRecomendadoClickListener(
        lugar: LugaresRecomendadosModel,
        lugarPlanModel: LugarPlanModel,
        position: Int
    ) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val lugarJson = gson.toJson(lugar)
        val planJson = gson.toJson(lugarPlanModel)

        editor.putString("lugarRecomendadoSelected", lugarJson)
        editor.putString("planLugarSelected", planJson)
        editor.apply()

        agregarLugarMaps(lugar.nombre, lugar.latitud, lugar.longitud, lugar.imagen)

        if (hayConexionInternet()) {
            if (getTipoViaje().equals("Propio")) {
                mainViewModel.navigateTo(NavigationScreen.MenuLugar)
            } else {
                if(getTipoRol().equals("Lector")) {
                    showInputModalRestriccionColaborador()
                } else {
                    mainViewModel.navigateTo(NavigationScreen.MenuLugar)
                }
            }

        } else {
            showInputModalInternet()
        }


    }

    fun filterLugaresPlanesByDiaAndLugarRecomendado(
        dia: String,
        lugaresPlanes: List<LugarPlanModel>
    ): List<LugarPlanModel> {
        return lugaresPlanes.filter { lugarPlan ->
            lugarPlan.idLugarRecomendado != "null" && lugarPlan.idDia == dia
        }
    }

    fun filterLugaresPlanesByDiaAndLugarPropio(
        dia: String,
        lugaresPlanes: List<LugarPlanModel>
    ): List<LugarPlanModel> {
        return lugaresPlanes.filter { lugarPlan ->
            lugarPlan.idLugarPropio != "null" && lugarPlan.idDia == dia
        }
    }

    override fun onLugarClickListener(
        lugar: LugarPropioModel,
        lugarPlanModel: LugarPlanModel,
        position: Int
    ) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val lugarJson = gson.toJson(lugar)
        val planJson = gson.toJson(lugarPlanModel)

        editor.putString("lugarPropioSelected", lugarJson)
        editor.putString("planLugarSelected", planJson)
        editor.apply()

        agregarLugarMaps(lugar.nombre, lugar.latitud, lugar.longitud, lugar.imagen)

        if (hayConexionInternet()) {
            if (getTipoViaje().equals("Propio")) {
                mainViewModel.navigateTo(NavigationScreen.MenuLugar)
            } else {
                if(getTipoRol().equals("Lector")) {
                    showInputModalRestriccionColaborador()
                } else {
                    mainViewModel.navigateTo(NavigationScreen.MenuLugar)
                }
            }

        } else {
            showInputModalInternet()
        }


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

    fun updateInternetTitleVisibility(isConnected: Boolean) {
        if (isConnected) {
            titleInternet.visibility = View.GONE
        } else {
            titleInternet.visibility = View.VISIBLE
        }
    }


    override fun onInternetStatusChanged(fragment: Fragment?, isConnected: Boolean) {
        if (isConnected) {
            titleInternet.visibility = View.GONE
        } else {
            titleInternet.visibility = View.VISIBLE
        }
    }

    private fun getTipoViaje(): String? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val tipoViaje = sharedPref.getString("tipoViaje", "")
        return tipoViaje
    }

    private fun getTipoRol(): String? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val tipoRol = sharedPref.getString("tipoRol", "")
        return tipoRol
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

    private fun cleanLugarPropio() {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("lugarPropioSelected", null)
        editor.putString("planLugarSelected", null)
        editor.apply()
    }

}