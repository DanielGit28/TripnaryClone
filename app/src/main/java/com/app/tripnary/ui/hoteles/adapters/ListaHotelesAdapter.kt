package com.app.tripnary.ui.hoteles.adapters

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaHotelesBinding
import com.app.tripnary.databinding.ItemListaVuelosBinding
import com.app.tripnary.domain.models.HotelesModel
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.ui.hoteles.ListaHotelesFragment
import com.app.tripnary.ui.vuelos.ListaVuelosFragment
import com.app.tripnary.ui.vuelos.adapters.ListaVuelosAdapter
import com.squareup.picasso.Picasso

class ListaHotelesAdapter(val context: ListaHotelesFragment): RecyclerView.Adapter<ListaHotelesAdapter.ListaHotelesItemViewHolder>() {

    private val data = mutableListOf<HotelesModel>()

    fun setData(dataSource: List<HotelesModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListaHotelesAdapter.ListaHotelesItemViewHolder {
        return ListaHotelesItemViewHolder(
            ItemListaHotelesBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(ListaHotelesItemViewHolder: ListaHotelesAdapter.ListaHotelesItemViewHolder, position: Int) {
        ListaHotelesItemViewHolder.bind(data[position], position, context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ListaHotelesItemViewHolder(private val binding: ItemListaHotelesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var listaHoteles: HotelesModel

        private var listaHotelesNombre = binding.textHotelNombre
        private var listaHotelesUbicacion = binding.textHotelUbicacion
        private var listaHotelesCalificacion = binding.textHotelCalificacion
        private var listaHotelesImagen = binding.imageHoteles

        private val container = binding.root

        fun bind(listaHoteles: HotelesModel, position: Int, context: ListaHotelesFragment) {
            this.listaHoteles = listaHoteles

            if (!TextUtils.isEmpty(listaHoteles.nombre)) {
                listaHotelesNombre.text = listaHoteles.nombre
                listaHotelesUbicacion.text = listaHoteles.maps
                listaHotelesCalificacion.text = listaHoteles.puntuacion

                Picasso.get()
                    .load(listaHoteles.imagen)
                    .into(listaHotelesImagen)

                if(listaHoteles.maps == "false"){
                    listaHotelesUbicacion.isVisible = false
                }else{
                    listaHotelesUbicacion.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(listaHoteles.maps)
                        context.context?.startActivity(intent)
                    }
                }


                listaHotelesUbicacion.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }
}