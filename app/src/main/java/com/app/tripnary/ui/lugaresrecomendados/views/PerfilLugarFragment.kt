package com.app.tripnary.ui.lugaresrecomendados.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseLugaresRecomendadosDataSource
import com.app.tripnary.data.repositories.LugaresRecomendadosRepositoryImpl
import com.app.tripnary.domain.usecases.GetLugaresRecomendadosByIdUseCase
import com.app.tripnary.ui.lugaresrecomendados.viewmodels.PerfilLugarViewModel
import com.app.tripnary.ui.lugaresrecomendados.viewmodels.factories.PerfilLugarViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PerfilLugarFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private val lugaresRecomendadosDao by lazy { AppDatabase.getInstance(requireContext()).getLugaresRecomendadosDao() }
    private val lugaresRecomendadosDataSource by lazy { DatabaseLugaresRecomendadosDataSource(lugaresRecomendadosDao) }
    private lateinit var viewModelPerfilLugaresRecomendados : PerfilLugarViewModel

    private val getPerfilLugarViewModelFactory : PerfilLugarViewModelFactory by lazy {
        PerfilLugarViewModelFactory(GetLugaresRecomendadosByIdUseCase(LugaresRecomendadosRepositoryImpl(lugaresRecomendadosDataSource)))
    }

    private lateinit var lugarImageView: ImageView
    private lateinit var nombreTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var categoriaTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var temporadaTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_lugar, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModelPerfilLugaresRecomendados = ViewModelProvider(this, getPerfilLugarViewModelFactory)[PerfilLugarViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val referenceLugar = getReferenceLugar()
        if (referenceLugar != null){
            viewModelPerfilLugaresRecomendados.getLugarRecomendadoById(referenceLugar)

        }
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            mainViewModel.navigateTo(NavigationScreen.Main)
        }
        initViews(view)
    }

    private fun initViews(view: View) {
        with(view) {
            nombreTextView = findViewById(R.id.text_product_title)
            ratingTextView = findViewById(R.id.text_rating)
            categoriaTextView = findViewById(R.id.text_categoria)
            descripcionTextView = findViewById(R.id.text_short_description)
            temporadaTextView = findViewById(R.id.text_short_temporada)
            lugarImageView = findViewById(R.id.lugar_recomendado_image)
            viewModelPerfilLugaresRecomendados.lugarLiveData.observe(viewLifecycleOwner) { lugar ->
                nombreTextView.setText(lugar.nombre)
                ratingTextView.setText(lugar.puntuacion)
                categoriaTextView.setText(lugar.categoriaViaje)
                descripcionTextView.setText(lugar.descripcion)
                temporadaTextView.setText(lugar.temporada)

                Glide.with(lugarImageView)
                    .load(lugar.imagen)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(lugarImageView)
            }

        }
    }

    private fun getReferenceLugar(): String? {
        val sharedPref = requireContext().getSharedPreferences("TripnaryPreferences", Context.MODE_PRIVATE)
        val referenceLugar = sharedPref.getString("referenceLugar", "")
        return referenceLugar
    }
}