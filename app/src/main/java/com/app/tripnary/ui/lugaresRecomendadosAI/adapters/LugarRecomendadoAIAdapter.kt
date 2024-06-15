package com.app.tripnary.ui.lugaresRecomendadosAI.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemLugarRecomendadoAiBinding

import com.app.tripnary.domain.models.LugarRecomendadoAIModel
import com.app.tripnary.ui.lugaresRecomendadosAI.ListaLugaresRecomendadosAIFragment


class LugarRecomendadoAIAdapter (private val context: ListaLugaresRecomendadosAIFragment) : RecyclerView.Adapter<LugarRecomendadoAIAdapter.LugarRecomendadoAIItemViewHolder>() {
    private val data = mutableListOf<LugarRecomendadoAIModel>()

    fun setData(dataSource: List<LugarRecomendadoAIModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LugarRecomendadoAIAdapter.LugarRecomendadoAIItemViewHolder {
        return LugarRecomendadoAIItemViewHolder(
            ItemLugarRecomendadoAiBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: LugarRecomendadoAIAdapter.LugarRecomendadoAIItemViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class LugarRecomendadoAIItemViewHolder(private val binding: ItemLugarRecomendadoAiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var lugarRecomendadoAI: LugarRecomendadoAIModel

        private val nombre = binding.textRecomendacionAi
        private val direccion = binding.textRecomendacionAiDireccion
        private val horario = binding.textRecomendacionAiHorario
        private val precio = binding.textRecomendacionAiPrecio
        private val dia = binding.textRecomendacionAiDia

        private val container = binding.root

        fun bind(lugarAI: LugarRecomendadoAIModel, position: Int) {
            this.lugarRecomendadoAI = lugarAI

            nombre.text = lugarAI.nombre
            direccion.text = lugarAI.direccion
            horario.text = lugarAI.horario
            precio.text = lugarAI.precio
            dia.text = "Día ${lugarAI.dia}"
        }
    }


}
