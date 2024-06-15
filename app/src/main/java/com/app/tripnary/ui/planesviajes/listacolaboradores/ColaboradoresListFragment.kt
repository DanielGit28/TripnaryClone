package com.app.tripnary.ui.planesviajes.listacolaboradores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseColaboradoresDataSource
import com.app.tripnary.data.repositories.ColaboradoresRepositoryImpl
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.domain.usecases.GetColaboradoresByViajeUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listacolaboradores.adapters.ColaboradoresListAdapter
import com.app.tripnary.ui.planesviajes.listacolaboradores.viewmodels.ColaboradoresListViewModel
import com.app.tripnary.ui.planesviajes.listacolaboradores.viewmodels.factories.ColaboradoresListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class ColaboradoresListFragment : Fragment(), ColaboradoresListAdapter.ColaboradoresListClickListener {
    private lateinit var mainViewModel: MainViewModel
    private val colaboradoresDao by lazy { AppDatabase.getInstance(requireContext()).getColaboradoresDao() }
    private val colaboradoresDataSource by lazy { DatabaseColaboradoresDataSource(colaboradoresDao) }
    private val colaboradoresRepository by lazy { ColaboradoresRepositoryImpl(colaboradoresDataSource = colaboradoresDataSource) }
    private val getColaboradoresByViajeUseCase by lazy { GetColaboradoresByViajeUseCase(colaboradoresRepository) }
    private val viewModelFactoryColaboradores by lazy {
        ColaboradoresListViewModelFactory(
            getColaboradoresByViajeUseCase
        )
    }
    private lateinit var viewModelColaboradoresList: ColaboradoresListViewModel
    private lateinit var fabButton: FloatingActionButton
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView

    private lateinit var recyclerViewColaboradores: RecyclerView
    private val colaboradoresAdapter = ColaboradoresListAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_colaboradores, container, false)
        initViews(view)
        viewModelColaboradoresList = ViewModelProvider(this, viewModelFactoryColaboradores)[ColaboradoresListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        observe()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plan = getPlan()
        if (plan != null) {
            viewModelColaboradoresList.onViewReady(plan)
        }
        initViews(view)
    }
    private fun initViews(view: View) {
        recyclerViewColaboradores = view.findViewById(R.id.recycler_view_colaboradores)
        recyclerViewColaboradores.adapter = colaboradoresAdapter
        recyclerViewColaboradores.layoutManager = LinearLayoutManager(context)
        imageNoData = view.findViewById(R.id.image_no_data_lista_planes)
        titleNoData = view.findViewById(R.id.title_no_data_lista_planes)
        infoNoData = view.findViewById(R.id.info_no_data_lista_planes)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
        }
        fabButton = view.findViewById(R.id.fab_add_colaborador)
        fabButton.setOnClickListener { mainViewModel.navigateTo(NavigationScreen.AgregarColaboradores) }
    }

    private fun observe() {
        viewModelColaboradoresList.colaboradoresLiveData.observe(viewLifecycleOwner) { list ->
            colaboradoresAdapter.setData(list)
            if (list.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                recyclerViewColaboradores.visibility = View.VISIBLE
                recyclerViewColaboradores.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                recyclerViewColaboradores.visibility = View.GONE
                recyclerViewColaboradores.visibility = View.VISIBLE
            }

        }
    }

    private fun getPlan(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referencePlan = sharedPref.getString("planSelected", "")
        if (referencePlan != null){
            val planJsonObject = JSONObject(referencePlan)
            val reference = planJsonObject.optString("reference", "")

            return reference
        }
        return null
    }

    override fun onColaboradoresClickListener(colaborador: ColaboradoresModel, position: Int) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        val gson = Gson()
        val colaboradorJson = gson.toJson(colaborador)
        editor.putString("referenceColaborador", colaboradorJson)
        editor.apply()
        mainViewModel.navigateTo(NavigationScreen.PerfilColaborador)
    }
}