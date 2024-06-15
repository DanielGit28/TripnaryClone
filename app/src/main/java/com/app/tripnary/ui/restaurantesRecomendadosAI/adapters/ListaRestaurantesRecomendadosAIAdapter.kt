package com.app.tripnary.ui.restaurantesRecomendadosAI.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemLugarRecomendadoAiBinding
import com.app.tripnary.databinding.ItemRestauranteRecomendadoAiBinding
import com.app.tripnary.domain.models.LugarRecomendadoAIModel
import com.app.tripnary.domain.models.RestauranteRecomendadoAIModel
import com.app.tripnary.ui.lugaresRecomendadosAI.ListaLugaresRecomendadosAIFragment
import com.app.tripnary.ui.lugaresRecomendadosAI.adapters.LugarRecomendadoAIAdapter
import com.app.tripnary.ui.restaurantesRecomendadosAI.ListaRestaurantesRecomendadosAIFragment

class ListaRestaurantesRecomendadosAIAdapter (private val context: ListaRestaurantesRecomendadosAIFragment) : RecyclerView.Adapter<ListaRestaurantesRecomendadosAIAdapter.RestaurantesRecomendadosAIItemViewHolder>() {
    private val data = mutableListOf<RestauranteRecomendadoAIModel>()

    fun setData(dataSource: List<RestauranteRecomendadoAIModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaRestaurantesRecomendadosAIAdapter.RestaurantesRecomendadosAIItemViewHolder {
        return RestaurantesRecomendadosAIItemViewHolder(
            ItemRestauranteRecomendadoAiBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ListaRestaurantesRecomendadosAIAdapter.RestaurantesRecomendadosAIItemViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RestaurantesRecomendadosAIItemViewHolder(private val binding: ItemRestauranteRecomendadoAiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var restauranteRecomendadoAI: RestauranteRecomendadoAIModel

        private val nombre = binding.textRecomendacionRestauranteAi
        private val direccion = binding.textRecomendacionRestauranteAiDireccion
        private val horario = binding.textRecomendacionRestauranteAiHorario
        private val rating = binding.textRecomendacionRestauranteAiRating
        private val dia = binding.textRecomendacionRestauranteAiDia
        private val comida = binding.textRecomendacionRestauranteAiComida

        private val container = binding.root

        fun bind(restauranteAI: RestauranteRecomendadoAIModel, position: Int) {
            this.restauranteRecomendadoAI = restauranteAI

            nombre.text = restauranteAI.nombre
            direccion.text = restauranteAI.direccion
            horario.text = restauranteAI.horario
            rating.text = "Rating: ${restauranteAI.rating}"
            comida.text = restauranteAI.comida
            dia.text = "Día ${restauranteAI.dia}"
        }
    }


}