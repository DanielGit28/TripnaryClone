package com.app.tripnary.ui.planesviajes.perfilcolaborador.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseColaboradoresDataSource
import com.app.tripnary.data.repositories.ColaboradoresRepositoryImpl
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.GetColaboradoresByReferenceUseCase
import com.app.tripnary.domain.usecases.UpdateColaboradorUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.EditarPerfilColaboradorViewModel
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.PerfilColaboradorViewModel
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.factories.EditarPerfilColaboradorViewModelFactory
import com.app.tripnary.ui.planesviajes.perfilcolaborador.viewmodels.factories.PerfilColaboradorViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilColaboradorFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel

    private val colaboradoresDao by lazy {
        AppDatabase.getInstance(requireContext()).getColaboradoresDao()
    }
    private val colaboradoresDataSource by lazy { DatabaseColaboradoresDataSource(colaboradoresDao) }
    private lateinit var viewModelPerfilColaborador: PerfilColaboradorViewModel
    private lateinit var viewModelEditarPerfilColaborador: EditarPerfilColaboradorViewModel

    private val perfilColaboradorViewModelFactory: PerfilColaboradorViewModelFactory by lazy {
        PerfilColaboradorViewModelFactory(
            GetColaboradoresByReferenceUseCase(
                ColaboradoresRepositoryImpl(colaboradoresDataSource)
            )
        )
    }

    private val editarPerfilColaboradorViewModelFactory: EditarPerfilColaboradorViewModelFactory by lazy {
        EditarPerfilColaboradorViewModelFactory(
            UpdateColaboradorUseCase(
                ColaboradoresRepositoryImpl(
                    colaboradoresDataSource
                )
            )
        )
    }

    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var spinnerRol: Spinner
    private lateinit var buttonSubmit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_colaborador, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelEditarPerfilColaborador = ViewModelProvider(
            this,
            editarPerfilColaboradorViewModelFactory
        )[EditarPerfilColaboradorViewModel::class.java]
        viewModelPerfilColaborador = ViewModelProvider(
            this,
            perfilColaboradorViewModelFactory
        )[PerfilColaboradorViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colaborador = getReferenceColaborador()
        if (colaborador != null) {
            initSpinner(view)
            initViews(view, colaborador)
        }
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
        }

    }

    private fun initViews(view: View, colaborador: ColaboradoresModel) {
        with(view) {
            textName = findViewById(R.id.text_name_colaborador)
            textEmail = findViewById(R.id.text_email_colaborador)
            buttonSubmit = findViewById(R.id.button_submit)
            spinnerRol = findViewById(R.id.spinner_colaborador_rol)

            textName.setText(colaborador.nombre)
            textEmail.setText(colaborador.correoElectronico)
            if (colaborador.rol == "Lector") {
                spinnerRol.setSelection(0)
            } else {
                spinnerRol.setSelection(1)
            }
            buttonSubmit.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    viewModelEditarPerfilColaborador.updateColaborador(colaborador.reference, textEmail.text.toString(), textName.text.toString(), "Activo", "", "",  spinnerRol.selectedItem.toString())
                }

                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }
        }
    }

    private fun initSpinner(view: View) {
        val rolSpinner = view.findViewById<Spinner>(R.id.spinner_colaborador_rol)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.roles_colaboradores,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            rolSpinner.adapter = adapter
        }
    }

    private fun getReferenceColaborador(): ColaboradoresModel? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val colaborador = sharedPref.getString("referenceColaborador", "")
        return if (colaborador != null) {
            val gson = Gson()
            gson.fromJson(colaborador, ColaboradoresModel::class.java)
        } else {
            null
        }
    }
}