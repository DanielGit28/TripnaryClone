package com.app.tripnary.ui.planesviajes.actualizarplanviaje

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import com.app.tripnary.domain.usecases.UpdatePlanViajeUseCase
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.factories.UpdateLugarPlanesViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.actualizarplanviaje.viewmodels.UpdatePlanViajeViewModel
import com.app.tripnary.ui.planesviajes.actualizarplanviaje.viewmodels.factories.UpdatePlanViajeViewModelFactory
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.AgregarPlanViewModel
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.factories.AddPlanViewModelFactory
import com.app.tripnary.ui.planesviajes.updatediasplanes.factories.UpdatePlanDiasViewModelFactory
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class UpdatePlanViajeFragment : Fragment() {

    private var fechaInicio: String? = null
    private var fechaFin: String? = null

    private lateinit var buttonShowCalendarInicio: TextView
    private lateinit var buttonShowCalendarFin: TextView
    private lateinit var edtTextNombre: EditText
    private lateinit var buttonUpdatePlan: Button
    private var planViaje: PlanViajeModel? = null

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: UpdatePlanViajeViewModel

    private val planDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planesViajesDataSource by lazy { DatabasePlanesViajesDataSource(planDao) }
    private val repositoryPlan by lazy { PlanViajeRepositoryImpl(planDataSource = planesViajesDataSource) }
    private val updatePlanUseCase by lazy { UpdatePlanViajeUseCase(repositoryPlan) }

    private val viewModelFactoryUpdatePlan by lazy {
        UpdatePlanViajeViewModelFactory(
            updatePlanUseCase
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_plan_viaje, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactoryUpdatePlan)[UpdatePlanViajeViewModel::class.java]
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            buttonShowCalendarInicio = view.findViewById(R.id.button_update_fecha_inicio)
            buttonShowCalendarFin = view.findViewById(R.id.button_update_fecha_fin)
            edtTextNombre = view.findViewById(R.id.edit_update_name_plan)
            buttonUpdatePlan = view.findViewById(R.id.button_submit_update_plan)

            planViaje = getPlanViaje()

            if (planViaje != null) {

                edtTextNombre.setText(planViaje!!.nombre)
                buttonShowCalendarInicio.setText(planViaje!!.fechaInicio)
                buttonShowCalendarFin.setText(planViaje!!.fechaFin)

                fechaInicio = planViaje!!.fechaInicio
                fechaFin = planViaje!!.fechaFin

            }

            buttonShowCalendarInicio.setOnClickListener {
                showDatePickerDialog("inicio")
            }

            buttonShowCalendarFin.setOnClickListener {
                showDatePickerDialog("fin")
            }

            buttonUpdatePlan.setOnClickListener {
                updatePlanViaje()
            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }


        }
    }

    private fun showDatePickerDialog(buttonType: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

            if (buttonType == "inicio") {
                fechaInicio = selectedDate
                buttonShowCalendarInicio.text = selectedDate
            } else if (buttonType == "fin") {
                fechaFin = selectedDate
                buttonShowCalendarFin.text = selectedDate

            }
        }, year, month, day)

        datePickerDialog.show()
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

    private fun updatePlanViaje() {

        if (planViaje != null) {

            planViaje!!.nombre = edtTextNombre.text.toString()
            planViaje!!.fechaInicio = fechaInicio.toString()
            planViaje!!.fechaFin = fechaFin.toString()
        }


        val planLiveData = planViaje?.let { viewModel.updatePlanViaje(it) }

        viewModel.lugarplanAddedLiveData.observe(viewLifecycleOwner) { updatePlan ->

            if (updatePlan != null) {

                mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
            }
        }
    }


}