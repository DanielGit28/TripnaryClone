package com.app.tripnary.ui.planesviajes.listacolaboradores.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemColaboradorBinding
import com.app.tripnary.domain.models.ColaboradoresModel
import com.app.tripnary.ui.planesviajes.listacolaboradores.ColaboradoresListFragment
import com.app.tripnary.ui.planesviajes.listacolaboradores.adapters.models.ColaboradoresItemModel

class ColaboradoresListAdapter(private val context: ColaboradoresListFragment, val onClickListener: ColaboradoresListAdapter.ColaboradoresListClickListener) : RecyclerView.Adapter<ColaboradoresListAdapter.ColaboradoresListItemViewHolder>() {
    private val data = mutableListOf<ColaboradoresModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColaboradoresListAdapter.ColaboradoresListItemViewHolder {
        return ColaboradoresListItemViewHolder(
            ItemColaboradorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun setData(dataSource: List<ColaboradoresModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ColaboradoresListItemViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ColaboradoresListItemViewHolder(val binding: ItemColaboradorBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var colaborador: ColaboradoresModel
        private var colaboradorPosition: Int = 0
        val nombreColaborador = binding.textColaboradorNombre
        val correoColaborador = binding.textColaboradorCorreo
        val rolColaborador = binding.textColaboradorRol
        val cardView = binding.root

        fun bind(colaborador: ColaboradoresModel, position: Int) {
            this.colaborador = colaborador
            colaboradorPosition = position

            if (!TextUtils.isEmpty(colaborador.reference)) {
                nombreColaborador.text = colaborador.nombre
                correoColaborador.text = colaborador.correoElectronico
                rolColaborador.text = colaborador.rol
            }
            cardView.setOnClickListener{
                onClickListener.onColaboradoresClickListener(colaborador, position)
            }
        }
    }
    interface ColaboradoresListClickListener {
        fun onColaboradoresClickListener(colaborador: ColaboradoresModel, position: Int)
    }
}