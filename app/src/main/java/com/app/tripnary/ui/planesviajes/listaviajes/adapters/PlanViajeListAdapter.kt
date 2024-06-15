package com.app.tripnary.ui.planesviajes.listaviajes.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaPlanViajesBinding
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.ui.planesviajes.listaviajes.PlanViajeListFragment
import com.squareup.picasso.Picasso

class PlanViajeListAdapter (
    context: PlanViajeListFragment, val onClickListener: PlanViajeClickListener
)
    :  RecyclerView.Adapter<PlanViajeListAdapter.PlanViajeItemViewHolder>() {

    private val data = mutableListOf<PlanViajeModel>()

    interface PlanViajeClickListener {
        fun onTaskClickListener(plan: PlanViajeModel, position: Int)
    }

    fun setData(dataSource: List<PlanViajeModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PlanViajeListAdapter.PlanViajeItemViewHolder {
        return PlanViajeItemViewHolder(
            ItemListaPlanViajesBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(planViajeItemViewHolder: PlanViajeItemViewHolder, position: Int) {
        planViajeItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addPlanViaje(plan: PlanViajeModel) {
        if (data.isNotEmpty()) {
            data.add(plan)
            notifyDataSetChanged()
        }
    }

    inner class PlanViajeItemViewHolder(private val binding: ItemListaPlanViajesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var plan: PlanViajeModel
        private var planPosition: Int = 0
        private val planImagen= binding.imagePlan
        private val nombrePlan = binding.textProductPlanViaje
        private val pais = binding.textPaisPlan
        private val fechas = binding.textFechasPlanViaje
        private val container = binding.root

        fun bind(plan: PlanViajeModel, position: Int) {
            this.plan = plan
            planPosition = position

            if (!TextUtils.isEmpty(plan.reference)) {

                nombrePlan.text = plan.nombre
                pais.text = plan.idPais
                fechas.text = plan.fechaInicio + " - " + plan.fechaFin

                Picasso.get()
                    .load(plan.imagenPortada)
                    .into(planImagen)


            }

            container.setOnClickListener {
                onClickListener.onTaskClickListener(plan, position)
            }
        }
    }

}