package com.app.tripnary.ui.listaDeseos.adapters

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.ui.listaDeseos.ListaDeseosFragment
import com.app.tripnary.databinding.ItemListaDeseosBinding
import com.app.tripnary.domain.models.ListaDeseosModel
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.squareup.picasso.Picasso

class ListaDeseosAdapter(context: ListaDeseosFragment,
                         val onClickListener: ListaDeseosClickListener,
                         val onEliminarListaDeseosClickListener: EliminarListaDeseosClickListener,
                         val onAgregarListaDeseosClickListener: AgregarListaDeseosClickListener)
    : RecyclerView.Adapter<ListaDeseosAdapter.ListaDeseosItemViewHolder>(){

    private lateinit var mainViewModel: MainViewModel

    private val data = mutableListOf<ListaDeseosModel>()

    fun setData(dataSource: List<ListaDeseosModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }


    interface ListaDeseosClickListener {
        fun onListaDeseosClickListener(listaDeseos: ListaDeseosModel, position: Int)
    }

    interface EliminarListaDeseosClickListener {
        fun onEliminarListaDeseosClickListener(reference: String)
    }

    interface AgregarListaDeseosClickListener {
        fun onAgregarListaDeseosClickListener(idLugar: String)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListaDeseosItemViewHolder {
        return ListaDeseosItemViewHolder(
            ItemListaDeseosBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(listaDeseosItemViewHolder: ListaDeseosItemViewHolder, position: Int) {
        listaDeseosItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {

        return data.size
    }
    inner class ListaDeseosItemViewHolder(private val binding: ItemListaDeseosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var listaDeseos: ListaDeseosModel
        private var listaDeseosPosition: Int = 0
        private val listaDeseosNombre = binding.textDeseoNombre
        private val listaDeseosImagen = binding.imageDeseo
        private val listaDeseosDescripcion = binding.textDeseoDescripcion
        private val listaDeseosCategoriaViaje = binding.textDeseoCategoriaViaje

        private val container = binding.root

        private val eliminarListaDeseo = binding.imageTrashIcon
        private val agregarListaDeseosPlanViaje = binding.imagePlusIcon

        fun bind(listaDeseos: ListaDeseosModel, position: Int) {
            this.listaDeseos = listaDeseos
            listaDeseosPosition = position

            if (!TextUtils.isEmpty(listaDeseos.reference)) {
                listaDeseosNombre.text = listaDeseos.nombre
                listaDeseosDescripcion.text = listaDeseos.descripcion
                listaDeseosCategoriaViaje.text = listaDeseos.categoriaViaje

                Picasso.get()
                    .load(listaDeseos.imagen)
                    .into(listaDeseosImagen)

            }

            container.setOnClickListener {
                onClickListener.onListaDeseosClickListener(listaDeseos, position)
            }

            eliminarListaDeseo.setOnClickListener{
                onEliminarListaDeseosClickListener.onEliminarListaDeseosClickListener(listaDeseos.reference)
            }

            agregarListaDeseosPlanViaje.setOnClickListener{
                onAgregarListaDeseosClickListener.onAgregarListaDeseosClickListener(listaDeseos.idLugar)
            }
        }
    }

}