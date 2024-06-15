package com.app.tripnary.ui.hoteles

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
import com.app.tripnary.data.repositories.HotelesRepositoryImpl
import com.app.tripnary.data.repositories.VuelosRepositoryImpl
import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.domain.usecases.GetHotelesRecomendadoUseCase
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import com.app.tripnary.ui.hoteles.adapters.ListaHotelesAdapter
import com.app.tripnary.ui.hoteles.viewmodels.BusquedaHotelesViewModel
import com.app.tripnary.ui.hoteles.viewmodels.factories.BusquedaHotelesViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.vuelos.adapters.ListaVuelosAdapter
import com.app.tripnary.ui.vuelos.viewModels.BusquedaVuelosViewModel
import com.app.tripnary.ui.vuelos.viewModels.factories.BusquedaVuelosViewModelFactory

class ListaHotelesFragment:Fragment(), ConnectivityListener {
    private val repository by lazy { HotelesRepositoryImpl() }
    private val getHotelesRecomendadosUseCase by lazy { GetHotelesRecomendadoUseCase(repository) }

    private val busquedaHotelesViewModelFactory by lazy {
        BusquedaHotelesViewModelFactory(
            getHotelesRecomendadosUseCase,
        )
    }

    private lateinit var busquedaHotelesViewModel: BusquedaHotelesViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var listaHotelesRecomendadosRecyclerView: RecyclerView

    var adapter = ListaHotelesAdapter(this)
    private var hotelesList: List<HotelesModel> = emptyList()

    private lateinit var titleInternet: TextView
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        busquedaHotelesViewModel = ViewModelProvider(this, busquedaHotelesViewModelFactory)[busquedaHotelesViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_lista_hoteles, container, false)
        initViews(view)

//        observe()

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
            mainViewModel.navigateTo(NavigationScreen.BusquedaHoteles)
        }

        titleInternet = view.findViewById(R.id.title_internet_hoteles)

        busquedaHotelesViewModel = ViewModelProvider(this, busquedaHotelesViewModelFactory)[BusquedaHotelesViewModel::class.java]

        observe()
    }

    private fun initViews(view: View) {
        with(view) {
            listaHotelesRecomendadosRecyclerView = findViewById(R.id.recycler_view_lista_hoteles_items)
            listaHotelesRecomendadosRecyclerView.adapter = adapter

            imageNoData = findViewById(R.id.image_no_data_lista_hoteles)
            titleNoData = findViewById(R.id.title_no_data_lista_hoteles)
            infoNoData = findViewById(R.id.info_no_data_lista_hoteles)

            listaHotelesRecomendadosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observe() {
        val toastCargandoHoteles = Toast.makeText(context, "Buscando Hoteles...", Toast.LENGTH_LONG)

        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)

        val hotelesCiudad = sharedPref.getString("hotelesCiudad", "")
        val hotelesCalificacion = sharedPref.getString("hotelesCalificacion", "")
        var hotelesRadio = sharedPref.getString("hotelesRadio", "")

        if(hotelesRadio == ""){
            hotelesRadio = "5";
        }

        busquedaHotelesViewModel.getHotelesRecomendados(true, hotelesCiudad.toString().split(",")[0],
                hotelesCiudad.toString().split(",")[1], Integer.parseInt(hotelesRadio.toString())
        )

        toastCargandoHoteles.show()


        busquedaHotelesViewModel.listaHotelesLiveData.observe(viewLifecycleOwner) { list ->
            hotelesList = list

            if (hotelesCalificacion != null) {
                hotelesList = list.filter { hotel ->
                    hotel.puntuacion.toDouble() >= hotelesCalificacion.toDouble()
                }
            }

            hotelesList = hotelesList.sortedByDescending { it.puntuacion }

            if (hotelesList.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                listaHotelesRecomendadosRecyclerView.visibility = View.VISIBLE
                listaHotelesRecomendadosRecyclerView.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                listaHotelesRecomendadosRecyclerView.visibility = View.GONE
                listaHotelesRecomendadosRecyclerView.visibility = View.VISIBLE
                adapter.setData(hotelesList)
            }

            toastCargandoHoteles.cancel()
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