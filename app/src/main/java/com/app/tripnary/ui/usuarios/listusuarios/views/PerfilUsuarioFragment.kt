package com.app.tripnary.ui.usuarios.listusuarios.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.repositories.CiudadRepositoryImpl
import com.app.tripnary.data.repositories.EstadisticasRepositoryImpl
import com.app.tripnary.domain.models.EstadisticasModel
import com.app.tripnary.domain.usecases.GetEstadisticasUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.ui.ciudades.viewmodels.factories.CiudadListViewModelFactory
import com.app.tripnary.ui.estadisticas.EstadisticasViewModel
import com.app.tripnary.ui.estadisticas.factories.EstadisticasViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.paises.viewmodels.PaisListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class PerfilUsuarioFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cardViewEditarPerfil: CardView
    private lateinit var cardViewListaDeseos: CardView
    private lateinit var cardViewPlanesViaje: CardView
    private lateinit var cardViewEstadisticas: CardView
    private lateinit var textViewNombre: TextView
    private lateinit var textViewCorreo: TextView
    private lateinit var imagePerfil: ImageView
    private lateinit var textCantidadPaises: TextView
    private lateinit var textCantidadPlanes: TextView
    private lateinit var textCantidadCiudades: TextView
    private lateinit var textCantidadLugares: TextView
    private lateinit var buttonEstadisticas: Button
    private lateinit var estadisticasModel: EstadisticasModel

    private lateinit var auth: FirebaseAuth

    private val repositoryEstadisticas by lazy { EstadisticasRepositoryImpl() }
    private val getEstadisticasUseCase by lazy { GetEstadisticasUseCase(repositoryEstadisticas) }

    private val viewModelFactory by lazy {
        EstadisticasViewModelFactory(
            getEstadisticasUseCase
        )

    }

    private lateinit var viewModelEstadisticas: EstadisticasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelEstadisticas = ViewModelProvider(this, viewModelFactory)[EstadisticasViewModel::class.java]
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }
        initViews(view)

    }

    @SuppressLint("SetTextI18n")
    private fun initViews(view: View) {
        with(view) {
            val user = getLocalUser()
            textViewNombre = findViewById(R.id.text_customer_name)
            textViewCorreo = findViewById(R.id.text_customer_email)
            imagePerfil = findViewById(R.id.image_customer_perfil)
            cardViewEditarPerfil = findViewById(R.id.card_link_edit_profile)
            cardViewPlanesViaje = findViewById(R.id.card_link_planes)
            cardViewListaDeseos = findViewById(R.id.card_link_lista_deseos)
            cardViewEstadisticas = findViewById(R.id.card_link_estadisticas)
            val profileIconImage = view.findViewById<ImageView>(R.id.image_customer_perfil)
            if (user != null) {
                if (user.get("correoUsuario") != "") {
                    Glide.with(view.context)
                        .load(user.get("fotoPerfilUsuario"))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .into(profileIconImage)
                    textViewNombre.setText(user.get("nombreUsuario").toString())
                    textViewCorreo.setText(user.get("correoUsuario").toString())
                    cardViewEditarPerfil.setOnClickListener {
                        mainViewModel.navigateTo(NavigationScreen.EditarPerfilUsuario)
                    }
                    cardViewListaDeseos.setOnClickListener{
                        mainViewModel.navigateTo(NavigationScreen.ListaDeseos)
                    }
                    cardViewPlanesViaje.setOnClickListener{
                        mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
                    }
                    cardViewEstadisticas.setOnClickListener{
                        showInputModalEstadisticas()
                    }
                    Picasso.get()
                        .load(user.get("fotoPerfilUsuario").toString())
                        .into(imagePerfil)
                } else {
                    textViewNombre.setText("Invitado")
                    textViewCorreo.setText("invitado@correo.com")
                    cardViewEditarPerfil.setOnClickListener {
                        mainViewModel.navigateTo(NavigationScreen.RegistroUsuario)
                    }
                    cardViewListaDeseos.setOnClickListener{
                        mainViewModel.navigateTo(NavigationScreen.RegistroUsuario)
                    }
                    cardViewPlanesViaje.setOnClickListener{
                        mainViewModel.navigateTo(NavigationScreen.RegistroUsuario)
                    }
                }
            }

        }
    }


    private fun showInputModalEstadisticas() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_estadisticas, null)

        buttonEstadisticas = view.findViewById(R.id.button_estadisticas)
        textCantidadLugares = view.findViewById(R.id.text_cantidad_lugares)
        textCantidadCiudades = view.findViewById(R.id.text_cantidad_ciudades)
        textCantidadPlanes = view.findViewById(R.id.text_cantidad_planes)
        textCantidadPaises = view.findViewById(R.id.text_cantidad_paises)
        auth = Firebase.auth
        val userLogin = auth.currentUser

        if (userLogin !== null) {

            val estadisticasLiveData = viewModelEstadisticas.obtenerEstadisticas(userLogin?.email.toString())

            viewModelEstadisticas.estadisticasLiveData.observe(viewLifecycleOwner) { estadisticas ->

                if (estadisticas != null) {
                    textCantidadLugares.setText(estadisticas.cantidadLugares)
                    textCantidadCiudades.setText(estadisticas.cantidadCiudades)
                    textCantidadPlanes.setText(estadisticas.cantidadPlanes)
                    textCantidadPaises.setText(estadisticas.cantidadPaises)

                }
            }

        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()


        buttonEstadisticas.setOnClickListener {
            dialog.dismiss()
        }


    }

    private fun getLocalUser(): MutableMap<String, *>? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        return sharedPref.all
    }
}