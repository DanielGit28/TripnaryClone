package com.app.tripnary.ui.vuelos

import android.app.DatePickerDialog
import android.content.Context
import android.content.IntentFilter
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.repositories.VuelosRepositoryImpl
import com.app.tripnary.domain.usecases.GetVuelosRecomendadosUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.vuelos.viewModels.BusquedaVuelosViewModel
import com.app.tripnary.ui.vuelos.viewModels.factories.BusquedaVuelosViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale


class BusquedaVuelosFragment: Fragment(), ConnectivityListener {
    private val repository by lazy { VuelosRepositoryImpl() }
    private val getVuelosRecomendadosUseCase by lazy { GetVuelosRecomendadosUseCase(repository) }

    private val viewModelFactory by lazy {
        BusquedaVuelosViewModelFactory(
            getVuelosRecomendadosUseCase,
        )
    }

    private lateinit var viewModel: BusquedaVuelosViewModel
    private lateinit var mainViewModel: MainViewModel

    private var fechaSalida: String? = null
//    private var fechaRegreso: String? = null

    private lateinit var buttonShowCalendarSalida: TextView
//    private lateinit var buttonShowCalendarRegreso: TextView
    private lateinit var editTextAeropuertoSalida: EditText
    private lateinit var editTextAeropuertoDestino: EditText
    private lateinit var editNumberCantidad: EditText
    private lateinit var buttonBuscarVuelos: Button

    private lateinit var titleInternet: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_busqueda_vuelos, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[BusquedaVuelosViewModel::class.java]
        initViews(view)

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
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        titleInternet = view.findViewById(R.id.title_internet_home)
    }

    private fun initViews(view: View) {
        with(view) {
            editTextAeropuertoSalida = view.findViewById(R.id.edit_text_aeropuerto_salida_plan)
            editTextAeropuertoDestino = view.findViewById(R.id.edit_text_aeropuerto_destino_plan)
            buttonShowCalendarSalida = view.findViewById(R.id.button_fecha_salida)
//            buttonShowCalendarRegreso = view.findViewById(R.id.button_fecha_regreso)
            editNumberCantidad = view.findViewById(R.id.edit_text_cantidad_plan)
            buttonBuscarVuelos = view.findViewById(R.id.button_buscar_vuelos)

            buttonShowCalendarSalida.setOnClickListener {
                showDatePickerDialog("inicio")
            }

//            buttonShowCalendarRegreso.setOnClickListener {
//                showDatePickerDialog("fin")
//            }

            buttonBuscarVuelos.setOnClickListener {


//                val planLiveData =
//                    fechaSalida?.let { it1 ->
//                    fechaRegreso?.let { it2 ->
//                            viewModel.getVuelosRecomendados(true, editTextAeropuertoSalida.text.toString(),
//                                editTextAeropuertoDestino.text.toString(), it1,
//                                Integer.parseInt(editNumberCantidad.text.toString()), it2, 50
//                            )
//                        }
//                    }

                val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("vuelosFechaSalida", fechaSalida)
//                editor.putString("vuelosFechaLlegada", fechaRegreso)
                editor.putString("vuelosAeropuertoSalida", editTextAeropuertoSalida.text.toString())
                editor.putString("vuelosAeropuertoDestino", editTextAeropuertoDestino.text.toString())
                editor.putString("vuelosCantidad", editNumberCantidad.text.toString())
                editor.apply()

                mainViewModel.navigateTo(NavigationScreen.ListaVuelos)

//                viewModel.listaVuelosLiveData.observe(viewLifecycleOwner) { vuelos ->
//                    Log.e("ETES", "TEST")
//                    // Check if the vuelos data is not null or empty before navigating
//                    if (!vuelos.isNullOrEmpty()) {
//                        Log.e("TES", vuelos.toString())
//
//                        mainViewModel.navigateTo(NavigationScreen.ListaVuelos)
//                    }
//                }
            }


            }
        }

    private fun showDatePickerDialog(buttonType: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

            if (buttonType == "inicio") {
                fechaSalida = selectedDate
                buttonShowCalendarSalida.text = selectedDate
            }

//            else if (buttonType == "fin") {
//                fechaRegreso = selectedDate
//                buttonShowCalendarRegreso.text = selectedDate
//
//            }
        }, year, month, day)

        datePickerDialog.show()
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