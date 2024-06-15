package com.app.tripnary.ui.lugares.menu

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugarPlanesDataSource
import com.app.tripnary.data.repositories.LugarPlanesRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.DeleteLugarPlanUseCase
import com.app.tripnary.domain.usecases.UpdateLugarPlanesUseCase
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.UpdateLugarPlanesViewModel
import com.app.tripnary.ui.lugares.actualizarlugares.viewmodels.factories.UpdateLugarPlanesViewModelFactory
import com.app.tripnary.ui.lugares.deletelugares.DeleteLugaresViewModel
import com.app.tripnary.ui.lugares.deletelugares.factories.DeleteLugaresViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.gson.Gson


class MenuLugarFragment : Fragment() {


    private val lugarDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresPlanes() }
    private val lugarPlanesDataSource by lazy { DatabaseLugarPlanesDataSource(lugarDao) }
    private val repositoryLugar by lazy { LugarPlanesRepositoryImpl(lugarPlanesDataSource = lugarPlanesDataSource) }
    private val lugarPlanUseCase by lazy { UpdateLugarPlanesUseCase(repositoryLugar) }

    private val deletelugarPlanUseCase by lazy { DeleteLugarPlanUseCase(repositoryLugar) }

    private val viewModelFactoryUpdateLugar by lazy {
        UpdateLugarPlanesViewModelFactory(
            lugarPlanUseCase
        )
    }

    private val viewModelFactoryDeleteLugar by lazy {
        DeleteLugaresViewModelFactory(
            deletelugarPlanUseCase
        )
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var deleteViewModel: DeleteLugaresViewModel
    private lateinit var viewModel: UpdateLugarPlanesViewModel
    private lateinit var buttonEditarLugar: CardView
    private lateinit var buttonAgregarNotas: CardView
    private lateinit var buttonDeleteLugar: CardView
    private lateinit var buttonUpdateNotas: Button
    private lateinit var buttonCancelDelete: Button
    private lateinit var buttonEliminar: Button
    private lateinit var buttonInternet: Button
    private lateinit var buttonverMapa: CardView
    private lateinit var textNotas: TextView
    private var lugarPlanModel: LugarPlanModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_lugar, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            viewModelFactoryUpdateLugar
        )[UpdateLugarPlanesViewModel::class.java]
        deleteViewModel = ViewModelProvider(
            this,
            viewModelFactoryDeleteLugar
        )[DeleteLugaresViewModel::class.java]
        return view
    }

    private fun initViews(view: View) {
        with(view) {

            buttonEditarLugar = findViewById(R.id.card_link_menu_editar_lugar)
            buttonAgregarNotas = findViewById(R.id.card_link_menu_agregar_notas)
            buttonDeleteLugar = findViewById(R.id.card_link_menu_eliminar_lugar)
            buttonverMapa = findViewById(R.id.card_link_menu_mapas_lugar)
            lugarPlanModel = getLugarPlan()

            buttonEditarLugar.setOnClickListener {
                if(hayConexionInternet()) {
                    mainViewModel.navigateTo(NavigationScreen.EditarLugar)
                } else {
                    showInputModalInternet()
                }

            }

            buttonAgregarNotas.setOnClickListener {
                if(hayConexionInternet()) {
                    showInputModal()
                } else {
                    showInputModalInternet()
                }

            }

            buttonDeleteLugar.setOnClickListener {
                if(hayConexionInternet()) {
                    showInputModalDelete()
                } else {
                    showInputModalInternet()
                }

            }

            buttonverMapa.setOnClickListener {
                if(hayConexionInternet()) {
                    mainViewModel.navigateTo(NavigationScreen.MapaLugares)
                } else {
                    showInputModalInternet()
                }

            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
            }



        }
    }


    private fun getLugarPlan(): LugarPlanModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("planLugarSelected", null)

        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, LugarPlanModel::class.java)
        } else {
            null
        }
    }


    private fun showInputModal() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_notas, null)

        buttonUpdateNotas = view.findViewById(R.id.button_agregar_notas)

        textNotas = view.findViewById(R.id.edit_text_notas_lugar)
        if (lugarPlanModel != null) {
            if (lugarPlanModel!!.notas != "null") {

                textNotas.setText(lugarPlanModel!!.notas)
            }
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonUpdateNotas.setOnClickListener {
            updateNotas()
            dialog.dismiss()
        }

    }

    private fun updateNotas() {

        if (lugarPlanModel != null) {

            lugarPlanModel!!.notas = textNotas.text.toString()

            val lugarPlanLiveData = lugarPlanModel?.let { viewModel.updateLugarPlan(it) }

            viewModel.lugarplanAddedLiveData.observe(viewLifecycleOwner) { updateLugarPlan ->

                if (updateLugarPlan != null) {

                    lugarPlanModel = updateLugarPlan
                }
            }

        }



    }

    private fun showInputModalDelete() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_delete_lugar, null)

        buttonCancelDelete = view.findViewById(R.id.button_cancel_lugar_modal)
        buttonEliminar = view.findViewById(R.id.button_eliminar_modal)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonCancelDelete.setOnClickListener {
            dialog.dismiss()
        }

        buttonEliminar.setOnClickListener {
            deleteLugares()
            dialog.dismiss()
        }

    }

    private fun deleteLugares() {

        if (lugarPlanModel != null) {

            val lugarPlanLiveData = lugarPlanModel?.let { deleteViewModel.deleteLugarPlan(it.reference) }

            deleteViewModel.lugarplanAddedLiveData.observe(viewLifecycleOwner) { deleteLugar ->

                if (deleteLugar != null) {
                    mainViewModel.navigateTo(NavigationScreen.PerfilGeneralViaje)
                }
            }

        }



    }

    private fun hayConexionInternet(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun showInputModalInternet() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_no_internet, null)

        buttonInternet = view.findViewById(R.id.button_internet)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonInternet.setOnClickListener {
            dialog.dismiss()
        }

    }


}