package com.app.tripnary.ui.general


import android.content.Context
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.app.tripnary.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabasePlanDiasDataSource
import com.app.tripnary.data.datasources.DatabaseUsuariosDataSource
import com.app.tripnary.data.repositories.CiudadRepositoryImpl
import com.app.tripnary.data.repositories.PlanDiasRepositoryImpl
import com.app.tripnary.data.repositories.UsuariosRepositoryImpl
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.domain.repositories.UsuariosRepository
import com.app.tripnary.domain.usecases.AddUsuarioUseCase
import com.app.tripnary.domain.usecases.EditarPerfilUsuarioUseCase
import com.app.tripnary.domain.usecases.GetListCiudadesPaisUseCase
import com.app.tripnary.domain.usecases.GetListDiasPlanesUseCase
import com.app.tripnary.domain.usecases.GetPerfilUsuarioByEmailUseCase
import com.app.tripnary.ui.ciudades.viewmodels.factories.CiudadListViewModelFactory
import com.app.tripnary.ui.usuarios.agregarusuario.AgregarUsuarioViewModel
import com.app.tripnary.ui.usuarios.agregarusuario.factories.AgregarUsuarioViewModelFactory
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.EditarPerfilUsuarioViewModel
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.GetPerfilUsuarioByEmailViewModel
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories.EditarPerfilUsuarioViewModelFactory
import com.app.tripnary.ui.usuarios.editarperfilusuario.viewmodels.factories.GetPerfilUsuarioByEmailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory


