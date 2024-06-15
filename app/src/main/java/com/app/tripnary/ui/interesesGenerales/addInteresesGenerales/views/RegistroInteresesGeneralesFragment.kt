package com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseInteresesGeneralesDataSource
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.InteresesGeneralesRepositoryImpl
import com.app.tripnary.domain.usecases.AddInteresesGeneralesUseCase
import com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.viewmodels.AddInteresesGeneralesViewModel
import com.app.tripnary.ui.interesesgenerales.addinteresesgenerales.viewmodels.factories.AddInteresesGeneralesViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroInteresesGeneralesFragment : Fragment() {
    private lateinit var viewModel: AddInteresesGeneralesViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var spinnerTipoViaje: Spinner
    private lateinit var spinnerContinente: Spinner
    private lateinit var spinnerTemporada: Spinner
    private lateinit var buttonInteresesGenerales: Button

    private val interesesGeneralesDao by lazy { AppDatabase.getInstance(requireContext()).getInteresesGeneralesDao() }
    private val usuariosDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuariosDao) }
    private val interesesGeneralesDataSource by lazy { DatabaseInteresesGeneralesDataSource(interesesGeneralesDao) }

    private val viewModelFactory: AddInteresesGeneralesViewModelFactory by lazy {
        AddInteresesGeneralesViewModelFactory(AddInteresesGeneralesUseCase(InteresesGeneralesRepositoryImpl(interesesGeneralesDataSource, usuariosDataSource)))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[AddInteresesGeneralesViewModel::class.java]
        return inflater.inflate(R.layout.fragment_registro_intereses_generales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTouristicInterestsSpinner(view)
        initContinentsSpinner(view)
        initSeasonsSpinner(view)
        initViews(view)
    }
    private fun initViews(view: View) {
        with(view) {
            spinnerTipoViaje = findViewById(R.id.spinner_tipo_viaje)
            spinnerContinente = findViewById(R.id.spinner_continentes)
            spinnerTemporada = findViewById(R.id.spinner_temporadas)
            buttonInteresesGenerales = findViewById(R.id.button_registrar_intereses_generales)
            buttonInteresesGenerales.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addInteresesGenerales(spinnerTipoViaje.selectedItem.toString(), spinnerContinente.selectedItem.toString(), "Activo", spinnerTemporada.selectedItem.toString())
                }

                mainViewModel.navigateTo(NavigationScreen.Main)
            }
        }
    }

    private fun initTouristicInterestsSpinner(view: View){
        val tipoViajeSpinner = view.findViewById<Spinner>(R.id.spinner_tipo_viaje)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias_viaje_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            tipoViajeSpinner.adapter = adapter
        }
    }
    private fun initContinentsSpinner(view: View){
        val continentesSpinner = view.findViewById<Spinner>(R.id.spinner_continentes)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.continentes_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            continentesSpinner.adapter = adapter
        }
    }
    private fun initSeasonsSpinner(view: View){
        val continentesSpinner = view.findViewById<Spinner>(R.id.spinner_temporadas)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.temporadas_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            continentesSpinner.adapter = adapter
        }
    }
}