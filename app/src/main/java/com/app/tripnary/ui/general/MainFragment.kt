package com.app.tripnary.ui.general

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.usecases.GetListUsuarioUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.UsuarioListViewModel
import com.app.tripnary.ui.usuarios.listusuarios.viewmodels.factories.UsuarioListViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson


class MainFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel
    //private lateinit var auth: FirebaseAuth

    private lateinit var menu: Menu
    private lateinit var logout: MenuItem
    private lateinit var logIn: MenuItem
    private lateinit var registrarse: MenuItem

    private lateinit var googleSignInClient: GoogleSignInClient
    private var usuariosList: List<UsuariosModel> = emptyList()


    private val usuarioDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuarioDao) }


    private val repositoryUsuario by lazy { UsuariosRepositoryImpl(usuariosDataSource = usuariosDataSource) }

    private val getUsuarioListUseCase by lazy { GetListUsuarioUseCase(repositoryUsuario) }

    private val viewModelFactoryUsuarios by lazy {
        UsuarioListViewModelFactory(
            getUsuarioListUseCase
        )
    }
    private lateinit var viewModelUsuarios: UsuarioListViewModel
    interface MainFragmentListener {
        fun openCloseNavigationDrawer()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // [START initialize_auth]
        // Initialize Firebase Auth
        //auth = Firebase.auth
        // [END initialize_auth]


        //Hacerlo singleton
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelUsuarios = ViewModelProvider(this, viewModelFactoryUsuarios)[UsuarioListViewModel::class.java]

        return inflater.inflate(R.layout.fragment_main, container, false)

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        if(verifyUser()) {
            menu.findItem(R.id.login).setVisible(false).isEnabled = false
            logIn?.isVisible = false
            menu.findItem(R.id.logout).setVisible(true).isEnabled = true
            logout?.isVisible = true
            menu.findItem(R.id.register).setVisible(false).isEnabled = false
            registrarse?.isVisible = false
        } else {
            menu.findItem(R.id.login).setVisible(true).isEnabled = true
            logIn?.isVisible = true
            menu.findItem(R.id.logout).setVisible(false).isEnabled = false
            logout?.isVisible = false
            menu.findItem(R.id.register).setVisible(true).isEnabled = true
            registrarse?.isVisible
        }
        menu.findItem(R.id.login).setVisible(false).isEnabled = false
    }
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(HomeFragment())

        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout_home)

        // Setting lock mode for the drawer
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        // Getting NavigationView by its id
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view_drawer_layout_home)
        logIn = navigationView.menu.findItem(R.id.login)
        logout = navigationView.menu.findItem(R.id.logout)
        registrarse = navigationView.menu.findItem(R.id.register)
        //changeLoginOptions(navigationView)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                }

                R.id.planeViajes -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
                }

                R.id.listaDeseos -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.ListaDeseos)
                }

                R.id.mensajes -> {
                    mainViewModel.navigateTo(NavigationScreen.Canales)
                }

                R.id.busquedaVuelos -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.BusquedaVuelos)
                }

                R.id.busquedaHoteles -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.BusquedaHoteles)
                }

                R.id.crearTiquetes -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.AgregarTiquete)
                }

                R.id.login -> {
                    // Calling method to open specified activity
                    mainViewModel.navigateTo(NavigationScreen.Login)
                }
                R.id.logout -> {
                    signOut()
                }
                R.id.register -> {
                    observeUsuarios()
                    mainViewModel.navigateTo(NavigationScreen.RegistroUsuario)
                }
            }

            // Closing drawer
            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.home_bottom_navigation_view)

        // Setting Home selected in BottomNavigationView by default
        bottomNavigationView.selectedItemId = R.id.home

        if (getCorreoUsuario() != "") {
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile -> {

                        mainViewModel.navigateTo(NavigationScreen.PerfilUsuario)
                    }
                    R.id.cart -> {
                        mainViewModel.navigateTo(NavigationScreen.ListaDeseos)
                    }
                    R.id.notifications -> {
                        mainViewModel.navigateTo(NavigationScreen.ListaPlanesViajes)
                    }
                }

                true
            }
        } else {
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profile -> {

                        mainViewModel.navigateTo(NavigationScreen.Login)
                    }
                    R.id.cart -> {
                        mainViewModel.navigateTo(NavigationScreen.Login)
                    }
                    R.id.notifications -> {
                        mainViewModel.navigateTo(NavigationScreen.Login)
                    }
                }

                true
            }
        }

    }

    override fun onStart() {
        super.onStart()
        changeLoginOptions()
        //updateUI(currentUser)
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.signOut()
        eliminateReferenceUsuario()
        changeLoginOptions()
        mainViewModel.navigateTo(NavigationScreen.Main)
    }

    fun changeLoginOptions() {
        if(verifyUser()) {
            logIn?.isVisible = false
            logout?.isVisible = true
            registrarse?.isVisible = false
        } else {
            logIn?.isVisible = true
            logout?.isVisible = false
            registrarse?.isVisible
        }
    }

    public fun verifyUser(): Boolean {
        return FirebaseAuth.getInstance().currentUser !== null
    }

    // Method to toggle navigation drawer menu. NOTE: this method will be called from the Home fragment
    fun openCloseNavigationDrawer() {
        // Getting drawer layout by its id from the fragment's view
        val drawerLayout = requireView().findViewById<DrawerLayout>(R.id.drawer_layout_home)

        // Comparing
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Closing drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Opening drawer
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Getting childFragmentManager
        val fragmentManager = childFragmentManager

        // Beginning transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replacing FrameLayout with the desired Fragment
        fragmentTransaction.replace(R.id.frame_layout, fragment)

        // Committing Fragment transaction
        fragmentTransaction.commit()
    }
    private fun observeUsuarios() {
        viewModelUsuarios.usuarioModelListLiveData.observe(viewLifecycleOwner) { list ->
            usuariosList = list
            if(list.isNotEmpty()) {
                val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("referenceUsuario", usuariosList[0].reference)
                editor.apply()
            }
        }
    }

    private fun eliminateReferenceUsuario() {
        val gson = Gson()
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString("referenceUsuario", null)
        editor.apply()
    }

    private fun getCorreoUsuario(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val correoUsuario = sharedPref.getString("correoUsuario", "")
        return correoUsuario
    }



}