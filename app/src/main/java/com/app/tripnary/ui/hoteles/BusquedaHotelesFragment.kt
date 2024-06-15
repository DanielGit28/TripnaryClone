package com.app.tripnary.ui.hoteles

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabasePlanesViajesDataSource
import com.app.tripnary.data.repositories.CiudadRepositoryImpl
import com.app.tripnary.data.repositories.PlanViajeRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import com.app.tripnary.domain.usecases.GetListViajesReferenceUseCase
import com.app.tripnary.ui.hoteles.viewmodels.ListaCiudadesViewModel
import com.app.tripnary.ui.hoteles.viewmodels.factories.ListaCiudadesViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.PlanViajesListViewModel
import com.app.tripnary.ui.planesviajes.listaviajes.viewmodels.factories.PlanViajeListViewModelFactory

class BusquedaHotelesFragment: Fragment(), ConnectivityListener {
    private lateinit var listaCiudadesViewModel: ListaCiudadesViewModel
    private lateinit var mainViewModel: MainViewModel

    lateinit var ciudadesList: Array<String>
    var calificacionesList = arrayOf("1", "2", "3", "4", "5")

    private lateinit var buttonModal: Button
    private lateinit var editTextRadio: EditText
    private lateinit var titleInternet: TextView

    var selectedCiudad = "";
    var selectedCalificacion = "";

    var latitudCiudad = ""
    var longitudCiudad = ""

    private val ciudadRepository by lazy { CiudadRepositoryImpl() }
    private val getListaCiudadesUseCase by lazy { GetListCiudadesUseCase(ciudadRepository) }

    private val listaCiudadesViewModelFactory by lazy {
        ListaCiudadesViewModelFactory(
            getListaCiudadesUseCase
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listaCiudadesViewModel = ViewModelProvider(this, listaCiudadesViewModelFactory)[ListaCiudadesViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_busqueda_hoteles, container, false)
        initViews(view)

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        val referenceUsuario = getReferenceUsuario()

        observeCiudades()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaCiudadesViewModel.getCidadesList()

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        titleInternet = view.findViewById(R.id.title_internet_home)
    }

    private fun initViews(view: View) {
        with(view) {
            val spinnerCiudad = view.findViewById<Spinner>(R.id.edit_text_ciudad_plan)
            val spinnerCalificacion = view.findViewById<Spinner>(R.id.edit_calificacion_minima_plan)
            editTextRadio = view.findViewById(R.id.edit_radio_plan)

            buttonModal = view.findViewById(R.id.button_buscar_vuelos)

            spinnerCiudad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCiudad = ciudadesList[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            spinnerCalificacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCalificacion = calificacionesList[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            buttonModal.setOnClickListener{
                val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("hotelesCiudad", latitudCiudad + "," + longitudCiudad)
                editor.putString("hotelesCalificacion", selectedCalificacion)
                editor.putString("hotelesRadio", editTextRadio.text.toString())
                editor.apply()

                mainViewModel.navigateTo(NavigationScreen.ListaHoteles)
            }
        }
    }

    private fun observeCiudades() {
        listaCiudadesViewModel.listaCiudadsLiveData.observe(viewLifecycleOwner) { list ->
            ciudadesList = list.map { ciudad -> ciudad.nombre }.toTypedArray()
            ciudadesList.sort()
            initSpinnerCiudades(requireView())
        }
    }

//    private fun observeCalificaciones() {
////        listaCiudadesViewModel.listaCiudadsLiveData.observe(viewLifecycleOwner) { list ->
//            calificacionesList = arrayOf("1", "2")
//            initSpinnerCalificacion(requireView())
////        }
//    }

    private fun initSpinnerCiudades(view: View) {
        val spinnerPlanes = view.findViewById<Spinner>(R.id.edit_text_ciudad_plan)

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, ciudadesList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerPlanes.adapter = adapter

        initSpinnerCalificacion(view)

        spinnerPlanes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCiudad = ciudadesList[position].toString()

                listaCiudadesViewModel.listaCiudadsLiveData.observe(viewLifecycleOwner) { list ->
                    list.map { ciudad ->
                        if(ciudad.nombre == selectedCiudad){
                            latitudCiudad = ciudad.latitud
                            longitudCiudad = ciudad.longitud
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerCalificacion(view: View) {
        val spinnerCalificacion = view.findViewById<Spinner>(R.id.edit_calificacion_minima_plan)

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, calificacionesList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerCalificacion.adapter = adapter

//        spinnerPlanes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }
    }


    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        Log.e("Usuario reference", referenceUsuario.toString())
        return referenceUsuario
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