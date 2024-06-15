package com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaLugaresPerfilGeneralBinding
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.ui.paises.ListaPaisesFragment
import com.app.tripnary.ui.planesviajes.perfilplanviaje.PerfilGeneralViajeFragment
import com.squareup.picasso.Picasso

class LugarRecomendadoListPerfilAdapter (
    context: PerfilGeneralViajeFragment, val onClickListener: LugarRecomendadoClickListener
)
    :  RecyclerView.Adapter<LugarRecomendadoListPerfilAdapter.LugarItemViewHolder>() {


    private val data = mutableListOf<LugaresRecomendadosModel>()

    private val dataLugaresPlanes = mutableListOf<LugarPlanModel>()

    fun setData(dataSource: List<LugaresRecomendadosModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    fun setDataPlanes(dataSource: List<LugarPlanModel>) {
        dataLugaresPlanes.clear()
        dataLugaresPlanes.addAll(dataSource)

    }

    interface LugarRecomendadoClickListener {
        fun onLugarRecomendadoClickListener(lugar: LugaresRecomendadosModel, lugarPlanModel: LugarPlanModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LugarRecomendadoListPerfilAdapter.LugarItemViewHolder {
        return LugarItemViewHolder(
            ItemListaLugaresPerfilGeneralBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(lugarItemViewHolder: LugarItemViewHolder, position: Int) {
        val itemAsociadoBuscado = dataLugaresPlanes[position].idLugarRecomendado

        val lugarRecomendado = data.find { lugar -> lugar.reference == itemAsociadoBuscado }

        lugarRecomendado?.let {
            lugarItemViewHolder.bind(it, dataLugaresPlanes[position], position)
        }
    }



    override fun getItemCount(): Int {
        return data.size
    }
    inner class LugarItemViewHolder(private val binding: ItemListaLugaresPerfilGeneralBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var lugar: LugaresRecomendadosModel
        private var lugarPosition: Int = 0
        private val lugarNombre = binding.textLugarRecomendadoPerfilTitle
        private val lugarImagen = binding.lugarRecomendadoPerfilImage
        private val lugarFecha = binding.textLugarRecomendadoPerfilDate

        private val container = binding.root

        fun bind(lugar: LugaresRecomendadosModel, lugarPlanModel: LugarPlanModel, position: Int) {
            this.lugar = lugar
            lugarPosition = position

            if (!TextUtils.isEmpty(lugar.reference)) {
                lugarNombre.text = lugar.nombre

                if (lugarPlanModel.horaInicio == "null") {
                    lugarFecha.text = "hh:mm" +  " - " + "hh:mm"
                } else {
                    lugarFecha.text = lugarPlanModel.horaInicio +  " - " + lugarPlanModel.horaFin
                }

                Picasso.get()
                    .load(lugar.imagen)
                    .into(lugarImagen)

            }

            container.setOnClickListener {
                onClickListener.onLugarRecomendadoClickListener(lugar, lugarPlanModel, position)
            }
        }
    }
}