package com.app.tripnary.ui.planesviajes.listaviajes

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabasePlanesViajesColaboradorDataSource
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.data.repositories.PlanesViajesColaboracionRepositoryImpl
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import com.app.tripnary.domain.usecases.GetPlanesViajesByCorreoColaboradorUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listaviajes.adapters.PlanViajeColaboradorListAdapter
import com.app.tripnary.ui.planesviajes.listaviajes.adapters.PlanViajeListAdapter
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajeColaboradorListViewModel
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajesListViewModel
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories.PlanViajeColaboradorListViewModelFactory
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories.PlanViajeListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class PlanViajeListFragment : Fragment(), PlanViajeListAdapter.PlanViajeClickListener, PlanViajeColaboradorListAdapter.PlanViajeColaboradorClickListener,
    ConnectivityListener {

    private val planDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajeDao() }
    private val planDataSource by lazy { DatabasePlanesViajesDataSource(planDao) }
    private val repository by lazy { PlanViajeRepositoryImpl(planDataSource = planDataSource) }
    private val planColaboradorDao by lazy { AppDatabase.getInstance(requireContext()).getPlanesViajesColaboradorDao() }
    private val planColaboradorDataSource by lazy { DatabasePlanesViajesColaboradorDataSource(planColaboradorDao) }
    private val repositoryColaborador by lazy { PlanesViajesColaboracionRepositoryImpl(planesViajesColaboradorDataSource = planColaboradorDataSource)}


    private val getViajeListUseCase by lazy { GetListViajesReferenceUseCase(repository) }
    private val getViajeColaboradorListUseCase by lazy { GetPlanesViajesByCorreoColaboradorUseCase(repositoryColaborador) }

    private val viewModelFactory by lazy {
        PlanViajeListViewModelFactory(
            getViajeListUseCase
        )
    }
    private val viewModelFactoryColaborador by lazy {
        PlanViajeColaboradorListViewModelFactory(
            getViajeColaboradorListUseCase
        )
    }


    private lateinit var viewModel: PlanViajesListViewModel
    private lateinit var viewModelColaborador: PlanViajeColaboradorListViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var planViajesRecyclerView: RecyclerView
    private lateinit var planViajesColaboradorRecyclerView: RecyclerView

    var adapter = PlanViajeListAdapter(this, this)
    var adapterColaborador = PlanViajeColaboradorListAdapter(this, this)

    private lateinit var fabButton: FloatingActionButton
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView
    private lateinit var titleInternet: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plan_viaje_list, container, false)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlanViajesListViewModel::class.java]
        viewModelColaborador = ViewModelProvider(this, viewModelFactoryColaborador)[PlanViajeColaboradorListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        observe()
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            val user = getLocalUser()

            planViajesRecyclerView = findViewById(R.id.recycler_view_planes_viajes)
            planViajesRecyclerView.adapter = adapter
            planViajesRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            planViajesColaboradorRecyclerView = findViewById(R.id.recycler_view_planes_viajes_colaborador)
            planViajesColaboradorRecyclerView.adapter = adapterColaborador
            planViajesColaboradorRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)


            fabButton = findViewById(R.id.fab_add_plan_viaje)
            imageNoData = findViewById(R.id.image_no_data_lista_planes)
            titleNoData = findViewById(R.id.title_no_data_lista_planes)
            infoNoData = findViewById(R.id.info_no_data_lista_planes)
            titleInternet = findViewById(R.id.title_internet_lista_planes)
            textView2 = findViewById(R.id.textView2)
            textView3 = findViewById(R.id.textView3)
            auth = Firebase.auth

            val userLogin = auth.currentUser

            fabButton.setOnClickListener {

                if (userLogin !== null) {
                    mainViewModel.navigateTo(NavigationScreen.PaisList)
                } else {
                    mainViewModel.navigateTo(NavigationScreen.Login)

                }
            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.Main)
            }

        }
    }

    private fun observe() {
        viewModelColaborador.viajesColaboradorListLiveData.observe(viewLifecycleOwner) { list ->
            adapterColaborador.setData(list)
            if (list.isEmpty()) {
                textView3.visibility = View.VISIBLE
                textView3.visibility = View.GONE
                planViajesColaboradorRecyclerView.visibility = View.VISIBLE
                planViajesColaboradorRecyclerView.visibility = View.GONE
            } else {
                textView3.visibility = View.GONE
                textView3.visibility = View.VISIBLE
                planViajesColaboradorRecyclerView.visibility = View.GONE
                planViajesColaboradorRecyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.viajesListLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
            if (list.isEmpty()) {
                textView2.visibility = View.VISIBLE
                textView2.visibility = View.GONE
                planViajesRecyclerView.visibility = View.VISIBLE
                planViajesRecyclerView.visibility = View.GONE
            } else {
                textView2.visibility = View.GONE
                textView2.visibility = View.VISIBLE
                planViajesRecyclerView.visibility = View.GONE
                planViajesRecyclerView.visibility = View.VISIBLE
            }
        }
        viewModel.viajesListLiveData.observe(viewLifecycleOwner) { list1 ->
            viewModelColaborador.viajesColaboradorListLiveData.observe(viewLifecycleOwner) { list2 ->
                if (list1.isEmpty() && list2.isEmpty()){
                    imageNoData.visibility = View.VISIBLE
                    titleNoData.visibility = View.VISIBLE
                    infoNoData.visibility = View.VISIBLE
                } else {
                    imageNoData.visibility = View.GONE
                    titleNoData.visibility = View.GONE
                    infoNoData.visibility = View.GONE
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userLogin = auth.currentUser
        if (userLogin != null) {
            viewModel.onViewReady(userLogin?.email.toString())
            Log.e("Correo", userLogin?.email.toString())
            viewModelColaborador.onViewReady(userLogin?.email.toString())
        }
    }

    override fun onTaskClickListener(plan: PlanViajeModel, position: Int) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val planJson = gson.toJson(plan)

        editor.putString("planSelected", planJson)
        editor.apply()
        setTipoViaje("Propio")
        mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
    }
    override fun onPlanViajeColaboradorClickListener(plan: PlanViajeModel, position: Int) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val planJson = gson.toJson(plan)

        editor.putString("planSelected", planJson)
        editor.apply()
        setTipoViaje("Colaborador")
        mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
    }
    private fun getReferenceUsuario(): String? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario
    }

    private fun getLocalUser(): MutableMap<String, *>? {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        return sharedPref.all
    }

    private fun setTipoViaje(tipo: String) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("tipoViaje", tipo)
        editor.apply()
    }
    fun updateInternetTitleVisibility(isConnected: Boolean) {
        if (isConnected) {
            titleInternet.visibility = View.GONE
        } else {
            titleInternet.visibility = View.VISIBLE
        }
    }

    override fun onInternetStatusChanged(fragment: Fragment?, isConnected: Boolean) {
        if (isConnected) {
            titleInternet.visibility = View.GONE
        } else {
            titleInternet.visibility = View.VISIBLE
        }
    }



}