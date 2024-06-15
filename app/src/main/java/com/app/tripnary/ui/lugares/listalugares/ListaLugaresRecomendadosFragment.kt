package com.app.tripnary.ui.lugares.listalugares

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.api.servicesimpl.LugarPlanServiceImpl
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.CiudadRepositoryImpl
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.LugaresRecomendadosViajesRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.domain.usecases.GetListLugaresRecomendadosUseCase
import com.app.tripnary.domain.usecases.UpdatePlanDiasUseCase
import com.app.tripnary.ui.ciudades.viewmodels.CiudadListViewModel
import com.app.tripnary.ui.ciudades.viewmodels.factories.CiudadListViewModelFactory
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.factories.AgregarLugarPlanesViewModelFactory
import com.app.tripnary.ui.lugares.listalugares.viewmodels.LugarListViewModel
import com.app.tripnary.ui.lugares.listalugares.viewmodels.factories.LugarListViewModelFactory
import com.app.tripnary.ui.lugares.listalugares.adapters.ListaLugaresAdapter
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListViewModelFactory
import com.app.tripnary.ui.planesviajes.updatediasplanes.factories.UpdatePlanDiasViewModelFactory
import com.google.gson.Gson
import java.util.Locale


class ListaLugaresRecomendadosFragment : Fragment(), ListaLugaresAdapter.LugarClickListener {

    private val repository by lazy { LugaresRecomendadosViajesRepositoryImpl() }
    private val getListLugaresRecomendadosUseCase by lazy {
        GetListLugaresRecomendadosUseCase(
            repository
        )
    }

