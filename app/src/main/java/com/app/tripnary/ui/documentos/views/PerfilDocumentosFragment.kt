package com.app.tripnary.ui.documentos.views

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseDocumentosDataSource
import com.app.tripnary.data.repositories.DocumentosRepositoryImpl
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.GetDocumentosByIdPlanViajeUseCase
import com.app.tripnary.domain.usecases.GetDocumentosByReferenceUseCase
import com.app.tripnary.domain.usecases.UpdateDocumentosUseCase
import com.app.tripnary.ui.documentos.viewmodels.PerfilDocumentosViewModel
import com.app.tripnary.ui.documentos.viewmodels.UpdateDocumentosViewModel
import com.app.tripnary.ui.documentos.viewmodels.factories.PerfilDocumentosViewModelFactory
import com.app.tripnary.ui.documentos.viewmodels.factories.UpdateDocumentosViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PerfilDocumentosFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private val documentosDao by lazy { AppDatabase.getInstance(requireContext()).getDocumentosDao() }
    private val documentosDataSource by lazy { DatabaseDocumentosDataSource(documentosDao) }
    private val repositoryDocumentos by lazy { DocumentosRepositoryImpl(documentosDataSource = documentosDataSource) }
    private val getDocumentosByReferenceUseCase by lazy { GetDocumentosByReferenceUseCase(repositoryDocumentos) }
    private val updateDocumentosUseCase by lazy { UpdateDocumentosUseCase(repositoryDocumentos) }


    private lateinit var viewModelPerfilDocumentos: PerfilDocumentosViewModel
    private lateinit var viewModelUpdateDocumentos: UpdateDocumentosViewModel
    private val viewModelFactoryDocumentosByReference by lazy {
        PerfilDocumentosViewModelFactory(
            getDocumentosByReferenceUseCase
        )
    }
    private val viewModelFactoryUpdateDocumentos by lazy {
        UpdateDocumentosViewModelFactory(
            updateDocumentosUseCase
        )
    }

    private lateinit var editTextName: EditText
    private lateinit var textUrl: TextView
    private lateinit var cardLink: CardView
    private lateinit var cardDownload: CardView
    private lateinit var buttonSubmit: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelPerfilDocumentos = ViewModelProvider(this, viewModelFactoryDocumentosByReference)[PerfilDocumentosViewModel::class.java]
        viewModelUpdateDocumentos = ViewModelProvider(this, viewModelFactoryUpdateDocumentos)[UpdateDocumentosViewModel::class.java]
        return inflater.inflate(R.layout.fragment_perfil_documentos, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val documento = getDocumento()
        if (documento != null){
            initViews(view, documento)
        }
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.ListaDocumentosPlanViaje)
        }
    }
    private fun initViews(view: View, documento: DocumentosModel) {
        with(view) {
            editTextName = findViewById(R.id.edit_text_name_documento)
            textUrl = findViewById(R.id.text_url_documento)
            cardLink = findViewById(R.id.card_link_documento)
            cardDownload = findViewById(R.id.card_descargar_documento)
            buttonSubmit = findViewById(R.id.button_submit)
            editTextName.setText(documento.nombre)
            textUrl.setText(documento.url)
            val url = textUrl.text.toString()
            val fileName = url.substringAfterLast("/")
            cardLink.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "No browser detected!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            cardDownload.setOnClickListener{
                downloadFile(context, url, fileName)
            }
            buttonSubmit.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    viewModelUpdateDocumentos.updateDocumento(documento.reference, editTextName.text.toString())

                }
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }
        }

    }

    private fun getDocumento(): DocumentosModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val documentoJson = sharedPref.getString("documentoSeleccionado", null)


        return if (documentoJson != null) {
            val gson = Gson()
            gson.fromJson(documentoJson, DocumentosModel::class.java)
        } else {
            null
        }
    }

    private fun downloadFile(context: Context, url: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading $fileName")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}