package com.app.tripnary.ui.planesviajes.agregarviaje.views

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.AddPlanViajeUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.AgregarPlanViewModel
import com.app.tripnary.ui.planesviajes.agregarviaje.viewmodels.factories.AddPlanViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class RegistrarPlanViajeFragment : Fragment() {

    private var fechaInicio: String? = null
    private var fechaFin: String? = null
    private lateinit var auth: FirebaseAuth

    private lateinit var buttonShowCalendarInicio: TextView
    private lateinit var buttonShowCalendarFin: TextView
    private lateinit var edtTextNombre: EditText
    private lateinit var buttonAgregarPlan: Button

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: AgregarPlanViewModel

    private val planViajeDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planViajeDataSource by lazy { DatabasePlanesViajesDataSource(planViajeDao) }
    private val viewModelFactory: AddPlanViewModelFactory by lazy {
        AddPlanViewModelFactory(AddPlanViajeUseCase(PlanViajeRepositoryImpl(planViajeDataSource)))
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registrar_plan_viaje, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[AgregarPlanViewModel::class.java]
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            auth = Firebase.auth
            buttonShowCalendarInicio = view.findViewById(R.id.button_fecha_inicio)
            buttonShowCalendarFin = view.findViewById(R.id.button_fecha_fin)
            buttonAgregarPlan = view.findViewById(R.id.button_submit_plan)
            edtTextNombre = view.findViewById(R.id.edit_text_name_plan)
            val userLogin = auth.currentUser

            val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
            val selectedCountry = sharedPref.getString("selectedCountry", "")

            var referenceUsuario = getReferenceUsuario()


            buttonShowCalendarInicio.setOnClickListener {
                showDatePickerDialog("inicio")
            }

            buttonShowCalendarFin.setOnClickListener {
                showDatePickerDialog("fin")
            }

            buttonAgregarPlan.setOnClickListener {

                val planLiveData = selectedCountry?.let { it1 ->
                    fechaInicio?.let { it2 ->
                        fechaFin?.let { it3 ->
                            viewModel.addPlan(edtTextNombre.text.toString(),userLogin?.email.toString(),
                                it1, it2, it3
                            )
                        }
                    }
                }

                viewModel.planAddedLiveData.observe(viewLifecycleOwner) { newPlan ->

                    if (newPlan != null) {

                        mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
                    }
                }


            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.PaisList)
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

    private fun getReferenceUsuario(): String {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario.toString()
    }



}