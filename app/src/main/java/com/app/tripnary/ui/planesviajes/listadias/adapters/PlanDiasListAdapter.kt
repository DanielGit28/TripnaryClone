package com.app.tripnary.ui.planesviajes.listadias.adapters

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaDiasBinding
import com.app.tripnary.domain.models.PlanDiasModel
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.ui.planesviajes.perfilplanviaje.PerfilGeneralViajeFragment



class PlanDiasListAdapter(
    context: PerfilGeneralViajeFragment,val onClickListener: DiasClickListener
)
    : RecyclerView.Adapter<PlanDiasListAdapter.DiasItemViewHolder>() {

    private val data = mutableListOf<PlanDiasModel>()

    private var selectedDayIndex = RecyclerView.NO_POSITION

    interface DiasClickListener {
        fun onDiasClickListener(dia: PlanDiasModel, position: Int)
    }

    fun setData(dataSource: List<PlanDiasModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PlanDiasListAdapter.DiasItemViewHolder {
        return DiasItemViewHolder(
            ItemListaDiasBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(diasItemViewHolder: DiasItemViewHolder, position: Int) {
        diasItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addDias(dia: PlanDiasModel) {
        if (data.isNotEmpty()) {
            data.add(dia)
            notifyDataSetChanged()
        }
    }

    inner class DiasItemViewHolder(private val binding: ItemListaDiasBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var dia: PlanDiasModel
        private var diaPosition: Int = 0
        private val numDia= binding.textDiaName
        private val fechaDia= binding.textDiaFecha
        private val cardDia = binding.cardDiaPerfil
        private val container = binding.root

        private val originalBackgroundColor = cardDia.cardBackgroundColor.defaultColor
        private val clickedBackgroundColor = Color.parseColor("#7FD4F9")


        fun bind(dia: PlanDiasModel, position: Int) {
            this.dia = dia
            diaPosition = position

            if (!TextUtils.isEmpty(dia.reference)) {
                numDia.text = "Día " + dia.dia.toString()
                fechaDia.text = dia.fecha

            }

            if (position == selectedDayIndex) {
                cardDia.setCardBackgroundColor(clickedBackgroundColor)
            } else {
                cardDia.setCardBackgroundColor(originalBackgroundColor)
            }

            container.setOnClickListener {

                selectedDayIndex = position
                notifyDataSetChanged()

                onClickListener.onDiasClickListener(dia, position)
            }
        }

        fun restoreOriginalColor() {
            cardDia.setCardBackgroundColor(originalBackgroundColor)
        }
    }

}