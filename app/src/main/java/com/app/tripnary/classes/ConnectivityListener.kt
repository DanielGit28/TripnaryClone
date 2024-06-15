package com.app.tripnary.classes

import androidx.fragment.app.Fragment

interface ConnectivityListener {

    fun onInternetStatusChanged(fragment: Fragment?, isConnected: Boolean)
}