    private val repositoryCiudad by lazy { CiudadRepositoryImpl() }
    private val getListCiudadesPaisUseCase by lazy { GetListCiudadesPaisUseCase(repositoryCiudad) }

    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }

    private val lugarPlanUseCase by lazy { AddLugarPlanUseCase(repositoryLugar) }


    //dias

    private val diaDao by lazy { AppDatabase.getInstance(requireContext()).getPlanDiasDao() }
    private val planDiasDataSource by lazy { DatabasePlanDiasDataSource(diaDao) }
    private val repositoryDias by lazy { PlanDiasRepositoryImpl(planDiasDataSource = planDiasDataSource) }
    private val getDiasListUseCase by lazy { GetListDiasPlanesUseCase(repositoryDias) }


    private val viewModelFactoryDias by lazy {
        PlanDiasListViewModelFactory(
            getDiasListUseCase
        )
    }

    private val viewModelFactoryAgregarLugar by lazy {
        AgregarLugarPlanesViewModelFactory(
            lugarPlanUseCase
        )
    }


    private lateinit var spinnerCiudades: Spinner
    private var selectedCiudad: String = "Todos"
    private var selectedDias: String = "Todos"
    private lateinit var currentLocale: Locale
    private lateinit var buttonModal: Button
    private var planViaje: PlanViajeModel? = null

    private val viewModelFactory by lazy {
        LugarListViewModelFactory(
            getListLugaresRecomendadosUseCase
        )

    }

    private val viewModelFactoryCiudades by lazy {
        CiudadListViewModelFactory(
            getListCiudadesPaisUseCase
        )

    }

    private lateinit var viewModel: LugarListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelCiudad: CiudadListViewModel
    private lateinit var viewModelLugarPlan: AgregarLugarPlanesViewModel
    private lateinit var viewModelDias: PlanDiasListViewModel


    private lateinit var lugarRecyclerView: RecyclerView

    var adapter = ListaLugaresAdapter(this, this)

    private var lugaresList: List<LugaresRecomendadosModel> = emptyList()
    private var filteredLugaresList: List<LugaresRecomendadosModel> = emptyList()
    private var filteredDiasList: List<PlanDiasModel> = emptyList()

    lateinit var nombresCiudades: Array<String>
    lateinit var diasList: Array<Int>
    private var lugarPlan: LugarPlanModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_lugares_recomendados, container, false)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[LugarListViewModel::class.java]
        viewModelLugarPlan = ViewModelProvider(
            this,
            viewModelFactoryAgregarLugar
        )[AgregarLugarPlanesViewModel::class.java]
        viewModelCiudad =
            ViewModelProvider(this, viewModelFactoryCiudades)[CiudadListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelDias =
            ViewModelProvider(this, viewModelFactoryDias)[PlanDiasListViewModel::class.java]
        currentLocale = resources.configuration.locales[0]
        observe()
        observeCiudades()
        observeDias()
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            spinnerCiudades = findViewById(R.id.spinner_ciudades_lugares)
            lugarRecyclerView = findViewById(R.id.recycler_view_lugares_items)
            lugarRecyclerView.adapter = adapter

            lugarRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
            }


        }
    }

    private fun observe() {
        viewModel.lugarListLiveData.observe(viewLifecycleOwner) { list ->
            lugaresList = list
            filterLugaresByCiudades(selectedCiudad)
            if (list.isEmpty()) {
                lugarRecyclerView.visibility = View.VISIBLE
                lugarRecyclerView.visibility = View.GONE
            } else {
                lugarRecyclerView.visibility = View.GONE
                lugarRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun filterLugaresByCiudades(ciudad: String) {
        filteredLugaresList = if (ciudad.isEmpty() || ciudad == "Todos") {
            lugaresList
        } else {
            lugaresList.filter { lugar ->
                lugar.idCiudad == getCiudadCodeByName(ciudad)
            }
        }

        adapter.setData(filteredLugaresList)
    }

    private fun getCiudadCodeByName(ciudadName: String): String {
        val ciudad = viewModelCiudad.ciudadListLiveData.value?.find {
            it.nombre == ciudadName
        }
        return ciudad?.codigoCiudad ?: ""
    }

    private fun observeCiudades() {
        viewModelCiudad.ciudadListLiveData.observe(viewLifecycleOwner) { list ->
            nombresCiudades = list.map { ciudad -> ciudad.nombre }.toTypedArray()
            initCiudadesSpinner(requireView())

            if (nombresCiudades.isNotEmpty()) {
                selectedCiudad = nombresCiudades[0]
                filterLugaresByCiudades(selectedCiudad) // Actualizar la lista de lugares filtrados
            }
        }
    }

    private fun observeDias() {
        viewModelDias.diasListLiveData.observe(viewLifecycleOwner) { list ->
            filteredDiasList = validateElements(list)
            diasList = filteredDiasList.map { dia -> dia.dia }.toTypedArray()


        }
    }

    private fun getDiaCorrespondiente(): PlanDiasModel? {
        return filteredDiasList.find { dia -> dia.dia.toString() == selectedDias }
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

    private fun initCiudadesSpinner(view: View) {
        val ciudadesSpinner = view.findViewById<Spinner>(R.id.spinner_ciudades_lugares)

        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, nombresCiudades)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        ciudadesSpinner.adapter = adapter

        ciudadesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCiudad = nombresCiudades[position]
                filterLugaresByCiudades(selectedCiudad)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewReady()
        viewModelDias.onViewReady()
        getPais()?.let { viewModelCiudad.onViewReady(it) }

    }

    override fun onLugarClickListener(lugar: LugaresRecomendadosModel, position: Int) {
        agregarLugar(lugar)
    }

    private fun getPais(): String? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val paisViaje = sharedPref.getString("paisPlanViaje", "")
        return paisViaje
    }

    private fun agregarLugar(lugar: LugaresRecomendadosModel) {

        val dia = getDiaSeleccionado()
        val lugarPlanModel = dia?.let {
            getPlanViaje()?.let { it1 ->
                LugarPlanModel(
                    "null",
                    false,
                    "null",
                    "null", dia, "null", lugar.reference, it1.reference, "null", "Activo"
                )
            }
        }

        val lugarPlanLiveData = lugarPlanModel?.let { viewModelLugarPlan.agregarLugarPlan(it) }

        viewModelLugarPlan.lugarplanAddedLiveData.observe(viewLifecycleOwner) { newlugar ->

            if (newlugar != null) {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }
        }


    }

    private fun getDiaSeleccionado(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val dia = sharedPref.getString("diaSeleccionadoPerfil", "")
        return dia
    }


}