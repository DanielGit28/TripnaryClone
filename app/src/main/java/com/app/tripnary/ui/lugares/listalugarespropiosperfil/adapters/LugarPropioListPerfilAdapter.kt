package com.app.tripnary.ui.lugares.listalugarespropiosperfil.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaLugaresPropiosPerfilBinding
import com.app.tripnary.domain.models.LugarPlanModel
import com.app.tripnary.domain.models.LugarPropioModel
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.ui.lugares.listalugaresrecomendadosperfil.adapters.LugarRecomendadoListPerfilAdapter
import com.app.tripnary.ui.planesviajes.perfilplanviaje.PerfilGeneralViajeFragment
import com.squareup.picasso.Picasso

class LugarPropioListPerfilAdapter (
    context: PerfilGeneralViajeFragment, val onClickListener: LugarPropioClickListener
)
    :  RecyclerView.Adapter<LugarPropioListPerfilAdapter.LugarItemViewHolder>() {


    private val data = mutableListOf<LugarPropioModel>()

    private val dataLugaresPlanes = mutableListOf<LugarPlanModel>()

    fun setData(dataSource: List<LugarPropioModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    fun setDataPlanes(dataSource: List<LugarPlanModel>) {
        dataLugaresPlanes.clear()
        dataLugaresPlanes.addAll(dataSource)

    }


    interface LugarPropioClickListener {
        fun onLugarClickListener(lugar: LugarPropioModel, lugarPlanModel: LugarPlanModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LugarPropioListPerfilAdapter.LugarItemViewHolder {
        return LugarItemViewHolder(
            ItemListaLugaresPropiosPerfilBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(lugarItemViewHolder: LugarItemViewHolder, position: Int) {
        val itemAsociadoBuscado = dataLugaresPlanes[position].idLugarPropio
        val lugarPropio = data.find { lugar -> lugar.reference == itemAsociadoBuscado }

        lugarPropio?.let {
            lugarItemViewHolder.bind(it, dataLugaresPlanes[position], position)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }
    inner class LugarItemViewHolder(private val binding: ItemListaLugaresPropiosPerfilBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var lugar: LugarPropioModel
        private var lugarPosition: Int = 0
        private val lugarNombre = binding.textLugarPropioPerfilTitle
        private val lugarImagen = binding.lugarPropioPerfilImage
        private val lugarFecha = binding.textLugarPropioPerfilDate

        private val container = binding.root

        fun bind(lugar: LugarPropioModel, lugarPlanModel: LugarPlanModel, position: Int) {
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
                onClickListener.onLugarClickListener(lugar, lugarPlanModel, position)
            }
        }
    }
}