package com.app.tripnary.ui.lugaresRecomendadosAI

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosAIDataSource
import com.app.tripnary.data.repositories.LugaresRecomendadosAIRepositoryImpl
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.models.PlanViajeModel

import com.app.tripnary.domain.usecases.lugaresRecomendadosAI.GetLugaresRecomendadosAIByInteresUseCase

import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.lugaresRecomendadosAI.viewmodels.LugarRecomendadoAIListViewModel
import com.app.tripnary.ui.lugaresRecomendadosAI.viewmodels.factories.LugarRecomendadoAIListViewModelFactory
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit



class RegistroPromptFragment : Fragment() {

    private val lugaresRecomendadosAIDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresRecomendadosAIDao() }
    private val lugaresDataSource by lazy { DatabaseLugaresRecomendadosAIDataSource(lugaresRecomendadosAIDao = lugaresRecomendadosAIDao) }
    private val repository by lazy { LugaresRecomendadosAIRepositoryImpl(lugaresDataSource) }
    private val getLugaresRecomendadosAIByInteresUseCase by lazy { GetLugaresRecomendadosAIByInteresUseCase(repository) }

    private val viewModelFactory by lazy {
        LugarRecomendadoAIListViewModelFactory(
            getLugaresRecomendadosAIByInteresUseCase
        )

    }
    private lateinit var viewModel: LugarRecomendadoAIListViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var submitBtn: Button
    private lateinit var lugarPreferidoInput: EditText
    private lateinit var ciudadInput: EditText
    private lateinit var presupuestoInput: EditText
    private lateinit var loadingCircle: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_lugar_recomendado_ai, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[LugarRecomendadoAIListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        initViews(view)
        observe()

        submitBtn.setOnClickListener {
            submitForm()
        }


        return view
    }


    private fun initViews(view: View) {
        with(view) {
            if (recomendacionesAlmacenadas()) {
                mainViewModel.navigateTo(NavigationScreen.ListaLugaresRecomendadosAI)
            }
            lugarPreferidoInput = view.findViewById(R.id.edit_text_lugar)
            ciudadInput = view.findViewById(R.id.edit_ciudad)
            presupuestoInput = view.findViewById(R.id.edit_text_presupuesto)
            loadingCircle = view.findViewById(R.id.loadingPanel)
            submitBtn = view.findViewById(R.id.button_submit_generar_lugares_ai)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }
        }
    }

    private fun observe() {
        viewModel.lugaresRecomendadosAIListLiveData.observe(viewLifecycleOwner) { recomendaciones ->

            if(recomendaciones !== null && recomendaciones.isNotEmpty() && !recomendaciones.contains("DeferredCoroutine")) {
                Log.d("Observe lugares ai", recomendaciones)
                Log.d("Observe lugares ai verification",
                    (recomendaciones.lowercase() === "Hubo un error con la AI".lowercase()).toString()
                )
                if(recomendaciones.lowercase() === "Hubo un error con la AI".lowercase()) {
                    loadingCircle.visibility = View.GONE
                    lugarPreferidoInput.setError("Hubo un error con la generación. Intentarlo de nuevo")
                } else {
                    loadingCircle.visibility = View.GONE
                    savePreference("recomendacionesAI", recomendaciones)
                    Log.d("Recomendaciones observe: ", recomendaciones)
                    mainViewModel.navigateTo(NavigationScreen.ListaLugaresRecomendadosAI)
                }

            } else {
                loadingCircle.visibility = View.GONE
                lugarPreferidoInput.setError("Hubo un error con la generación. Intentarlo de nuevo")
                Log.d("Recomendaciones observe","null")
            }
        }
    }

    private fun savePreference(preferenceName: String, preference: String) {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(preferenceName, preference)
        editor.apply()
    }

    private fun submitForm() {
        if(lugarPreferidoInput.text !== null && ciudadInput.text !== null && presupuestoInput.text !== null){
            loadingCircle.visibility = View.VISIBLE
            var planViaje = obtenerPlanViaje()

            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var fechaInicio = LocalDate.parse(planViaje.fechaInicio, formatter)
            var fechaFinal = LocalDate.parse(planViaje.fechaFin, formatter)

            var cantidadDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinal)

            ChronoUnit.DAYS.between(fechaFinal,fechaInicio)
            var interes = InteresLugaresAIModel("", ciudadInput.text.toString(),presupuestoInput.text.toString(),lugarPreferidoInput.text.toString(), cantidadDias.toString() , "Activo","")
            Log.d("Form",interes.toString())
            viewModel.onViewReady(interes, obtenerPlanViaje().reference,"lugares")
        } else {
            lugarPreferidoInput.setError("Rellene todos los campos")
        }
    }

    //Obtenerlo de reference
    private fun obtenerPlanViaje(): PlanViajeModel {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referencePlanViaje = sharedPref.getString("planSelected", "")
        var gson = Gson()
        val planViaje = gson.fromJson(referencePlanViaje, PlanViajeModel::class.java)
        return planViaje
    }

    private fun recomendacionesAlmacenadas(): Boolean {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceRecomendaciones = sharedPref.getString("recomendacionesAI", "")
        if (referenceRecomendaciones != null) {
            Log.d("Recomendaciones lista: ", referenceRecomendaciones)
        }
        return !referenceRecomendaciones.isNullOrEmpty()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}