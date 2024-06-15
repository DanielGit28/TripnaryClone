package com.app.tripnary.ui.mensajes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.databinding.FragmentCanalesBinding
import com.app.tripnary.databinding.FragmentMensajeBinding
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.getstream.sdk.chat.utils.typing.TypingUpdatesBuffer
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.google.gson.Gson
import io.getstream.chat.android.client.ChatClient

import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters

import io.getstream.chat.android.ui.message.composer.viewmodel.MessageComposerViewModel
import io.getstream.chat.android.ui.message.input.MessageInputView

import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory


class MensajeFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel

    private lateinit var headerView: MessageListHeaderView
    private lateinit var listView: MessageListView
    private lateinit var inputView: MessageInputView

    private var backHandler: () -> Unit = {}

    private var _binding: FragmentMensajeBinding? = null
    private val binding get() = _binding!!
    private val inputText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMensajeBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_mensaje, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

       //ChatClient.instance().

        headerView = view.findViewById(R.id.messageListHeaderView)
        listView = view.findViewById(R.id.messageListView)
        inputView = view.findViewById(R.id.messageInputView)

        val cid = "messaging:${getReferenceChannelId()}"
        val request = QueryChannelsRequest(
            filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("cid", cid),
            ),
            offset = 0,
            limit = 10
        ).apply {
            watch = true
            state = true
        }

        ChatClient.instance().queryChannels(request).enqueue { result ->
            if (result.isSuccess) {
                val channels: List<Channel> = result.data()
                Log.d("Canal", result.toString())
            } else {
                Log.d("Canal error", result.error().toString())
            }
        }

        // Step 1 - Create three separate ViewModels for the views so it's easy
        //          to customize them individually
        val factory = MessageListViewModelFactory(cid)
        val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
        val messageListViewModel: MessageListViewModel by viewModels { factory }
        val messageInputViewModel: MessageInputViewModel by viewModels { factory }



        // Step 2 - Bind the view and ViewModels, they are loosely coupled so it's easy to customize
        messageListHeaderViewModel.bindView(headerView, this)
        messageListViewModel.bindView(listView, this)
        messageInputViewModel.bindView(inputView, this)

        // Step 3 - Let both MessageListHeaderView and MessageInputView know when we open a thread
        messageListViewModel.mode.observe(viewLifecycleOwner) { mode ->
            when (mode) {
                is MessageListViewModel.Mode.Thread -> {
                    messageListHeaderViewModel.setActiveThread(mode.parentMessage)
                    messageInputViewModel.setActiveThread(mode.parentMessage)
                }
                MessageListViewModel.Mode.Normal -> {
                    messageListHeaderViewModel.resetThread()
                    messageInputViewModel.resetThread()
                }
            }
        }



        // Step 4 - Let the message input know when we are editing a message
        listView.setMessageEditHandler(messageInputViewModel::postMessageToEdit)


        // Step 5 - Handle navigate up state
        messageListViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state is MessageListViewModel.State.NavigateUp) {
                getActivity()?.supportFragmentManager?.popBackStack();
            }
        }


        // Step 6 - Handle back button behaviour correctly when you're in a thread
        backHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
            eliminateReference("channelId")
            mainViewModel.navigateTo(NavigationScreen.Canales)
        }
        headerView.setBackButtonClickListener {
            backHandler()
        }



        return view
    }

    private fun getReferenceChannelId(): String {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceUsuario = sharedPref.getString("channelId", "")
        return referenceUsuario.toString()
    }

    private fun eliminateReference(reference: String) {
        val gson = Gson()
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.remove(reference).apply()
    }



}

