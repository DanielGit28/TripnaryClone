package com.app.tripnary.ui.tiquetes

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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.repositories.TiquetesRepositoryImpl
import com.app.tripnary.domain.usecases.AddTiqueteUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.tiquetes.viewmodel.AgregarTiqueteViewModel
import com.app.tripnary.ui.tiquetes.viewmodel.factories.AgregarTiqueteViewModelFactory

class AgregarTiquetesFragment: Fragment(), ConnectivityListener {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var agregarTiqueteViewModel : AgregarTiqueteViewModel

    private val agregarTiqueteViewModelFactory : AgregarTiqueteViewModelFactory by lazy {
        AgregarTiqueteViewModelFactory(
            AddTiqueteUseCase(
                TiquetesRepositoryImpl()
            )
        )
    }

    var tiposTiquetesList = arrayOf(
        "Reporte de problemas técnicos",
        "Ayuda con la cuenta",
        "Centro de ayuda o base de conocimientos",
        "Consulta sobre el manejo de datos"
    )

    var selectedCategoria = "";

    private lateinit var mensajeTextView: EditText
    private lateinit var titleInternet: TextView
    private lateinit var buttonCrearTiquete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_tiquete, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        agregarTiqueteViewModel = ViewModelProvider(this, agregarTiqueteViewModelFactory)[AgregarTiqueteViewModel::class.java]


        initViews(view)

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

//        observeTipos()

//        initSpinnerCategoria(view)

        return view
    }

//    private fun observeTipos() {
//        initSpinnerCategoria(requireView())
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initSpinnerCategoria(requireView())

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        titleInternet = view.findViewById(R.id.title_internet_home)

    }

    private fun initViews(view: View) {
        with(view) {
            val spinnerCategoria = view.findViewById<Spinner>(R.id.edit_text_categoria_tiquete)
            mensajeTextView = view.findViewById(R.id.edit_mensaje_tiquete)
            buttonCrearTiquete = view.findViewById(R.id.button_crear_tiquete)

            spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCategoria = tiposTiquetesList[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            buttonCrearTiquete.setOnClickListener{
                if(mensajeTextView.text.toString() != ""){
                    agregarTiqueteViewModel.addTiquete(getReferenceUsuario(), selectedCategoria, mensajeTextView.text.toString())
                    mainViewModel.navigateTo(NavigationScreen.Main)
                    Toast.makeText(activity?.applicationContext, "Tiquete enviado", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(activity?.applicationContext, "El mensaje no puede ser vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSpinnerCategoria(view: View) {
        val spinnerCategoria = view.findViewById<Spinner>(R.id.edit_text_categoria_tiquete)

        val adapter = ArrayAdapter(requireContext(), R.layout.custum_spinner_item_dias, tiposTiquetesList)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_dias)
        spinnerCategoria.adapter = adapter
    }

    private fun getReferenceUsuario(): String {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario.toString()
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