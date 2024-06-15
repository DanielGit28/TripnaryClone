package com.app.tripnary.ui.usuarios.registrousuario.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.usecases.RegistroUsuarioUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.usuarios.registrousuario.viewmodels.RegistroUsuarioViewModel
import com.app.tripnary.ui.usuarios.registrousuario.viewmodels.factories.RegistroUsuarioViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistroUsuarioFragment : Fragment() {
    private lateinit var viewModel: RegistroUsuarioViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var registerButton: Button

    private val usuariosDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuariosDao) }

    private val viewModelFactory : RegistroUsuarioViewModelFactory by lazy {
        RegistroUsuarioViewModelFactory(RegistroUsuarioUseCase(UsuariosRepositoryImpl(usuariosDataSource)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[RegistroUsuarioViewModel::class.java]
        return inflater.inflate(R.layout.fragment_register_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val referenceUsuario = getReferenceUsuario()
        if (referenceUsuario != null) {
            initViews(view, referenceUsuario)
        }
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }
    }

    private fun initViews(view: View, referenceUsuario: String) {
        with(view) {
            editTextName = findViewById(R.id.edit_text_name)
            editTextEmail = findViewById(R.id.edit_text_email)
            editTextPassword = findViewById(R.id.edit_text_password)
            editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password)
            registerButton = findViewById(R.id.register_button)
            if (editTextConfirmPassword.text.toString().equals(editTextPassword.text.toString())){
                registerButton.setOnClickListener {

                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.updateUsuario(referenceUsuario, editTextName.text.toString(), editTextEmail.text.toString(), editTextPassword.text.toString())
                    }

                    mainViewModel.navigateTo(NavigationScreen.Login)
                }
            }
        }
    }
    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario
    }
}