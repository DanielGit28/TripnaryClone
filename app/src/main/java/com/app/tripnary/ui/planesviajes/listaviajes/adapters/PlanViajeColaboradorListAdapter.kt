package com.app.tripnary.ui.planesviajes.listaviajes.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaPlanViajesColaboradorBinding
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.ui.planesviajes.listaviajes.PlanViajeListFragment
import com.squareup.picasso.Picasso

class PlanViajeColaboradorListAdapter(context: PlanViajeListFragment, private val onClickListener: PlanViajeColaboradorClickListener) : RecyclerView.Adapter<PlanViajeColaboradorListAdapter.PlanViajeColaboradorItemViewHolder>() {
    private val data = mutableListOf<PlanViajeModel>()

    fun setData(dataSource: List<PlanViajeModel>){
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViajeColaboradorListAdapter.PlanViajeColaboradorItemViewHolder {
        return PlanViajeColaboradorItemViewHolder(
            ItemListaPlanViajesColaboradorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(planViajeColaboradorItemViewHolder: PlanViajeColaboradorItemViewHolder, position: Int) {
        planViajeColaboradorItemViewHolder.bind(data[position], position)
    }

    inner class PlanViajeColaboradorItemViewHolder(private val binding: ItemListaPlanViajesColaboradorBinding) : RecyclerView.ViewHolder(binding.root) {
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
                onClickListener.onPlanViajeColaboradorClickListener(plan, position)
            }
        }
    }

    interface PlanViajeColaboradorClickListener {
        fun onPlanViajeColaboradorClickListener(plan: PlanViajeModel, position: Int)
    }
}