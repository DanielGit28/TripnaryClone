package com.app.tripnary.ui.lugares.mapas

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import java.util.Locale


class MapsLugaresFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var mainViewModel: MainViewModel
    private lateinit var textNombre: TextView
    private lateinit var textCoordenadas: TextView
    private lateinit var lugarImagen: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_maps_lugares, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it

            val uiSettings = googleMap.uiSettings
            uiSettings.isZoomControlsEnabled = true

            val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
            val latitud = sharedPref.getString("latitudLugarMap", "")
            val longitud = sharedPref.getString("longitudLugarMap", "")

            val markerPosition = latitud?.let { it1 -> longitud?.let { it2 -> LatLng(it1.toDouble(), it2.toDouble()) } }
            val markerOptions = markerPosition?.let { it1 ->
                MarkerOptions()
                    .position(it1)
                    .title("Example Marker")
            }
            googleMap.addMarker(markerOptions)

            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(markerPosition?.latitude ?: 0.0, markerPosition?.longitude ?: 0.0, 1)

            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses?.get(0)
                    val placeDescription = address?.getAddressLine(0)
                    textCoordenadas.text = placeDescription
                }
            }

            // Move the camera to the marker position and set an appropriate zoom level
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 14f))
        }
    }

    private fun initViews(view: View) {
        with(view) {

            val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
            val nombre = sharedPref.getString("nombreLugarMap", "")
            val latitud = sharedPref.getString("latitudLugarMap", "")
            val longitud = sharedPref.getString("longitudLugarMap", "")
            val imagen = sharedPref.getString("imagenLugarMap", "")

            textNombre = findViewById(R.id.text_nombre_lugar_title)
            lugarImagen = findViewById(R.id.lugar_perfil_image)
            textCoordenadas = findViewById(R.id.text_lugar_coordenadas)

            textNombre.setText(nombre)

            Picasso.get()
                .load(imagen)
                .into(lugarImagen)

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuLugar)
            }



        }
    }


}