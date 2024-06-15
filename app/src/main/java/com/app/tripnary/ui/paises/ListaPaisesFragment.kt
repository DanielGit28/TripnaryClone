package com.app.tripnary.ui.paises

import android.content.Context
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
import com.app.tripnary.data.repositories.ContinenteRepositoryImpl
import com.app.tripnary.data.repositories.PaisRepositoryImpl
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.usecases.GetListContinenteUseCase
import com.app.tripnary.domain.usecases.GetListPaisUseCase
import com.app.tripnary.ui.continentes.viewmodels.ContinenteListViewModel
import com.app.tripnary.ui.continentes.viewmodels.factories.ContinenteListViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.paises.adapters.PaisListAdapter
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel
import com.app.tripnary.ui.paises.viewmodels.factories.PaisListViewModelFactory
import java.util.Locale


class ListaPaisesFragment : Fragment(), PaisListAdapter.PaisClickListener {

    private val repository by lazy { PaisRepositoryImpl() }
    private val getListPaisUseCase by lazy { GetListPaisUseCase(repository) }

    private val repositoryContinente by lazy { ContinenteRepositoryImpl() }
    private val getListContinenteUseCase by lazy { GetListContinenteUseCase(repositoryContinente) }


    private lateinit var spinnerContinentes: Spinner
    private lateinit var selectedContinent: String
    private lateinit var currentLocale: Locale


    private val viewModelFactory by lazy {
        PaisListViewModelFactory(
            getListPaisUseCase
        )

    }

    private val viewModelFactoryContinente by lazy {
        ContinenteListViewModelFactory(
            getListContinenteUseCase
        )

    }



    private lateinit var viewModel: PaisListViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelContinente: ContinenteListViewModel


    private lateinit var paisRecyclerView: RecyclerView

    var adapter = PaisListAdapter(this, this)
    private var countryList: List<PaisModel> = emptyList()
    private var filteredCountryList: List<PaisModel> = emptyList()
    private val defaultContinent = "África"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_paises, container, false)
        initContinentsSpinner(view)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[PaisListViewModel::class.java]
        viewModelContinente = ViewModelProvider(this, viewModelFactoryContinente)[ContinenteListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        currentLocale = resources.configuration.locales[0]
        observeContinentes()
        observe()


        return view
    }


    private fun initViews(view: View) {
        with(view) {
            spinnerContinentes = findViewById(R.id.spinner_continentes_paises)
            paisRecyclerView = findViewById(R.id.recycler_view_paises_items)
            paisRecyclerView.adapter = adapter

            paisRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        }
    }

    private fun observe() {
        viewModel.paisListLiveData.observe(viewLifecycleOwner) { list ->
            countryList = list
            if (list.isEmpty()) {
                paisRecyclerView.visibility = View.VISIBLE
                paisRecyclerView.visibility = View.GONE
            } else {
                paisRecyclerView.visibility = View.GONE
                paisRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun filterCountriesByContinent(continent: String) {
        filteredCountryList = if (continent.isEmpty() || continent == "Todos") {
            countryList
        } else {
            countryList.filter { pais ->
                pais.idContinente == getContinentCodeByName(continent)
            }
        }

        adapter.setData(filteredCountryList)
    }

    private fun getContinentCodeByName(continentName: String): String {
        val continent = viewModelContinente.continenteListLiveData.value?.find {
            it.nombre == continentName
        }
        return continent?.codigoContinente ?: ""
    }

    private fun observeContinentes() {
        viewModelContinente.continenteListLiveData.observe(viewLifecycleOwner) { list ->
            selectedContinent = defaultContinent
            filterCountriesByContinent(selectedContinent)
        }
    }

    private fun getReferenceUsuario() {
        viewModelContinente.continenteListLiveData.observe(viewLifecycleOwner) { list ->
        }
    }




    private fun initContinentsSpinner(view: View) {
        val continentesSpinner = view.findViewById<Spinner>(R.id.spinner_continentes_paises)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.continentes_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
            continentesSpinner.adapter = adapter
        }

        continentesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedContinent = parent?.getItemAtPosition(position).toString()
                filterCountriesByContinent(selectedContinent)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewReady()
        viewModelContinente.onViewReady()
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }
    }


    override fun onPaisClickListener(pais: PaisModel, position: Int) {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("selectedCountry", pais.codigoPais)
        editor.apply()

        mainViewModel.navigateTo(NavigationScreen.RegistroPlanViaje)


    }


}