package com.app.tripnary.ui.general

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
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
import com.app.tripnary.classes.AdaptiveSpacingItemDecoration
import com.app.tripnary.classes.ConnectivityListener
import com.app.tripnary.classes.ConnectivityReceiver
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosDataSource
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.providers.HomeLugaresData
import com.app.tripnary.data.repositories.ListaDeseosRepositoryImpl
import com.app.tripnary.data.repositories.LugaresRecomendadosRepositoryImpl
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.AddListaDeseosUseCase
import com.app.tripnary.domain.usecases.DeleteListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetListUsuarioUseCase
import com.app.tripnary.domain.usecases.GetListaDeseosUseCase
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByInteresesUseCase
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByPopularidadUseCase
import com.app.tripnary.ui.listaDeseos.viewModels.ListaDeseosViewModel
import com.app.tripnary.ui.listaDeseos.viewModels.factories.ListaDeseosViewModelFactory
import com.app.tripnary.ui.lugareshome.adapters.HomeLugaresPopularesAdapter
import com.app.tripnary.ui.lugareshome.adapters.HomeLugaresRecomendadosAdapter
import com.app.tripnary.ui.lugareshome.adapters.models.LugaresItemModel
import com.app.tripnary.ui.lugareshome.viewmodels.LugaresRecomendadosByInteresesListViewModel
import com.app.tripnary.ui.lugareshome.viewmodels.LugaresRecomendadosByPopularidadListViewModel
import com.app.tripnary.ui.lugareshome.viewmodels.factories.LugaresRecomendadosByInteresesListViewModelFactory
import com.app.tripnary.ui.lugareshome.viewmodels.factories.LugaresRecomendadosByPopularidadListViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.UsuarioListViewModel
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.factories.UsuarioListViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class HomeFragment : Fragment(), HomeLugaresRecomendadosAdapter.LugaresRecomendadosClickListener,
    HomeLugaresPopularesAdapter.LugaresPopularesClickListener,
    ConnectivityListener, HomeLugaresRecomendadosAdapter.ListaDeseosClickListener {

    private var mainFragmentListener: MainFragment.MainFragmentListener? = null
    private lateinit var textGreeting: TextView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var titleInternet: TextView

    private var usuariosList: List<UsuariosModel> = emptyList()

    private val usuarioDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuarioDao) }
    private val lugaresRecomendadosDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresRecomendadosDao() }
    private val lugaresRecomendadosDataSource by lazy { DatabaseLugaresRecomendadosDataSource(lugaresRecomendadosDao) }

    private val repositoryUsuario by lazy { UsuariosRepositoryImpl(usuariosDataSource = usuariosDataSource) }
    private val repositoryLugaresRecomendados by lazy { LugaresRecomendadosRepositoryImpl(lugaresRecomendadosDataSource = lugaresRecomendadosDataSource) }

    private val getUsuarioListUseCase by lazy { GetListUsuarioUseCase(repositoryUsuario) }
    private val getLugaresRecomendadosByInteresesUseCase by lazy { GetLugaresRecomendadosByInteresesUseCase(repositoryLugaresRecomendados) }
    private val getLugaresRecomendadosByPopularidadUseCase by lazy { GetLugaresRecomendadosByPopularidadUseCase(repositoryLugaresRecomendados) }

    private val listaDeseosRepository by lazy { ListaDeseosRepositoryImpl() }
    private val getListaDeseosUseCase by lazy { GetListaDeseosUseCase(listaDeseosRepository) }
    private val addListaDeseosUseCase by lazy { AddListaDeseosUseCase(listaDeseosRepository) }
    private val deleteListaDeseosUseCase by lazy { DeleteListaDeseosUseCase(listaDeseosRepository) }

    private val viewModelFactoryUsuarios by lazy {
        UsuarioListViewModelFactory(
            getUsuarioListUseCase
        )
    }

    private val viewModelFactoryLugaresRecomendadosByIntereses by lazy {
        LugaresRecomendadosByInteresesListViewModelFactory(
            getLugaresRecomendadosByInteresesUseCase
        )
    }

    private val viewModelFactoryLugaresRecomendadosByPopularidad by lazy {
        LugaresRecomendadosByPopularidadListViewModelFactory(
            getLugaresRecomendadosByPopularidadUseCase
        )
    }

    private val viewModelFactoryListaDeseos by lazy {
        ListaDeseosViewModelFactory(
            getListaDeseosUseCase,
            addListaDeseosUseCase,
            deleteListaDeseosUseCase
        )
    }

    private lateinit var viewModelUsuarios: UsuarioListViewModel
    private lateinit var viewModelLugaresRecomendadosByInteresesList: LugaresRecomendadosByInteresesListViewModel
    private lateinit var viewModelLugaresRecomendadosByPopularidadList: LugaresRecomendadosByPopularidadListViewModel
    private lateinit var viewModelListaDeseos: ListaDeseosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        // Inflate the layout for this fragment
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelUsuarios =
            ViewModelProvider(this, viewModelFactoryUsuarios)[UsuarioListViewModel::class.java]
        viewModelLugaresRecomendadosByInteresesList =
            ViewModelProvider(this, viewModelFactoryLugaresRecomendadosByIntereses)[LugaresRecomendadosByInteresesListViewModel::class.java]
        viewModelLugaresRecomendadosByPopularidadList =
            ViewModelProvider(this, viewModelFactoryLugaresRecomendadosByPopularidad)[LugaresRecomendadosByPopularidadListViewModel::class.java]
        viewModelListaDeseos = ViewModelProvider(this, viewModelFactoryListaDeseos)[ListaDeseosViewModel::class.java]

        val connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setListener(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(connectivityReceiver, intentFilter)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelUsuarios.onViewReady()

        val menuIconImage = view.findViewById<ImageView>(R.id.image_menu_icon)

        val profileIconImage = view.findViewById<ImageView>(R.id.image_customer_home)

        titleInternet = view.findViewById(R.id.title_internet_home)

        // Setting on click listener for the drawer menu icon image
        menuIconImage.setOnClickListener {
            mainFragmentListener?.openCloseNavigationDrawer()
        }

        observeUsuarios(view)
        if (getCorreoUsuario() != ""){
            profileIconImage.setOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.PerfilUsuario)
            }
        } else {
            profileIconImage.setOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.Login)
            }
        }

        // Calling method to initialize the RecyclerView(Lugares)
        initRecyclerViewLugaresRecomendados(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragment.MainFragmentListener) {
            mainFragmentListener = context
        } else {
            throw ClassCastException("$context must implement MainFragmentListener")
        }
    }

    private fun initRecyclerViewLugaresRecomendados(view: View) {
        // Getting the recyclerView by its id
        val recyclerViewLugaresRecomendados = view.findViewById<RecyclerView>(R.id.recycler_view_lugares_recomendados)
        val recyclerViewLugaresPopulares = view.findViewById<RecyclerView>(R.id.recycler_view_lugares_populares)

        // Setting layout manager for the recyclerView
        recyclerViewLugaresRecomendados.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewLugaresPopulares.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Creating an object of the HomeCategoriesData class
        val lugaresRecomendadosArrayList = ArrayList<LugaresItemModel>()
        viewModelLugaresRecomendadosByInteresesList.lugaresLiveData.observe(viewLifecycleOwner) { list ->
            list.forEach { lugar ->
                lugaresRecomendadosArrayList.add(LugaresItemModel(lugar.reference, lugar.imagen, lugar.nombre, lugar.categoriaViaje, lugar.puntuacion))

            }
            val homeLugaresRecomendadosAdapter = HomeLugaresRecomendadosAdapter(lugaresRecomendadosArrayList, this, this)
            // Setting adapter for the recyclerView
            recyclerViewLugaresRecomendados.adapter = homeLugaresRecomendadosAdapter
            // Assigning equal spacing to the each item of the recyclerView
            recyclerViewLugaresRecomendados.addItemDecoration(
                AdaptiveSpacingItemDecoration(
                    resources.getDimension(
                        com.intuit.sdp.R.dimen._15sdp
                    ).roundToInt(), false
                )
            )
        }
        val lugaresPopularesArrayList = ArrayList<LugaresItemModel>()
        viewModelLugaresRecomendadosByPopularidadList.lugaresLiveData.observe(viewLifecycleOwner) { list ->

            list.forEach { lugar ->
                lugaresPopularesArrayList.add(LugaresItemModel(lugar.reference, lugar.imagen, lugar.nombre, lugar.categoriaViaje, lugar.puntuacion))
            }

            val homeLugaresPopularesAdapter = HomeLugaresPopularesAdapter(lugaresPopularesArrayList, this)

            recyclerViewLugaresPopulares.adapter = homeLugaresPopularesAdapter

            recyclerViewLugaresPopulares.addItemDecoration(
                AdaptiveSpacingItemDecoration(
                    resources.getDimension(
                        com.intuit.sdp.R.dimen._15sdp
                    ).roundToInt(), false
                )
            )
        }


    }

    @SuppressLint("SetTextI18n")
    private fun observeUsuarios(view: View) {
        textGreeting = view.findViewById(R.id.text_greeting)
        val profileIconImage = view.findViewById<ImageView>(R.id.image_customer_home)
        viewModelUsuarios.usuarioModelListLiveData.observe(viewLifecycleOwner) { list ->
            usuariosList = list
            if (list.isNotEmpty()) {
                if (usuariosList[0].nombre.isNotBlank()) {
                    val nombres = usuariosList[0].nombre.split(" ")
                    val primerNombre = nombres[0]
                    textGreeting.text = "Hola, $primerNombre!"
                } else {
                    textGreeting.text = "Hola, Invitado!"
                }

                if(!verifyUser()) {
                    textGreeting.text = "Hola, Invitado!"
                }
                val sharedPref = requireContext().getSharedPreferences(
                    "TripnaryPreferences",
                    Context.MODE_PRIVATE
                )


                val editor = sharedPref.edit()
                editor.putString("referenceUsuario", usuariosList[0].reference)
                editor.putString("interesesIniciados", "true")
                editor.putString("nombreUsuario", usuariosList[0].nombre)
                editor.putString("correoUsuario", usuariosList[0].correoElectronico)
                editor.putString("idInteresesGenerales", usuariosList[0].idInteresesGenerales)


                if(usuariosList[0].fotoPerfil.isNotEmpty()) {
                    Picasso.get()
                        .load(usuariosList[0].fotoPerfil)
                        .into(profileIconImage)

                    editor.putString("fotoPerfilUsuario", usuariosList[0].fotoPerfil)
                }
                editor.apply()
                viewModelLugaresRecomendadosByInteresesList.onViewReady(usuariosList[0].idInteresesGenerales)
                viewModelLugaresRecomendadosByPopularidadList.onViewReady()
            }

            Log.e("Usuarios Data Home", usuariosList.toString())
        }
    }
    private fun guardarReferenceLugar(reference: String) {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("referenceLugar", reference)
        editor.apply()
    }
    override fun onLugaresRecomendadosClickListener(reference: String) {
        guardarReferenceLugar(reference)
        mainViewModel.navigateTo(NavigationScreen.PerfilLugarRecomendado)
    }

    override fun onListaDeseosClickListener(referenceLugar: String) {
        var referenceUsuario = getReferenceUsuario();

        viewModelListaDeseos.addLugar(referenceUsuario, referenceLugar)
    }

    override fun onLugaresPopularesClickListener(reference: String) {
        guardarReferenceLugar(reference)
        mainViewModel.navigateTo(NavigationScreen.PerfilLugarRecomendado)
    }

    private fun getReferenceUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        Log.e("Usuario reference", referenceUsuario.toString())
        return referenceUsuario
    }
    private fun getCorreoUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val correoUsuario = sharedPref.getString("correoUsuario", "")
        return correoUsuario
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

    public fun verifyUser(): Boolean {
        return FirebaseAuth.getInstance().currentUser !== null
    }
}




