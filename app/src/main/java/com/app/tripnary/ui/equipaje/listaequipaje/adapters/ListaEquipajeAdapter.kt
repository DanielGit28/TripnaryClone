package com.app.tripnary.ui.equipaje.listaequipaje.adapters

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.databinding.ItemListaEquipajeBinding
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.ui.equipaje.listaequipaje.ListaEquipajeFragment

class ListaEquipajeAdapter  (
    context: ListaEquipajeFragment, val onClickListener: EquipajeClickListener,
    val menuClickListener: MenuClickListener
)
    :  RecyclerView.Adapter<ListaEquipajeAdapter.EquipajeItemViewHolder>() {


    private val data = mutableListOf<EquipajeModel>()

    fun setData(dataSource: List<EquipajeModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    interface MenuClickListener {
        fun onMenuClick(equipaje: EquipajeModel, position: Int, anchorView: View)
    }

    interface EquipajeClickListener {
        fun onEquipajeClickListener(equipaje: EquipajeModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EquipajeItemViewHolder {
        return EquipajeItemViewHolder(
            ItemListaEquipajeBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(equipajeItemViewHolder: EquipajeItemViewHolder, position: Int) {
        equipajeItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class EquipajeItemViewHolder(private val binding: ItemListaEquipajeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var equipaje: EquipajeModel
        private var equipajePosition: Int = 0

        private val equipajeNombre = binding.textEquipajeLabel
        private val checkmarkButton = binding.btnCheckmark
        private val checkmarkCard = binding.cardCircledCheckbox

        private val originalBackgroundColor = checkmarkCard.cardBackgroundColor.defaultColor
        private val clickedBackgroundColor = Color.parseColor("#FB8500")

        private val container = binding.root

        init {
            checkmarkButton.setOnClickListener { v ->
                menuClickListener.onMenuClick(equipaje, equipajePosition, v)
            }
        }

        fun bind(equipaje: EquipajeModel, position: Int) {
            this.equipaje = equipaje
            equipajePosition = position

            if (!TextUtils.isEmpty(equipaje.reference)) {
                equipajeNombre.text = equipaje.nombre + " (" + equipaje.cantidad + ")"


            }

            if (equipaje.completado) {
                checkmarkCard.setCardBackgroundColor(clickedBackgroundColor)
            } else {
                checkmarkCard.setCardBackgroundColor(originalBackgroundColor)
            }

            container.setOnClickListener {
                onClickListener.onEquipajeClickListener(equipaje, position)
            }
        }

    }
}