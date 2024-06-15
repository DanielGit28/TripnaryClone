package com.app.tripnary.ui.restaurantesRecomendadosAI

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
import com.app.tripnary.data.repositories.RestaurantesRecomendadosAIRepositoryImpl
import com.app.tripnary.domain.models.InteresLugaresAIModel
import com.app.tripnary.domain.models.InteresesRestaurantesAIModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.restaurantesRecomendadosAI.SaveRestaurantesRecomendadosAIUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.restaurantesRecomendadosAI.viewmodels.RestauranteRecomendadoAIListViewModel
import com.app.tripnary.ui.restaurantesRecomendadosAI.viewmodels.RestauranteRecomendadoAIListViewModelFactory
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class RegistroRestaurantesAIFragment : Fragment() {
    private val repository by lazy { RestaurantesRecomendadosAIRepositoryImpl() }
    private val saveRestaurantesRecomendadosAIUseCase by lazy { SaveRestaurantesRecomendadosAIUseCase(repository) }

    private val viewModelFactory by lazy {
        RestauranteRecomendadoAIListViewModelFactory(
            saveRestaurantesRecomendadosAIUseCase
        )

    }
    private lateinit var viewModel: RestauranteRecomendadoAIListViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var submitBtn: Button
    private lateinit var ciudadInput: EditText
    private lateinit var tipoComidaInput: EditText
    private lateinit var loadingCircle: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_restaurante_ai, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[RestauranteRecomendadoAIListViewModel::class.java]
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
                mainViewModel.navigateTo(NavigationScreen.ListaRestaurantesAI)
            }
            tipoComidaInput = view.findViewById(R.id.edit_tipo_comida)
            ciudadInput = view.findViewById(R.id.edit_ciudad_restaurantes)
            loadingCircle = view.findViewById(R.id.loadingPanel)
            submitBtn = view.findViewById(R.id.button_submit_generar_restaurantes_ai)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }
        }
    }

    private fun observe() {
        viewModel.restaurantesRecomendadosAIListLiveData.observe(viewLifecycleOwner) { recomendaciones ->

            if(recomendaciones !== null && recomendaciones.isNotEmpty() && !recomendaciones.contains("DeferredCoroutine")) {
                Log.d("Observe restaurantes ai", recomendaciones)
                if(recomendaciones.lowercase() === "Hubo un error con la AI".lowercase()) {
                    loadingCircle.visibility = View.GONE
                    ciudadInput.setError("Hubo un error con la generación. Intentarlo de nuevo")
                } else {
                    loadingCircle.visibility = View.GONE
                    savePreference("recomendacionesRestaurantesAI", recomendaciones)
                    mainViewModel.navigateTo(NavigationScreen.ListaRestaurantesAI)
                }
            } else {
                Log.d("Recomendaciones restaurantes observe","null")
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
        if(tipoComidaInput.text !== null && ciudadInput.text !== null){
            loadingCircle.visibility = View.VISIBLE
            var planViaje = obtenerPlanViaje()

            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var fechaInicio = LocalDate.parse(planViaje.fechaInicio, formatter)
            var fechaFinal = LocalDate.parse(planViaje.fechaFin, formatter)

            var cantidadDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinal)

            ChronoUnit.DAYS.between(fechaFinal,fechaInicio)
            var interes = InteresesRestaurantesAIModel("", ciudadInput.text.toString(),cantidadDias.toString(), tipoComidaInput.text.toString(), "Activo", "")
            viewModel.onViewReady(interes, obtenerPlanViaje().reference,"Restaurantes")
        } else {
            ciudadInput.setError("Rellene todos los campos")
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
        val referenceRecomendaciones = sharedPref.getString("recomendacionesRestaurantesAI", "")
        if (referenceRecomendaciones != null) {
            Log.d("Recomendaciones restaurantes lista: ", referenceRecomendaciones)
        }
        return !referenceRecomendaciones.isNullOrEmpty()
    }
}