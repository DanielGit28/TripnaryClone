package com.app.tripnary.ui.vuelos

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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.repositories.VuelosRepositoryImpl
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.vuelos.adapters.ListaVuelosAdapter
import com.app.tripnary.ui.vuelos.viewModels.BusquedaVuelosViewModel
import com.app.tripnary.ui.vuelos.viewModels.factories.BusquedaVuelosViewModelFactory

class ListaVuelosFragment: Fragment(), ConnectivityListener {
    private val repository by lazy { VuelosRepositoryImpl() }
    private val getVuelosRecomendadosUseCase by lazy { GetVuelosRecomendadosUseCase(repository) }

    private val busquedaVuelosViewModelFactory by lazy {
        BusquedaVuelosViewModelFactory(
            getVuelosRecomendadosUseCase,
        )
    }

    private lateinit var busquedaVuelosViewModel: BusquedaVuelosViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var listaVuelosRecomendadosRecyclerView: RecyclerView

    var adapter = ListaVuelosAdapter(this)
    private var vuelosList: List<VuelosModel> = emptyList()

    private lateinit var titleInternet: TextView
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("Cargado", "Cargado")
        val view = inflater.inflate(R.layout.fragment_lista_vuelos, container, false)
        initViews(view)
        busquedaVuelosViewModel = ViewModelProvider(this, busquedaVuelosViewModelFactory)[BusquedaVuelosViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        observe()

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.BusquedaVuelos)
        }

        titleInternet = view.findViewById(R.id.title_internet_vuelos)
    }

    private fun initViews(view: View) {
        with(view) {
            listaVuelosRecomendadosRecyclerView = findViewById(R.id.recycler_view_lista_vuelos_items)
            listaVuelosRecomendadosRecyclerView.adapter = adapter

            imageNoData = findViewById(R.id.image_no_data_lista_vuelos)
            titleNoData = findViewById(R.id.title_no_data_lista_vuelos)
            infoNoData = findViewById(R.id.info_no_data_lista_vuelos)

            listaVuelosRecomendadosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observe() {
        val toastCargandoVuelos = Toast.makeText(context, "Buscando Vuelos...", Toast.LENGTH_LONG)

        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        val vuelosFechaSalida = sharedPref.getString("vuelosFechaSalida", "")
//        val vuelosFechaLlegada = sharedPref.getString("vuelosFechaLlegada", "")
        val vuelosAeropuertoSalida = sharedPref.getString("vuelosAeropuertoSalida", "")
        val vuelosAeropuertoDestino = sharedPref.getString("vuelosAeropuertoDestino", "")
        var vuelosCantidad = sharedPref.getString("vuelosCantidad", "")


        if(vuelosCantidad == ""){
            vuelosCantidad = "1";
        }

        busquedaVuelosViewModel.getVuelosRecomendados(true, vuelosAeropuertoSalida.toString(),
            vuelosAeropuertoDestino.toString(), vuelosFechaSalida.toString(),
            Integer.parseInt(vuelosCantidad.toString()), "", 50
        )

//        busquedaVuelosViewModel.getVuelosRecomendados(true, "SJO",
//            "MCO", "2023-08-11",
//            1, "2023-08-15", 50
//        )

        toastCargandoVuelos.show()


        busquedaVuelosViewModel.listaVuelosLiveData.observe(viewLifecycleOwner) { list ->
            vuelosList = list
            if (list.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                listaVuelosRecomendadosRecyclerView.visibility = View.VISIBLE
                listaVuelosRecomendadosRecyclerView.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                listaVuelosRecomendadosRecyclerView.visibility = View.GONE
                listaVuelosRecomendadosRecyclerView.visibility = View.VISIBLE
                adapter.setData(vuelosList)
            }

            toastCargandoVuelos.cancel()
        }
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