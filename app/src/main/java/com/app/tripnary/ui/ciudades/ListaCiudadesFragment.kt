package com.app.tripnary.ui.ciudades

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.repositories.CiudadRepositoryImpl
import com.app.tripnary.data.repositories.ContinenteRepositoryImpl
import com.app.tripnary.data.repositories.PaisRepositoryImpl
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesUseCase
import com.app.tripnary.domain.usecases.GetListContinenteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.ciudades.adapters.CiudadListAdapter
import com.app.tripnary.ui.ciudades.viewmodels.CiudadListViewModel
import com.app.tripnary.ui.ciudades.viewmodels.factories.CiudadListViewModelFactory
import com.app.tripnary.ui.continentes.viewmodels.ContinenteListViewModel
import com.app.tripnary.ui.continentes.viewmodels.factories.ContinenteListViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.paises.adapters.PaisListAdapter
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel
import com.app.tripnary.ui.paises.viewmodels.factories.PaisListViewModelFactory
import java.util.Locale


class ListaCiudadesFragment : Fragment(), CiudadListAdapter.CiudadClickListener {

    private val repository by lazy { PaisRepositoryImpl() }
    private val getListPaisUseCase by lazy { GetListPaisUseCase(repository) }

    private val repositoryCiudad by lazy { CiudadRepositoryImpl() }
    private val getListCiudadesPaisUseCase by lazy { GetListCiudadesPaisUseCase(repositoryCiudad) }


    private lateinit var spinnerCiudades: Spinner
    private lateinit var selectedPais: String
    private lateinit var currentLocale: Locale


    private val viewModelFactory by lazy {
        CiudadListViewModelFactory(
            getListCiudadesPaisUseCase
        )

    }

    private val viewModelFactoryPais by lazy {
        PaisListViewModelFactory(
            getListPaisUseCase
        )

    }

    private lateinit var viewModel: CiudadListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelPais: PaisListViewModel


    private lateinit var ciudadRecyclerView: RecyclerView

    var adapter = CiudadListAdapter(this, this)
    private var ciudadList: List<CiudadModel> = emptyList()
    private var filteredCiudadList: List<CiudadModel> = emptyList()

    lateinit var nombresPaises: Array<String>
    lateinit var nombresCiudades: Array<String>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_ciudades, container, false)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[CiudadListViewModel::class.java]
        viewModelPais = ViewModelProvider(this, viewModelFactoryPais)[PaisListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        currentLocale = resources.configuration.locales[0]
        observe()
        observePaises() // Mueve esta llamada aquí
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            spinnerCiudades = findViewById(R.id.spinner_paises_ciudades)
            ciudadRecyclerView = findViewById(R.id.recycler_view_ciudades_items)
            ciudadRecyclerView.adapter = adapter

            ciudadRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        }
    }

    private fun observe() {
        viewModel.ciudadListLiveData.observe(viewLifecycleOwner) { list ->
            ciudadList = list
            filterCiudadesByPaises(selectedPais)
            if (list.isEmpty()) {
                ciudadRecyclerView.visibility = View.VISIBLE
                ciudadRecyclerView.visibility = View.GONE
            } else {
                ciudadRecyclerView.visibility = View.GONE
                ciudadRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun filterCiudadesByPaises(pais: String) {
        filteredCiudadList = if (pais.isEmpty() || pais == "Todos") {
            ciudadList
        } else {
            ciudadList.filter { ciudad ->
                ciudad.idPais == getPaisCodeByName(pais)
            }
        }

        adapter.setData(filteredCiudadList)
    }

    private fun getPaisCodeByName(paisName: String): String {
        val pais = viewModelPais.paisListLiveData.value?.find {
            it.nombre == paisName
        }
        return pais?.codigoPais ?: ""
    }

    private fun observePaises() {
        viewModelPais.paisListLiveData.observe(viewLifecycleOwner) { list ->
            nombresPaises = list.map { pais -> pais.nombre }.toTypedArray()
            initPaisesSpinner(requireView())

            if (nombresPaises.isNotEmpty()) {
                selectedPais = nombresPaises[0]
            }
            filterCiudadesByPaises(selectedPais)
        }
    }

    private fun observeCiudades() {
        viewModel.ciudadListLiveData.observe(viewLifecycleOwner) { list ->
            nombresCiudades = list.map { ciudad -> ciudad.nombre }.toTypedArray()

        }
    }


    private fun initPaisesSpinner(view: View) {
        val paisesSpinner = view.findViewById<Spinner>(R.id.spinner_paises_ciudades)

        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, nombresCiudades)
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        paisesSpinner.adapter = adapter

        paisesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPais = nombresCiudades[position]
                filterCiudadesByPaises(selectedPais)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewReady("FRA")
        viewModelPais.onViewReady()

    }



    override fun onCiudadClickListener(ciudad: CiudadModel, position: Int) {
        TODO("Not yet implemented")
    }


}