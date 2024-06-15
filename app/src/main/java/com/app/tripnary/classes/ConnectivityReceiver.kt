package com.app.tripnary.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.tripnary.R
import com.app.tripnary.ui.planesviajes.perfilplanviaje.PerfilGeneralViajeFragment

class ConnectivityReceiver : BroadcastReceiver() {
    private var listener: ConnectivityListener? = null

    fun setListener(listener: ConnectivityListener) {
        this.listener = listener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = isInternetConnected(context)
            Log.d("ConnectivityReceiver", "Internet connected: $isConnected")

            // Obtiene el fragmento actual desde el contexto
            val fragment = getFragmentFromContext(context)

            listener?.onInternetStatusChanged(fragment, isConnected)
        }
    }
    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    private fun isInternetConnectedLegacy(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun getFragmentFromContext(context: Context): Fragment? {
        if (context is FragmentActivity) {

            return context.supportFragmentManager.findFragmentById(R.id.fragment_container)
        }
        return null
    }

}