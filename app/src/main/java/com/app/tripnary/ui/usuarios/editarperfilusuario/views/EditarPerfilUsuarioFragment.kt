package com.app.tripnary.ui.usuarios.editarperfilusuario.views

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.usecases.EditarPerfilUsuarioUseCase
import com.app.tripnary.domain.usecases.GetPerfilUsuarioByEmailUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.EditarPerfilUsuarioViewModel
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.GetPerfilUsuarioByEmailViewModel
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories.EditarPerfilUsuarioViewModelFactory
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories.GetPerfilUsuarioByEmailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class EditarPerfilUsuarioFragment : Fragment() {
    private lateinit var chooseDateButton: TextView
    private lateinit var buttonSubmit: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var editTextName: EditText
    private lateinit var editTextMail: EditText
    private lateinit var editTextPhone: EditText
    private var fecha: String? = null

    private lateinit var viewModelGetUsuarioByEmail: GetPerfilUsuarioByEmailViewModel
    private lateinit var viewModelEditarUsuario : EditarPerfilUsuarioViewModel

    private lateinit var mainViewModel: MainViewModel

    private val usuariosDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuariosDao) }

    private val getPerfilViewModelFactory : GetPerfilUsuarioByEmailViewModelFactory by lazy {
        GetPerfilUsuarioByEmailViewModelFactory(GetPerfilUsuarioByEmailUseCase(UsuariosRepositoryImpl(usuariosDataSource)))
    }

    private val editarPerfilViewModelFactory : EditarPerfilUsuarioViewModelFactory by lazy {
        EditarPerfilUsuarioViewModelFactory(EditarPerfilUsuarioUseCase(UsuariosRepositoryImpl(usuariosDataSource)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil_usuario, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelGetUsuarioByEmail = ViewModelProvider(this, getPerfilViewModelFactory)[GetPerfilUsuarioByEmailViewModel::class.java]
        viewModelEditarUsuario = ViewModelProvider(this, editarPerfilViewModelFactory)[EditarPerfilUsuarioViewModel::class.java]
        val referenceUsuario = getReferenceUsuario()
        if (referenceUsuario != null) {
            initViews(view, referenceUsuario)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEmailUsuario()?.let { viewModelGetUsuarioByEmail.getUsuarioByEmail(it) }
        val profileIconImage = view.findViewById<ImageView>(R.id.image_customer)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.PerfilUsuario)
        }

        viewModelGetUsuarioByEmail.usuarioLiveData.observe(viewLifecycleOwner)  { usuario ->
            editTextName.setText(usuario[0].nombre)
            editTextMail.setText(usuario[0].correoElectronico)
            editTextMail.inputType = InputType.TYPE_NULL
            Log.e("Teléfono", usuario[0].telefono.toString())
            if (usuario[0].telefono != "null"){
                editTextPhone.setText(usuario[0].telefono)
            } else {
                editTextPhone.setText("Ingresar el teléfono")
            }

            textViewName.setText(usuario[0].nombre)
            textViewEmail.setText(usuario[0].correoElectronico)
            chooseDateButton.text = usuario[0].fechaNacimiento
            Glide.with(view.context)
                .load(usuario[0].fotoPerfil)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(profileIconImage)
        }
    }

    private fun initViews(view: View, referenceUsuario: String) {
        with(view){
            editTextName = view.findViewById(R.id.edit_text_name)
            editTextMail = view.findViewById(R.id.edit_text_email)
            editTextPhone = view.findViewById(R.id.edit_text_phone)
            textViewName = view.findViewById(R.id.text_customer_name)
            textViewEmail = view.findViewById(R.id.text_customer_email)
            chooseDateButton = findViewById(R.id.button_choose_date)
            buttonSubmit = findViewById(R.id.button_submit)
            chooseDateButton.setOnClickListener {
                showDatePickerDialog()
            }
            buttonSubmit.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModelEditarUsuario.updatePerfilUsuario(referenceUsuario, editTextName.text.toString(), editTextMail.text.toString(), fecha, "0", "0", editTextPhone.text.toString())
                }

                mainViewModel.navigateTo(NavigationScreen.Main)
            }
        }
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

            fecha = selectedDate
            chooseDateButton.text = selectedDate
            Toast.makeText(context, "Fecha seleccionada: $selectedDate", Toast.LENGTH_SHORT).show()
        }, year, month, day)

        datePickerDialog.show()
    }
    private fun getEmailUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val emailUsuario = sharedPref.getString("correoUsuario", "")
        return emailUsuario
    }
    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario
    }
    private fun getFotoPerfilUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("fotoPerfilUsuario", "")
        return referenceUsuario
    }
}