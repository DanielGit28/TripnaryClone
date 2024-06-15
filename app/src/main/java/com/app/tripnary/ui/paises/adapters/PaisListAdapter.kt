package com.app.tripnary.ui.paises.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaPaisesBinding
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.ui.paises.ListaPaisesFragment
import com.app.tripnary.ui.tasks.TaskListFragment
import com.app.tripnary.ui.tasks.adapters.TaskListAdapter
import com.squareup.picasso.Picasso


class PaisListAdapter (
    context: ListaPaisesFragment, val onClickListener: PaisClickListener
)
    :  RecyclerView.Adapter<PaisListAdapter.PaisItemViewHolder>() {


    private val data = mutableListOf<PaisModel>()

    fun setData(dataSource: List<PaisModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }


    interface PaisClickListener {
        fun onPaisClickListener(pais: PaisModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PaisListAdapter.PaisItemViewHolder {
        return PaisItemViewHolder(
            ItemListaPaisesBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(paisItemViewHolder: PaisItemViewHolder, position: Int) {
        paisItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class PaisItemViewHolder(private val binding: ItemListaPaisesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var pais: PaisModel
        private var paisPosition: Int = 0
        private val paisNombre = binding.textPaisNombre
        private val paisImagen = binding.imagePais
        private val paisDescripcion = binding.textPaisDescripcion

        private val container = binding.root

        fun bind(pais: PaisModel, position: Int) {
            this.pais = pais
            paisPosition = position

            if (!TextUtils.isEmpty(pais.reference)) {
                paisNombre.text = pais.nombre
                paisDescripcion.text = pais.descripcion


                Picasso.get()
                    .load(pais.imagen) // Reemplaza "pais.imageUrl" por la URL de la imagen
                    .into(paisImagen)

            }

            container.setOnClickListener {
                onClickListener.onPaisClickListener(pais, position)
            }
        }
    }
}