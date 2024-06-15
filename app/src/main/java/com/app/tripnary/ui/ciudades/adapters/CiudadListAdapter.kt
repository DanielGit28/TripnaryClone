package com.app.tripnary.ui.ciudades.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaCiudadesBinding
import com.app.tripnary.domain.models.CiudadModel
import com.app.tripnary.domain.models.PaisModel
import com.app.tripnary.ui.ciudades.ListaCiudadesFragment
import com.squareup.picasso.Picasso

class CiudadListAdapter  (
    context: ListaCiudadesFragment, val onClickListener: CiudadClickListener
)
    :  RecyclerView.Adapter<CiudadListAdapter.CiudadItemViewHolder>() {


    private val data = mutableListOf<CiudadModel>()

    fun setData(dataSource: List<CiudadModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }


    interface CiudadClickListener {
        fun onCiudadClickListener(ciudad: CiudadModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CiudadListAdapter.CiudadItemViewHolder {
        return CiudadItemViewHolder(
            ItemListaCiudadesBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(ciudadItemViewHolder: CiudadItemViewHolder, position: Int) {
        ciudadItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class CiudadItemViewHolder(private val binding: ItemListaCiudadesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var ciudad: CiudadModel
        private var ciudadPosition: Int = 0
        private val ciudadNombre = binding.textCiudadNombre
        private val ciudadImagen = binding.imageCiudad
        private val ciudadDescripcion = binding.textCiudadDescripcion

        private val container = binding.root

        fun bind(ciudad: CiudadModel, position: Int) {
            this.ciudad = ciudad
            ciudadPosition = position

            if (!TextUtils.isEmpty(ciudad.reference)) {
                ciudadNombre.text = ciudad.nombre
                ciudadDescripcion.text = ciudad.descripcion


                Picasso.get()
                    .load(ciudad.imagen)
                    .into(ciudadImagen)

            }

            container.setOnClickListener {
                onClickListener.onCiudadClickListener(ciudad, position)
            }
        }
    }
}