package com.app.tripnary.ui.lugares.actualizarlugares

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddLugarPlanUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import com.app.tripnary.ui.ciudades.viewmodels.CiudadListViewModel
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.UpdateLugarPlanesViewModel
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.factories.UpdateLugarPlanesViewModelFactory
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.AgregarLugarPlanesViewModel
import com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes.factories.AgregarLugarPlanesViewModelFactory
import com.app.tripnary.ui.lugares.agregarlugarespropios.AgregarLugarPropioViewModel
import com.app.tripnary.ui.lugares.listalugares.viewmodels.LugarListViewModel
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.PlanDiasListViewModel
import com.app.tripnary.ui.planesviajes.listadias.viewmodels.factories.PlanDiasListViewModelFactory
import com.app.tripnary.ui.planesviajes.updatediasplanes.factories.UpdatePlanDiasViewModelFactory
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale


class UpdateLugaresFragment : Fragment() {

    private val diaDao by lazy { AppDatabase.getInstance(requireContext()).getPlanDiasDao() }
    private val planDiasDataSource by lazy { DatabasePlanDiasDataSource(diaDao) }
    private val repositoryDias by lazy { PlanDiasRepositoryImpl(planDiasDataSource = planDiasDataSource) }
    private val getDiasListUseCase by lazy { GetListDiasPlanesUseCase(repositoryDias) }

    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }
    private val lugarPlanUseCase by lazy { UpdateLugarPlanesUseCase(repositoryLugar) }

    private val viewModelFactoryUpdateLugar by lazy {
        UpdateLugarPlanesViewModelFactory(
            lugarPlanUseCase
        )
    }

    private val viewModelFactoryDias by lazy {
        PlanDiasListViewModelFactory(
            getDiasListUseCase
        )
    }

    private lateinit var viewModelDias: PlanDiasListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: UpdateLugarPlanesViewModel

    private var filteredDiasList: List<PlanDiasModel> = emptyList()
    private var selectedDias: String = "Todos"
    lateinit var diasList: Array<String>


    private lateinit var spinnerDias: Spinner

    private var lugarPlanModel: LugarPlanModel? = null
    private var lugarRecomendadoModel: LugaresRecomendadosModel? = null
    private var lugarPropioModel: LugarPropioModel? = null
    private var horaInicio: String? = null
    private var horaFin: String? = null
    private lateinit var buttonShowCalendarInicio: TextView
    private lateinit var buttonShowCalendarFin: TextView
    private lateinit var buttonUpdateLugar: Button

    private lateinit var edtNombre: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_lugares, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelDias =
            ViewModelProvider(this, viewModelFactoryDias)[PlanDiasListViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            viewModelFactoryUpdateLugar
        )[UpdateLugarPlanesViewModel::class.java]
        observeDias()
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            spinnerDias = findViewById(R.id.spinner_dias_lugar_update)
            buttonShowCalendarInicio = view.findViewById(R.id.button_update_hora_inicio)
            buttonShowCalendarFin = view.findViewById(R.id.button_update_hora_fin)
            buttonUpdateLugar = view.findViewById(R.id.button_submit_update_lugar)

            lugarPlanModel = getLugarPlan()
            lugarRecomendadoModel = getLugar()
            lugarPropioModel = getLugarPropio()
            edtNombre = view.findViewById(R.id.update_text_name_lugar)

            if (lugarPlanModel != null) {

                if (lugarRecomendadoModel != null) {

                    edtNombre.setText(lugarRecomendadoModel!!.nombre)
                    edtNombre.isEnabled = false

                }
                if (lugarPropioModel != null) {

                    edtNombre.setText(lugarPropioModel!!.nombre)
                    edtNombre.isEnabled = false

                }

                if (lugarPlanModel!!.horaInicio != "null") {
                    buttonShowCalendarInicio.text = lugarPlanModel!!.horaInicio
                } else {
                    horaInicio = "null"
                    buttonShowCalendarInicio.text = "Seleccionar hora de inicio"
                }

                if (lugarPlanModel!!.horaFin != "null") {
                    buttonShowCalendarFin.text = lugarPlanModel!!.horaFin
                } else {
                    horaFin = "null"
                    buttonShowCalendarFin.text = "Seleccionar hora de fin"
                }

            }



            buttonShowCalendarInicio.setOnClickListener {
                showTimePickerDialog("inicio")
            }

            buttonShowCalendarFin.setOnClickListener {
                showTimePickerDialog("fin")
            }

            buttonUpdateLugar.setOnClickListener {
                updateLugarPlan()
            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuLugar)
            }



        }
    }

    private fun observeDias() {
        viewModelDias.diasListLiveData.observe(viewLifecycleOwner) { list ->
            filteredDiasList = validateElements(list)
            diasList = filteredDiasList.map { dia -> "Día " + dia.dia }.toTypedArray()
            val diaPlan = lugarPlanModel?.let { getDiaByReference(it.idDia) }
            if (diaPlan != null) {
                initDiasSpinner(requireView(), "Día " + diaPlan.dia.toString())
            }


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

    private fun initDiasSpinner(view: View, reference: String) {

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, diasList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerDias.adapter = adapter

        val diaSeleccionadoPorReferencia = reference

        val position = diasList.indexOfFirst { it.contains(diaSeleccionadoPorReferencia) }
        if (position != -1) {
            spinnerDias.setSelection(position)
        }

        selectedDias = reference

        spinnerDias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDias = diasList[position].toString()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelDias.onViewReady()

    }

    private fun getLugarPlan(): LugarPlanModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("planLugarSelected", null)

        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, LugarPlanModel::class.java)
        } else {
            null
        }
    }

    private fun getLugar(): LugaresRecomendadosModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("lugarRecomendadoSelected", null)

        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, LugaresRecomendadosModel::class.java)
        } else {
            null
        }
    }

    private fun getLugarPropio(): LugarPropioModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("lugarPropioSelected", null)

        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, LugarPropioModel::class.java)
        } else {
            null
        }
    }

    private fun findDayInList(selectedDay: String): PlanDiasModel? {

        val selectedDayNumber = selectedDay.substringAfter("Día ").toInt()

        for (dia in filteredDiasList) {
            if (dia.dia == selectedDayNumber) {
                return dia
            }
        }

        return null
    }

    private fun showTimePickerDialog(buttonType: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                val selectedTime =
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

                if (buttonType == "inicio") {
                    horaInicio = selectedTime
                    buttonShowCalendarInicio.text = selectedTime
                    Toast.makeText(
                        context,
                        "Hora de inicio seleccionada: $selectedTime",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (buttonType == "fin") {
                    horaFin = selectedTime
                    buttonShowCalendarFin.text = selectedTime
                    Toast.makeText(
                        context,
                        "Hora de fin seleccionada: $selectedTime",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun getDiaByReference(reference: String): PlanDiasModel? {
        return filteredDiasList.find { dia -> dia.reference == reference }
    }

    private fun updateLugarPlan() {
        val diaUpdate = findDayInList(selectedDias)

        if (lugarPlanModel != null) {

            if (diaUpdate != null) {
                lugarPlanModel!!.idDia = diaUpdate.reference
            }

            lugarPlanModel!!.horaInicio = horaInicio.toString()
            lugarPlanModel!!.horaFin = horaFin.toString()
        }


        val lugarPlanLiveData = lugarPlanModel?.let { viewModel.updateLugarPlan(it) }

        viewModel.lugarplanAddedLiveData.observe(viewLifecycleOwner) { updateLugarPlan ->

            if (updateLugarPlan != null) {

                mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
            }
        }
    }
}



