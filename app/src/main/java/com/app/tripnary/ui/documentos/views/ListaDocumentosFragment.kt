package com.app.tripnary.ui.documentos.views

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
import com.app.tripnary.data.datasources.DatabaseDocumentosDataSource
import com.app.tripnary.data.repositories.DocumentosRepositoryImpl
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.usecases.GetDocumentosByIdPlanViajeUseCase
import com.app.tripnary.ui.documentos.adapters.DocumentosPlanViajeAdapter
import com.app.tripnary.ui.documentos.viewmodels.DocumentosByIdPlanViajeListViewModel
import com.app.tripnary.ui.documentos.viewmodels.factories.DocumentosByIdPlanViajeListViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.gson.Gson
import org.json.JSONObject

class ListaDocumentosFragment : Fragment(), DocumentosPlanViajeAdapter.DocumentosListClickListener {
    private lateinit var mainViewModel: MainViewModel
    private val documentosDao by lazy { AppDatabase.getInstance(requireContext()).getDocumentosDao() }
    private val documentosDataSource by lazy { DatabaseDocumentosDataSource(documentosDao) }
    private val repositoryDocumentos by lazy { DocumentosRepositoryImpl(documentosDataSource = documentosDataSource) }
    private val getDocumentosByIdPlanViajeUseCase by lazy { GetDocumentosByIdPlanViajeUseCase(repositoryDocumentos) }

    private val viewModelFactoryDocumentosByIdPlanViaje by lazy {
        DocumentosByIdPlanViajeListViewModelFactory(
            getDocumentosByIdPlanViajeUseCase
        )
    }

    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView
    private lateinit var recyclerViewDocumentos: RecyclerView
    val documentosAdapter = DocumentosPlanViajeAdapter(this, this)

    private lateinit var viewModelDocumentosList: DocumentosByIdPlanViajeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_documentos, container, false)
        initViews(view)
        viewModelDocumentosList = ViewModelProvider(this, viewModelFactoryDocumentosByIdPlanViaje)[DocumentosByIdPlanViajeListViewModel::class.java]

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        observe()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plan = getPlan()
        if (plan != null) {
            viewModelDocumentosList.onViewReady(plan)
        }
        initViews(view)
    }

    private fun initViews(view: View) {
        recyclerViewDocumentos = view.findViewById(R.id.recycler_view_documentos)
        recyclerViewDocumentos.adapter = documentosAdapter
        recyclerViewDocumentos.layoutManager = LinearLayoutManager(context)
        imageNoData = view.findViewById(R.id.image_no_data_documentos)
        titleNoData = view.findViewById(R.id.title_no_data_documentos)
        infoNoData = view.findViewById(R.id.info_no_data_documentos)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
        }
    }
    private fun observe() {
        viewModelDocumentosList.documentosLiveData.observe(viewLifecycleOwner) {list ->
            documentosAdapter.setData(list)
            if (list.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                recyclerViewDocumentos.visibility = View.VISIBLE
                recyclerViewDocumentos.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                recyclerViewDocumentos.visibility = View.GONE
                recyclerViewDocumentos.visibility = View.VISIBLE

            }

        }
    }
    override fun onDocumentosClickListener(documento: DocumentosModel, position: Int) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()

        val gson = Gson()
        val documentoJson = gson.toJson(documento)

        editor.putString("documentoSeleccionado", documentoJson)
        editor.apply()
        mainViewModel.navigateTo(NavigationScreen.PerfilDocumento)
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

}