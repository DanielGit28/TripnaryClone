package com.app.tripnary.ui.listaDeseos

import android.app.AppComponentFactory
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.data.repositories.ListaDeseosRepositoryImpl
import com.app.tripnary.domain.models.ListaDeseosModel
import android.net.ConnectivityManager
import android.widget.ImageView
import com.app.tripnary.R
import com.app.tripnary.domain.usecases.AddListaDeseosUseCase
import com.app.tripnary.domain.usecases.DeleteListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.ui.listaDeseos.adapters.ListaDeseosAdapter
import com.app.tripnary.ui.listaDeseos.viewModels.ListaDeseosViewModel
import com.app.tripnary.ui.listaDeseos.viewModels.factories.ListaDeseosViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import java.util.Locale
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver

class ListaDeseosFragment : Fragment(), ListaDeseosAdapter.ListaDeseosClickListener,
    ListaDeseosAdapter.EliminarListaDeseosClickListener,
    ListaDeseosAdapter.AgregarListaDeseosClickListener,
    ConnectivityListener{

    private val repository by lazy { ListaDeseosRepositoryImpl() }
    private val getListaDeseosUseCase by lazy { GetListaDeseosUseCase(repository) }
    private val addListaDeseosUseCase by lazy { AddListaDeseosUseCase(repository) }
    private val deleteListaDeseosUseCase by lazy { DeleteListaDeseosUseCase(repository) }

    private lateinit var titleInternet: TextView
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView

    private lateinit var currentLocale: Locale

    private val viewModelFactory by lazy {
        ListaDeseosViewModelFactory(
            getListaDeseosUseCase,
            addListaDeseosUseCase,
            deleteListaDeseosUseCase
        )
    }

    private lateinit var viewModel: ListaDeseosViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var listaDeseosRecyclerView: RecyclerView

    var adapter = ListaDeseosAdapter(this, this, this, this)
    private var deseosList: List<ListaDeseosModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_deseos, container, false)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListaDeseosViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        currentLocale = resources.configuration.locales[0]
        val referenceUsuario = getReferenceUsuario()
        observe()

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        return view
    }

    private fun initViews(view: View) {
        with(view) {
            listaDeseosRecyclerView = findViewById(R.id.recycler_view_lista_deseos_items)
            listaDeseosRecyclerView.adapter = adapter

            imageNoData = findViewById(R.id.image_no_data_lista_deseos)
            titleNoData = findViewById(R.id.title_no_data_lista_deseos)
            infoNoData = findViewById(R.id.info_no_data_lista_deseos)

            listaDeseosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observe() {
        viewModel.listaDeseosLiveData.observe(viewLifecycleOwner) { list ->
            deseosList = list
            if (list.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                listaDeseosRecyclerView.visibility = View.VISIBLE
                listaDeseosRecyclerView.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                listaDeseosRecyclerView.visibility = View.GONE
                listaDeseosRecyclerView.visibility = View.VISIBLE
                adapter.setData(deseosList)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val referenceUsuario = getReferenceUsuario()
        viewModel.onViewReady(referenceUsuario)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        titleInternet = view.findViewById(R.id.title_internet_deseos)

    }

    override fun onListaDeseosClickListener(listaDeseos: ListaDeseosModel, position: Int) {
        val listaDeseosString = listaDeseos.toString()

//        Toast.makeText(context, listaDeseosString, Toast.LENGTH_SHORT).show()
    }

    override fun onEliminarListaDeseosClickListener(reference: String) {
        viewModel.deleteLugar(reference)
        viewModel.onViewReady(getReferenceUsuario())
        observe()
    }

    override fun onAgregarListaDeseosClickListener(idLugar: String){
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val editor = sharedPref.edit()
        editor.putString("referenceLugarListaDeseos", idLugar)
        editor.apply()

        mainViewModel.navigateTo(NavigationScreen.AgregarListaDeseosPlanViaje)
    }

    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
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