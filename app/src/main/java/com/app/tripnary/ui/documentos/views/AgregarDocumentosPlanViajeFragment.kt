package com.app.tripnary.ui.documentos.views

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseDocumentosDataSource
import com.app.tripnary.data.repositories.DocumentosRepositoryImpl
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddDocumentosUseCase
import com.app.tripnary.ui.documentos.viewmodels.AddDocumentosPlanViajeViewModel
import com.app.tripnary.ui.documentos.viewmodels.factories.AddDocumentosPlanViajeViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.InputStream
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class AgregarDocumentosPlanViajeFragment : Fragment() {
    private lateinit var viewModel: AddDocumentosPlanViajeViewModel
    private val documentosDao by lazy { AppDatabase.getInstance(requireContext()).getDocumentosDao() }
    private val documentosDataSource by lazy { DatabaseDocumentosDataSource(documentosDao) }
    private val viewModelFactory: AddDocumentosPlanViajeViewModelFactory by lazy {
        AddDocumentosPlanViajeViewModelFactory(AddDocumentosUseCase(DocumentosRepositoryImpl(documentosDataSource)))
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imagePicker: TextView
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var editNombreDocumento: EditText
    private var SELECT_PICTURE = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[AddDocumentosPlanViajeViewModel::class.java]
        return inflater.inflate(R.layout.fragment_agregar_documentos_plan_viaje, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
        }
        initViews(view)
    }


    private fun initViews(view: View) {
        imagePicker = view.findViewById(R.id.button_documento)
        imageView = view.findViewById(R.id.documento_image)
        button = view.findViewById(R.id.button_submit_documento)
        editNombreDocumento = view.findViewById(R.id.edit_documento_name)

        imagePicker.setOnClickListener {
            val documentIsNull: Boolean
            val i = Intent()
            i.type = "*/*"
            i.action = Intent.ACTION_GET_CONTENT

            i.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                "image/*",
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))

            val chooser = Intent.createChooser(i, "Select File")
            documentIsNull = if (editNombreDocumento.text != null) {
                startActivityForResult(chooser, SELECT_PICTURE)
                false
            } else {
                true
            }
            button.setOnClickListener {
                if (!documentIsNull) {
                    mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    val contentResolver = context?.contentResolver
                    val mimeType = contentResolver?.getType(selectedImageUri)
                    val inputStream: InputStream? = contentResolver?.openInputStream(selectedImageUri)
                    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

                    if (mimeType != null && mimeType.startsWith("image/")) {
                        Glide.with(this)
                            .load(selectedImageUri)
                            .into(imageView)
                        imagePicker.setHint("Imagen seleccionada con éxito")
                    } else {
                        Glide.with(this)
                            .load(R.drawable.ic_file_text_orange)
                            .into(imageView)
                        imagePicker.setHint("Archivo seleccionado con éxito")
                    }

                    val tempFile = createTempFile("document", ".$extension", context?.cacheDir)
                    val outputStream = FileOutputStream(tempFile)

                    inputStream.use { input ->
                        outputStream.use { output ->
                            val buffer = ByteArray(4 * 1024)
                            var bytesRead: Int
                            if (input != null) {
                                while (input.read(buffer).also { bytesRead = it } != -1) {
                                    output.write(buffer, 0, bytesRead)
                                }
                            }
                            output.flush()
                        }
                    }
                    val storage = FirebaseStorage.getInstance()
                    val storageRef: StorageReference = storage.reference
                    val imagesRef: StorageReference = storageRef.child("documentos/" + getPlanViaje()?.reference)

                    val fileRef: StorageReference = imagesRef.child(tempFile.name)

                    val uploadTask: UploadTask = fileRef.putFile(Uri.fromFile(tempFile))

                    uploadTask.addOnSuccessListener {
                        fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            val downloadUrl = downloadUri.toString()
                            Log.e("Download?", downloadUrl)
                            CoroutineScope(Dispatchers.IO).launch {
                                getPlanViaje()?.let { idPlanViaje -> viewModel.addDocumentosPlanViaje(editNombreDocumento.text.toString(), idPlanViaje.reference, downloadUrl) }

                            }
                        }
                    }.addOnFailureListener {
                        tempFile.delete()
                    }
                }

            }
        }
    }
    private fun getPlanViaje(): PlanViajeModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("planSelected", null)


        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, PlanViajeModel::class.java)
        } else {
            null
        }
    }
}