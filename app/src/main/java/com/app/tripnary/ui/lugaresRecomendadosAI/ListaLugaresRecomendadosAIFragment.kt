package com.app.tripnary.ui.lugaresRecomendadosAI;

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.app.tripnary.R
import com.app.tripnary.domain.models.LugarRecomendadoAIModel

import com.app.tripnary.ui.lugaresRecomendadosAI.adapters.LugarRecomendadoAIAdapter
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListaLugaresRecomendadosAIFragment : Fragment() {

    var adapter = LugarRecomendadoAIAdapter(this)

    private lateinit var mainViewModel: MainViewModel

    private lateinit var diasRecomendacionesRecyclerView: RecyclerView
    private lateinit var recomendacionesRecyclerView: RecyclerView
    private lateinit var buttonReiniciarRecomendaciones: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_lugares_recomendados_ai, container, false)
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
            diasRecomendacionesRecyclerView = findViewById(R.id.recycler_view_lugares_ai)
            diasRecomendacionesRecyclerView.adapter = adapter

            diasRecomendacionesRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            buttonReiniciarRecomendaciones = view.findViewById(R.id.button_reiniciar_recomendaciones_ai)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }


        }
    }

    private fun observe() {
        val recomendaciones = obtenerRecomendaciones()
        if(recomendaciones.isNullOrEmpty()) {
            mainViewModel.navigateTo(NavigationScreen.RegistroLugaresAI)
        } else {
            Log.d("Lista ai", recomendaciones.toString())
            if(recomendaciones === null || recomendaciones.size === 0) {
                adapter.setData(mutableListOf<LugarRecomendadoAIModel>())
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
        editor.putString("recomendacionesAI", null)
        editor.apply()
        mainViewModel.navigateTo(NavigationScreen.RegistroLugaresAI)
    }

    //Obtenerlo de reference
    private fun obtenerRecomendaciones(): List<LugarRecomendadoAIModel> {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceRecomendaciones = sharedPref.getString("recomendacionesAI", "")
        var gson = Gson()
        val typeToken = object : TypeToken<List<LugarRecomendadoAIModel>>() {}.type
        val recomendaciones = gson.fromJson<List<LugarRecomendadoAIModel>>(referenceRecomendaciones, typeToken)
        //val recomendaciones = gson.fromJson(referenceRecomendaciones, List<List<LugaresRecomendadosAIModel>>::class.java)
        if (!referenceRecomendaciones.isNullOrEmpty()) {
            Log.d("Recomendaciones lista: ", referenceRecomendaciones)
            adapter.setData(recomendaciones)
        }

        return recomendaciones
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}