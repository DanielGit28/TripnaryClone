package com.app.tripnary.ui.lugares.agregarlugares

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabaseLugaresPropiosDataSource
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosDataSource
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.LugarPropioRepositoryImpl
import com.app.tripnary.data.repositories.LugaresRecomendadosRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.AddLugarPropioUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.ui.ciudades.viewmodels.CiudadListViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AutoCompleteAdapter
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.factories.AgregarLugarPlanesViewModelFactory
import com.app.tripnary.ui.lugares.agregarlugarespropios.AgregarLugarPropioViewModel
import com.app.tripnary.ui.lugares.agregarlugarespropios.factories.AgregarLugarPropioViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.AgregarPlanViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListViewModelFactory
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


class AgregarLugarFragment : Fragment() {

    private val diaDao by lazy { AppDatabase.getInstance(requireContext()).getPlanDiasDao() }
    private val planDiasDataSource by lazy { DatabasePlanDiasDataSource(diaDao) }
    private val repositoryDias by lazy { PlanDiasRepositoryImpl(planDiasDataSource = planDiasDataSource) }
    private val getDiasListUseCase by lazy { GetListDiasPlanesUseCase(repositoryDias) }

    private val lugarPropioDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPropios() }
    private val databaseLugaresPropiosDataSource by lazy { DatabaseLugaresPropiosDataSource(lugarPropioDao) }
    private val repositoryLugaresPropios by lazy { LugarPropioRepositoryImpl(databaseLugaresPropiosDataSource = databaseLugaresPropiosDataSource) }
    private val addLugarPropioUseCase by lazy { AddLugarPropioUseCase(repositoryLugaresPropios) }

    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }

    private val lugarPlanUseCase by lazy { AddLugarPlanUseCase(repositoryLugar) }

    private val viewModelFactoryAgregarLugar by lazy {
        AgregarLugarPlanesViewModelFactory(
            lugarPlanUseCase
        )
    }


    private val viewModelFactoryDias by lazy {
        PlanDiasListViewModelFactory(
            getDiasListUseCase
        )
    }

    private val viewModelFactoryLugarPropio by lazy {
        AgregarLugarPropioViewModelFactory(
            addLugarPropioUseCase
        )
    }

    private var horaInicio: String? = null
    private var horaFin: String? = null
    private var latitud: String? = null
    private var longitud: String? = null

    private lateinit var buttonShowCalendarInicio: TextView
    private lateinit var buttonShowCalendarFin: TextView
    private lateinit var edtDireccion: AutoCompleteTextView
    private lateinit var buttonAgregarLugarPropio: Button
    private lateinit var placesClient: PlacesClient

    private var selectedDias: String = "Todos"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelDias: PlanDiasListViewModel
    private lateinit var viewModelLugarPropio: AgregarLugarPropioViewModel
    private lateinit var viewModelLugarPlan: AgregarLugarPlanesViewModel

    private var filteredDiasList: List<PlanDiasModel> = emptyList()
    lateinit var diasList: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_lugar, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelDias = ViewModelProvider(this, viewModelFactoryDias)[PlanDiasListViewModel::class.java]
        viewModelLugarPlan = ViewModelProvider(this, viewModelFactoryAgregarLugar)[AgregarLugarPlanesViewModel::class.java]
        viewModelLugarPropio = ViewModelProvider(this, viewModelFactoryLugarPropio)[AgregarLugarPropioViewModel::class.java]
        observeDias()
        return view
    }

    private fun observeDias() {
        viewModelDias.diasListLiveData.observe(viewLifecycleOwner) { list ->
            filteredDiasList = validateElements(list)
            diasList = filteredDiasList.map { dia -> dia.dia }.toTypedArray()



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

    private fun initViews(view: View) {
        with(view) {
            buttonShowCalendarInicio = view.findViewById(R.id.button_hora_inicio)
            buttonShowCalendarFin = view.findViewById(R.id.button_hora_fin)
            buttonAgregarLugarPropio = view.findViewById(R.id.button_submit_lugar_propio)
            edtDireccion = view.findViewById(R.id.edit_text_direccion_lugar)

            Places.initialize(requireContext(), "AIzaSyC4Vf-odUGAgOBwFquqY36zdjcjeiGB2uM")
            placesClient = Places.createClient(requireContext())

            val autocompleteAdapter = AutoCompleteAdapter(requireContext(), placesClient)


            edtDireccion.setAdapter(autocompleteAdapter)

            edtDireccion.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let { query ->
                        if (query.isNotEmpty()) {
                            autocompleteAdapter.performAutocomplete(query.toString())
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            edtDireccion.setOnItemClickListener { parent, view, position, id ->
                val selectedPrediction = autocompleteAdapter.getItem(position)
                selectedPrediction?.let { prediction ->
                    val placeId = prediction.placeId
                    val placeFields = listOf(Place.Field.LAT_LNG)
                    val placeName = prediction.getPrimaryText(null)
                    edtDireccion.setText(placeName)

                    val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
                    placesClient.fetchPlace(fetchPlaceRequest)
                        .addOnSuccessListener { response ->
                            val place = response.place
                            val latLng = place.latLng
                            latitud = latLng?.latitude.toString()
                            longitud = latLng?.longitude.toString()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                requireContext(),
                                "Error al obtener detalles del lugar: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }

            buttonShowCalendarInicio.setOnClickListener {
                showTimePickerDialog("inicio")
            }

            buttonShowCalendarFin.setOnClickListener {
                showTimePickerDialog("fin")
            }

            buttonAgregarLugarPropio.setOnClickListener {
                agregarLugarPropio()
            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
            }


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelDias.onViewReady()

    }

    private fun showTimePickerDialog(buttonType: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)

            val selectedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

            if (buttonType == "inicio") {
                horaInicio = selectedTime
                buttonShowCalendarInicio.text = selectedTime
                Toast.makeText(context, "Hora de inicio seleccionada: $selectedTime", Toast.LENGTH_SHORT).show()
            } else if (buttonType == "fin") {
                horaFin = selectedTime
                buttonShowCalendarFin.text = selectedTime
                Toast.makeText(context, "Hora de fin seleccionada: $selectedTime", Toast.LENGTH_SHORT).show()
            }
        }, hour, minute, true)

        timePickerDialog.show()
    }


    private fun getDiaCorrespondiente(): PlanDiasModel? {
        return filteredDiasList.find { dia -> dia.dia.toString() == selectedDias }
    }


    private fun agregarLugarPropio() {
        val lugarPropio = latitud?.let {
            longitud?.let { it1 ->
                LugarPropioModel(
                    "",edtDireccion.text.toString(),edtDireccion.text.toString(), "Activo",
                    "", "value", it, it1,"value"
                )
            }
        }


        val lugarPropioLiveData = lugarPropio?.let { viewModelLugarPropio.agregarLugarPropio(it) }

        viewModelLugarPropio.lugarPropioAddedLiveData.observe(viewLifecycleOwner) { newlugarPropio ->

            if (newlugarPropio != null) {

                val dia = getDiaSeleccionado()
                val lugarPlanModel = dia?.let {
                    getPlanViaje()?.let { it1 ->
                        horaFin?.let { it2 ->
                            horaInicio?.let { it3 ->
                                LugarPlanModel(
                                    "null",
                                    false,
                                    it2,
                                    it3, it,newlugarPropio.reference,"null", it1.reference,"null","Activo")
                            }
                        }
                    }
                }

                val lugarPlanLiveData = lugarPlanModel?.let { viewModelLugarPlan.agregarLugarPlan(it) }

                viewModelLugarPlan.lugarplanAddedLiveData.observe(viewLifecycleOwner) { newlugar ->

                    if (newlugar != null) {
                        mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
                    }
                }
            }
        }
    }


    private fun getDiaSeleccionado(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val dia = sharedPref.getString("diaSeleccionadoPerfil", "")
        return dia
    }


}