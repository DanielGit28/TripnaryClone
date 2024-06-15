package com.app.tripnary.ui.restaurantesRecomendadosAI

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.domain.models.LugarRecomendadoAIModel
import com.app.tripnary.domain.models.RestauranteRecomendadoAIModel
import com.app.tripnary.ui.lugaresRecomendadosAI.adapters.LugarRecomendadoAIAdapter
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.restaurantesRecomendadosAI.adapters.ListaRestaurantesRecomendadosAIAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListaRestaurantesRecomendadosAIFragment : Fragment() {
    var adapter = ListaRestaurantesRecomendadosAIAdapter(this)

    private lateinit var mainViewModel: MainViewModel

    private lateinit var diasRecomendacionesRecyclerView: RecyclerView
    private lateinit var buttonReiniciarRecomendaciones: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_restaurantes_recomendados_ai, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        buttonReiniciarRecomendaciones.setOnClickListener {
            eliminateReferenceRecomendaciones()
        }

        observe()


        return view
    }

    private fun initViews(view: View) {
        with(view) {
            diasRecomendacionesRecyclerView = findViewById(R.id.recycler_view_restaurantes_ai)
            diasRecomendacionesRecyclerView.adapter = adapter

            diasRecomendacionesRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            buttonReiniciarRecomendaciones = view.findViewById(R.id.button_reiniciar_recomendaciones_restaurantes_ai)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }


        }
    }

    private fun observe() {
        val recomendaciones = obtenerRecomendaciones()
        if(recomendaciones.isNullOrEmpty()) {
            mainViewModel.navigateTo(NavigationScreen.RegistroRestaurantesAI)
        } else {
            Log.d("Lista ai", recomendaciones.toString())
            if(recomendaciones === null || recomendaciones.size === 0) {
                adapter.setData(mutableListOf<RestauranteRecomendadoAIModel>())
            } else {
                adapter.setData(recomendaciones)
            }
        }
    }

    private fun savePreference(preferenceName: String, preference: String) {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(preferenceName, preference)
        editor.apply()
    }
    private fun eliminateReferenceRecomendaciones() {

        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("recomendacionesRestaurantesAI", null)
        editor.apply()
        mainViewModel.navigateTo(NavigationScreen.RegistroRestaurantesAI)
    }

    //Obtenerlo de reference
    private fun obtenerRecomendaciones(): List<RestauranteRecomendadoAIModel> {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceRecomendaciones = sharedPref.getString("recomendacionesRestaurantesAI", "")
        var gson = Gson()
        val typeToken = object : TypeToken<List<RestauranteRecomendadoAIModel>>() {}.type
        val recomendaciones = gson.fromJson<List<RestauranteRecomendadoAIModel>>(referenceRecomendaciones, typeToken)

        if (!referenceRecomendaciones.isNullOrEmpty()) {
            Log.d("Recomendaciones restaurantes lista: ", referenceRecomendaciones)
            adapter.setData(recomendaciones)
        }

        return recomendaciones
    }
}