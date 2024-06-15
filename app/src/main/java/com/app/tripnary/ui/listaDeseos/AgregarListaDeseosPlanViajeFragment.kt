package com.app.tripnary.ui.listaDeseos

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.GetDiasByIdPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.factories.AgregarLugarPlanesViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListDeseosViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListDeseosViewModelFactory
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListViewModelFactory
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajesListViewModel
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories.PlanViajeListViewModelFactory

class AgregarListaDeseosPlanViajeFragment(): Fragment(), ConnectivityListener {
    private lateinit var planesViajeViewModel: PlanViajesListViewModel
    private lateinit var diasPlanViajeViewModel: PlanDiasListDeseosViewModel
    private lateinit var mainViewModel: MainViewModel

    private val planesViajeDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planesViajeDataSource by lazy { DatabasePlanesViajesDataSource(planesViajeDao) }
    private val planesViajeRepository by lazy { PlanViajeRepositoryImpl(planDataSource = planesViajeDataSource) }
    private val getPlanesViajeListUseCase by lazy { GetListViajesReferenceUseCase(planesViajeRepository) }

    private var selectedPlan: String = "Todos"
    lateinit var planesList: Array<String>

    private var selectedDia: String = "Todos"
    lateinit var diasList: Array<Int>


    private val planesViajeViewModelFactory by lazy {
        PlanViajeListViewModelFactory(
            getPlanesViajeListUseCase
        )
    }

    private val diaDao by lazy { AppDatabase.getInstance(requireContext()).getPlanDiasDao() }
    private val planDiasDataSource by lazy { DatabasePlanDiasDataSource(diaDao) }
    private val repositoryDias by lazy { PlanDiasRepositoryImpl(planDiasDataSource = planDiasDataSource) }
    private val getDiasByIdPlanViajeUseCase by lazy { GetDiasByIdPlanViajeUseCase(repositoryDias) }

    private val viewModelFactoryDias by lazy {
        PlanDiasListDeseosViewModelFactory(
            getDiasByIdPlanViajeUseCase
        )
    }

    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }
    private val lugarPlanUseCase by lazy { AddLugarPlanUseCase(repositoryLugar) }

    private val viewModelFactoryAgregarLugar by lazy {
        AgregarLugarPlanesViewModelFactory(
            lugarPlanUseCase
        )
    }

    var referencePlan = "";
    var referenceDia = "";
    private lateinit var buttonModal: Button
    private lateinit var viewModelLugarPlan: AgregarLugarPlanesViewModel
    private lateinit var titleInternet: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        planesViajeViewModel = ViewModelProvider(this, planesViajeViewModelFactory)[PlanViajesListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        diasPlanViajeViewModel = ViewModelProvider(this, viewModelFactoryDias)[PlanDiasListDeseosViewModel::class.java]
        viewModelLugarPlan = ViewModelProvider(this, viewModelFactoryAgregarLugar)[AgregarLugarPlanesViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_agregar_lista_deseos_plan_viaje, container, false)
        initViews(view)

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        val referenceUsuario = getReferenceUsuario()
        observePlanes()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planesViajeViewModel.onViewReady(getReferenceUsuario().toString())

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        titleInternet = view.findViewById(R.id.title_internet_lista_plan)
    }

    private fun initViews(view: View) {
        with(view) {
            val spinnerPlanes = view.findViewById<Spinner>(R.id.spinner_plan)
            val spinnerDias = view.findViewById<Spinner>(R.id.spinner_dias_deseos)

            buttonModal = view.findViewById(R.id.button_submit_lista_deseos)

            buttonModal.setOnClickListener {
                val lugarPlan = LugarPlanModel(
                    "null",
                    false,
                    "null",
                    "null",
                    referenceDia,
                    "null",
                    getReferenceLugarListaDeseos().toString(),
                    referencePlan,
                    "null",
                    "Activo")

                Log.w("WARNING WARNING", lugarPlan.toString())

                if(lugarPlan.idPlanViaje.isNotEmpty() && lugarPlan.idDia.isNotEmpty() && lugarPlan.idLugarRecomendado.isNotEmpty()){
                    viewModelLugarPlan.agregarLugarPlan(lugarPlan)
                    Log.e("TEST", "Entra valido")
                }

                mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
            }
        }
    }

    private fun observePlanes() {
        planesViajeViewModel.viajesListLiveData.observe(viewLifecycleOwner) { list ->
            planesList = list.map { plan -> plan.nombre }.toTypedArray()
//            list.map { plan -> plan.nombre; Log.e("PLANN", plan.nombre) }
            initSpinnerPlanes(requireView())
        }
    }

    private fun observeDias() {
        diasPlanViajeViewModel.diasPlanListLiveData.observe(viewLifecycleOwner) { list ->
            diasList = list.map { dia -> dia.dia }.toTypedArray()
            diasList.sort()
            list.map { dia -> dia.dia; Log.e("DIASS", dia.dia.toString()) }
            initSpinnerDias(requireView())
        }
    }

    private fun initSpinnerPlanes(view: View) {
        val spinnerPlanes = view.findViewById<Spinner>(R.id.spinner_plan)

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, planesList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerPlanes.adapter = adapter

        spinnerPlanes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPlan = planesList[position].toString()

                planesViajeViewModel.viajesListLiveData.observe(viewLifecycleOwner) { list ->
                    list.map { plan -> if(plan.nombre == selectedPlan){referencePlan = plan.reference} }

                    diasPlanViajeViewModel.getDiasByIdPlanViaje(referencePlan)

                    observeDias()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerDias(view: View?) {
        val spinnerDias = view?.findViewById<Spinner>(R.id.spinner_dias_deseos)

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, diasList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerDias?.adapter = adapter

        spinnerDias?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedDia = diasList[position].toString()

                diasPlanViajeViewModel.diasPlanListLiveData.observe(viewLifecycleOwner) { list ->
                    list.map { dia -> if(dia.dia == selectedDia.toInt()){referenceDia = dia.reference} }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        Log.e("Usuario reference", referenceUsuario.toString())
        return referenceUsuario
    }

    private fun getReferenceLugarListaDeseos(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceLugarListaDeseos = sharedPref.getString("referenceLugarListaDeseos", "")
        Log.e("Lugar lista deseos reference", referenceLugarListaDeseos.toString())
        return referenceLugarListaDeseos
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
}