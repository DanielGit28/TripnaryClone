package com.app.tripnary.ui.mensajes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.databinding.FragmentCanalesBinding
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.UsuariosModel
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.gson.Gson
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.header.ChannelListHeaderView
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory


class CanalesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainList: ChannelListView
    private lateinit var mainListHeader: ChannelListHeaderView
    private  var binding: FragmentCanalesBinding? = null

    private lateinit var edtTextNombre: EditText
    private lateinit var edtTextCorreo: EditText
    private lateinit var buttonCrearChat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanalesBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_canales, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

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
        val client = offlinePluginFactory?.let {
            context?.let { it1 ->
                ChatClient.Builder("q6suwcgr75xw", it1)
                    .withPlugin(it)
                    .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                    .build()
            }
        }

        val usuario = getReferenceUsuario()

        val user = User(
            id = usuario.correoElectronico.replace(".", ""),
            name = usuario.nombre,
            image = R.drawable.ic_arrow_back_white.toString(),
        )
        val token = client?.devToken(user.id)

        if (client != null) {
            if (token != null) {
                client.connectUser(user, token).enqueue()
            }
        }
        //client.createChannel("messaging","nombre-usuario", listOf("user1","user2"),hashMapOf<String, Any>())


        //client.connectUser(user, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiYmVuZGVyIn0.Jn5Pi4DBF-wdjnZdkCTlJNLTip1NughmrGnrIP3Q9yU").enqueue { /* ... */ }

        mainList = view.findViewById(R.id.channelListView)
        mainListHeader = view.findViewById(R.id.channelListHeaderView)

        // Step 4 - Set the channel list filter and order
        // This can be read as requiring only channels whose "type" is "messaging" AND
        // whose "members" include our "user.id"
        val filter = Filters.and(
            Filters.eq("type", "messaging"),
            Filters.`in`("members", listOf(user.id))
        )
        val viewModelFactory = ChannelListViewModelFactory(filter, ChannelListViewModel.DEFAULT_SORT)
        val viewModel: ChannelListViewModel by viewModels { viewModelFactory }

        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()
        // Bind the ViewModel with ChannelListView
        channelListHeaderViewModel.bindView(mainListHeader, this)


        // Step 5 - Connect the ChannelListViewModel to the ChannelListView, loose
        //          coupling makes it easy to customize
        viewModel.bindView(mainList, this)

        mainList.setChannelItemClickListener { channel ->
            savePreference("channelId", channel.id)
            mainViewModel.navigateTo(NavigationScreen.Mensaje)
        }

       mainListHeader.setOnActionButtonClickListener {
            Log.d("Action item", "channel action")
           showInputModal()
        }
        mainListHeader.setOnUserAvatarClickListener{
            mainViewModel.navigateTo(NavigationScreen.Main)
        }

        return view
    }

    private fun getReferenceUsuario(): UsuariosModel {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("usuario", "")
        Log.d("Canales user", referenceUsuario.toString())
        var gson = Gson()
        val usuario = gson.fromJson(referenceUsuario, UsuariosModel::class.java)
        return usuario
    }

    private fun savePreference(preferenceName: String, preference: String) {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(preferenceName, preference)
        editor.apply()
    }

    private fun showInputModal() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_nuevo_chat, null)

        buttonCrearChat = view.findViewById(R.id.button_nuevo_chat)
        edtTextNombre = view.findViewById(R.id.edit_text_name_chat)
        edtTextCorreo = view.findViewById(R.id.edit_text_usuario_chat)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        buttonCrearChat.setOnClickListener {
            val channelClient = ChatClient.instance().createChannel("messaging", edtTextNombre.text.toString().filterNot { it.isWhitespace() }, listOf(getReferenceUsuario().correoElectronico.replace(".", ""), edtTextCorreo.text.toString().replace(".", "")),hashMapOf<String, Any>())
            channelClient.enqueue { result ->
                if(result.isSuccess) {
                    Log.d("Channel created", result.data().id)
                    savePreference("channelId", result.data().id)
                    dialog.dismiss()
                    mainViewModel.navigateTo(NavigationScreen.Mensaje)
                } else {
                    Log.d("Channel creation failed", result.error().toString())
                    edtTextNombre.setError("El canal no se ha podido crear. Intenta de nuevo")
                }

            }

        }

    }


}