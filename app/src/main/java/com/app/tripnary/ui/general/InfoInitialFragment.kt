package com.app.tripnary.ui.general

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.GetListUsuarioUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.UsuarioListViewModel
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.factories.UsuarioListViewModelFactory

class InfoInitialFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var usuariosList: List<UsuariosModel> = emptyList()


    private val usuarioDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuarioDao) }


    private val repositoryUsuario by lazy { UsuariosRepositoryImpl(usuariosDataSource = usuariosDataSource) }

    private val getUsuarioListUseCase by lazy { GetListUsuarioUseCase(repositoryUsuario) }

    private val viewModelFactoryUsuarios by lazy {
        UsuarioListViewModelFactory(
            getUsuarioListUseCase
        )
    }

    private lateinit var viewModelUsuarios: UsuarioListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelUsuarios = ViewModelProvider(this, viewModelFactoryUsuarios)[UsuarioListViewModel::class.java]
        observeUsuarios()
        return inflater.inflate(R.layout.fragment_info_initial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelUsuarios.onViewReady()

        val myButton = view.findViewById<TextView>(R.id.text_action_label)

        myButton.setOnClickListener {
            if (usuariosList.isEmpty()) {
                mainViewModel.navigateTo(NavigationScreen.RegistroInteresesGenerales)
            } else {
                mainViewModel.navigateTo(NavigationScreen.Main)
            }
        }
    }

    private fun observeUsuarios() {
        viewModelUsuarios.usuarioModelListLiveData.observe(viewLifecycleOwner) { list ->
            usuariosList = list
            if(list.isNotEmpty()) {
                val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("referenceUsuario", usuariosList[0].reference)
                editor.apply()
            }

        }
    }


}