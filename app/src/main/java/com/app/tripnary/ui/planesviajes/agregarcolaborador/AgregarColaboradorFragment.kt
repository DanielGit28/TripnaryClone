package com.app.tripnary.ui.planesviajes.agregarcolaborador

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseColaboradoresDataSource
import com.app.tripnary.data.repositories.ColaboradoresRepositoryImpl
import com.app.tripnary.domain.usecases.AddColaboradorUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.agregarcolaborador.viewmodels.AgregarColaboradorViewModel
import com.app.tripnary.ui.planesviajes.agregarcolaborador.viewmodels.factories.AgregarColaboradorViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class AgregarColaboradorFragment : Fragment() {
    private lateinit var viewModelAgregarColaborador: AgregarColaboradorViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var editTextNameColaborador: EditText
    private lateinit var editTextCorreoColaborador: EditText
    private lateinit var buttonSubmitColaborador: Button
    private lateinit var spinner: Spinner

    private val colaboradoresDao by lazy { AppDatabase.getInstance(requireContext()).getColaboradoresDao() }
    private val colaboradoresDataSource by lazy { DatabaseColaboradoresDataSource(colaboradoresDao) }

    private val viewModelAgregarColaboradorFactory : AgregarColaboradorViewModelFactory by lazy {
        AgregarColaboradorViewModelFactory(AddColaboradorUseCase(ColaboradoresRepositoryImpl(colaboradoresDataSource)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelAgregarColaborador = ViewModelProvider(this, viewModelAgregarColaboradorFactory)[AgregarColaboradorViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return inflater.inflate(R.layout.fragment_agregar_colaborador, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner(view)
        initViews(view)
    }
    private fun initViews(view: View) {
        with(view) {
            editTextNameColaborador = findViewById(R.id.edit_text_name_colaborador)
            editTextCorreoColaborador = findViewById(R.id.edit_text_correo_colaborador)
            spinner = findViewById(R.id.spinner_colaborador_rol)
            buttonSubmitColaborador = findViewById(R.id.button_submit_colaborador)
            buttonSubmitColaborador.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    getPlan()?.let { id ->
                        viewModelAgregarColaborador.addColaborador(editTextNameColaborador.text.toString(), editTextCorreoColaborador.text.toString(),
                            id, spinner.selectedItem.toString())
                    }
                }
                Toast.makeText(context, "Colaborador ${editTextCorreoColaborador.text} ha sido invitado con éxito!", Toast.LENGTH_SHORT).show()
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
    private fun getPlan(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referencePlan = sharedPref.getString("planSelected", "")
        // Check if the JSON string is not empty or null
        if (referencePlan != null){
            val planJsonObject = JSONObject(referencePlan)

            // Get the value of the "reference" field from the JSONObject
            val reference = planJsonObject.optString("reference", "")

            return reference
        }
        return null
    }
}