class LoginFragment : Fragment
    () {
    private lateinit var mainViewModel: MainViewModel

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    private lateinit var loginBtn: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var registerTxt: TextView
    private lateinit var client: ChatClient
    private lateinit var loadingCircle: RelativeLayout

    private lateinit var googleSignInClient: GoogleSignInClient

    //Update usuario when google login
    private val usuariosDao by lazy { AppDatabase.getInstance(requireContext()).getUsuariosDao() }
    private val usuariosDataSource by lazy { DatabaseUsuariosDataSource(usuariosDao) }
    private lateinit var viewModelEditarUsuario: EditarPerfilUsuarioViewModel
    private lateinit var getPerfilUsuarioByEmailViewModel: GetPerfilUsuarioByEmailViewModel

    private val repositoryUsuarios by lazy { UsuariosRepositoryImpl(usuariosDataSource = usuariosDataSource) }
    private val addUsuarioUseCase by lazy { AddUsuarioUseCase(repositoryUsuarios) }

    private val viewModelFactoryUsuario by lazy {
        AgregarUsuarioViewModelFactory(
            addUsuarioUseCase
        )

    }

    private lateinit var addUsuarioViewModel: AgregarUsuarioViewModel

    private val getPerfilUsuarioByEmailViewModelFactory: GetPerfilUsuarioByEmailViewModelFactory by lazy {
        GetPerfilUsuarioByEmailViewModelFactory(
            GetPerfilUsuarioByEmailUseCase(
                UsuariosRepositoryImpl(usuariosDataSource)
            )
        )
    }
    private val editarPerfilViewModelFactory: EditarPerfilUsuarioViewModelFactory by lazy {
        EditarPerfilUsuarioViewModelFactory(
            EditarPerfilUsuarioUseCase(
                UsuariosRepositoryImpl(
                    usuariosDataSource
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val googleLoginButton = root.findViewById<ImageButton>(R.id.google_login_btn)
        googleLoginButton.setOnClickListener {
            signIn()
        }
        getPerfilUsuarioByEmailViewModel = ViewModelProvider(
            this,
            getPerfilUsuarioByEmailViewModelFactory
        )[GetPerfilUsuarioByEmailViewModel::class.java]


        //Stream initialization
        val offlinePluginFactory = context?.let {
            StreamOfflinePluginFactory(
                config = Config(
                    backgroundSyncEnabled = true,
                    userPresence = true,
                    persistenceEnabled = true,
                    uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
                ),
                appContext = it,
            )
        }

        // Step 2 - Set up the client for API calls with the plugin for offline storage
        client = offlinePluginFactory?.let {
            context?.let { it1 ->
                ChatClient.Builder("q6suwcgr75xw", it1)
                    .withPlugin(it)
                    .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                    .build()
            }
        }!!

        addUsuarioViewModel =
            ViewModelProvider(this, viewModelFactoryUsuario)[AgregarUsuarioViewModel::class.java]
        viewModelEditarUsuario = ViewModelProvider(
            this,
            editarPerfilViewModelFactory
        )[EditarPerfilUsuarioViewModel::class.java]

        forgotPassword = root.findViewById(R.id.text_forgot_password)
        loadingCircle = root.findViewById(R.id.loadingPanelLogin)
        loginBtn = root.findViewById(R.id.button_submit)
        emailInput = root.findViewById(R.id.edit_text_email)
        registerTxt = root.findViewById(R.id.text_create_here)
        passwordInput = root.findViewById<EditText>(R.id.edit_text_password)
        loginBtn.setOnClickListener {
            firebaseAuthentication(emailInput.text.toString(), passwordInput.text.toString())
        }
        forgotPassword.setOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.OlvidoContrasenna)
        }

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        registerTxt.setOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.RegistroUsuario)
        }

        getPerfilUsuarioByEmailViewModel.usuarioLiveData.observe(viewLifecycleOwner) { usuario ->
            if (usuario.size === 0) {
                emailInput.setError("No se encontró un usuario registrado con ese correo")
            } else {
                updateUI(usuario[0])
            }
        }

        val toolbar = root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        loadingCircle.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    Log.d(TAG, "signInWithCredential:success")

                    if (user !== null) {
                        getPerfilUsuarioByEmailViewModel.getUsuarioByEmail(user?.email.toString())

                        getPerfilUsuarioByEmailViewModel.usuarioLiveData.observe(viewLifecycleOwner) { usuario ->
                            if (usuario.size === 0) {
                                Log.e("InteresesIniciados", getInteresesIniciados())
                                if (getInteresesIniciados() == "true") {
                                    viewModelEditarUsuario.updatePerfilUsuario(
                                        getReferenceUsuarioUpdate(),
                                        user?.displayName.toString(),
                                        user?.email.toString(),
                                        "",
                                        "",
                                        "",
                                        user?.phoneNumber.toString()
                                    )
                                    Log.e("InteresesIniciados", getInteresesIniciados())
                                    updateUser(user)
                                    updateInteresesIniciados()
                                } else {
                                    val usuariosModel = UsuariosModel(
                                        "",
                                        "",
                                        user?.email.toString(),
                                        "Activo",
                                        "",
                                        "",
                                        getInteresesUsuario(),
                                        "",
                                        "",
                                        user?.displayName.toString(),
                                        arrayOf("Usuario"),
                                        user?.phoneNumber.toString()
                                    )

                                    val usuarioLiveData =
                                        usuariosModel?.let { addUsuarioViewModel.agregarUsuario(it) }

                                    addUsuarioViewModel.usuarioAddedLiveData.observe(viewLifecycleOwner) { newUsuario ->

                                        if (newUsuario != null) {
                                            Log.e("Nuevo Usuario Logueado", newUsuario.toString())
                                        }
                                    }
                                }


                                emailInput.setError("No se encontró un usuario registrado con ese correo")


                            } else {
                                viewModelEditarUsuario.updatePerfilUsuario(
                                    getReferenceUsuarioUpdate(),
                                    user?.displayName.toString(),
                                    user?.email.toString(),
                                    "",
                                    "",
                                    "",
                                    user?.phoneNumber.toString()
                                )

                                updateUser(user)

                            }
                        }
                    }


                    emailInput.setError(null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    emailInput.setError("Ha ocurrido un error. Intenta de nuevo")
                    updateUser(null)
                }
            }
    }
    // [END auth_with_google]

    private fun firebaseAuthentication(email: String, password: String) {
        loadingCircle.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                Log.d("Firebase email: ", email)
                Log.d("Firebase password: ", password)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser

                    emailInput.setError(null)

                    updateUser(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    emailInput.setError("Ha ocurrido un error. Intenta de nuevo")
                    updateUser(null)
                }
            }
    }

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun getReferenceUsuario(): String {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("usuario", "")
        return referenceUsuario.toString()
    }

    private fun getReferenceUsuarioUpdate(): String {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("referenceUsuario", "")
        return referenceUsuario.toString()
    }

    private fun getInteresesUsuario(): String {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val interesesUsuario = sharedPref.getString("idInteresesGenerales", "")
        return interesesUsuario.toString()
    }

    private fun getInteresesIniciados(): String {
        val sharedPref =
            requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val interesesIniciados = sharedPref.getString("interesesIniciados", "")
        return interesesIniciados.toString()
    }


    private fun updateReference(reference: String, user: UsuariosModel?) {
        val gson = Gson()
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        Log.d("Login update reference", gson.toJson(user))
        editor.putString(reference, gson.toJson(user))
        editor.apply()
    }

    private fun updateInteresesIniciados() {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("interesesIniciados", "false")
        editor.apply()
    }

    private fun updateUI(user: UsuariosModel) {
        Log.d("UI user", user.toString())
        if (user !== null) {

            Log.d("BD logged user", user.toString())
            val userStream = User(
                id = user.correoElectronico.replace(".", ""),
                name = user.nombre,
                image = R.drawable.ic_arrow_back_white.toString(),
            )
            val token = client?.devToken(userStream.id)

            if (client != null) {
                if (token != null) {
                    client.connectUser(userStream, token).enqueue()
                }
            }
            updateReference("usuario", user)
            loadingCircle.visibility = View.GONE
            mainViewModel.navigateTo(NavigationScreen.Main)
        } else {
            loadingCircle.visibility = View.GONE
        }

    }

    private fun updateUser(user: FirebaseUser?) {
        if (user !== null) {
            getPerfilUsuarioByEmailViewModel.getUsuarioByEmail(user?.email.toString())
        }
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}