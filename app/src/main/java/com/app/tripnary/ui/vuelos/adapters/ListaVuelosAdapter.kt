package com.app.tripnary.ui.vuelos.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaDeseosBinding
import com.app.tripnary.databinding.ItemListaVuelosBinding
import com.app.tripnary.domain.models.VuelosModel
import com.app.tripnary.ui.listaDeseos.adapters.ListaDeseosAdapter
import com.app.tripnary.ui.vuelos.ListaVuelosFragment
import com.squareup.picasso.Picasso

class ListaVuelosAdapter(val context:ListaVuelosFragment): RecyclerView.Adapter<ListaVuelosAdapter.ListaVuelosItemViewHolder>() {

    private val data = mutableListOf<VuelosModel>()

    fun setData(dataSource: List<VuelosModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListaVuelosAdapter.ListaVuelosItemViewHolder {
        return ListaVuelosItemViewHolder(
            ItemListaVuelosBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(ListaVuelosItemViewHolder: ListaVuelosAdapter.ListaVuelosItemViewHolder, position: Int) {
        ListaVuelosItemViewHolder.bind(data[position], position, context)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    inner class ListaVuelosItemViewHolder(private val binding: ItemListaVuelosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var listaVuelos: VuelosModel
        private var listaVuelosAeropuertoSalidaLlegada = binding.textOrigenDestino
//        private val listaVuelosAeropuertoLlegada = binding.textAeropuertoLlegada
        private val listaVuelosAerolinea = binding.textAerolinea
//        private val listaVuelosfechaSalida = binding.textFechaSalida
//        private val listaVuelosfechaLlegada = binding.textfechaLlegada
        private val listaVuelosDuracion = binding.textDuracion
        private val listaVuelosMoneda = binding.textMoneda
        private val listaVuelosParadas = binding.textEscalas
        private val listaVuelosPrecio = binding.textPrecio
//        private val listaVuelosTipo = binding.textTipo
//        private val listaVuelosCategoria = binding.textCategoria

        private val container = binding.root

        fun bind(listaVuelos: VuelosModel, position: Int, context: ListaVuelosFragment) {
            this.listaVuelos = listaVuelos

            if (!TextUtils.isEmpty(listaVuelos.precio)) {
                listaVuelosAeropuertoSalidaLlegada.text = listaVuelos.paradas.substringBefore("/")
//                listaVuelosAeropuertoLlegada.text = listaVuelos.destino
                listaVuelosAerolinea.text = "Aerolinea: " + listaVuelos.codigoAerolinea
//                listaVuelosfechaSalida.text = listaVuelos.fechaSalida
//                listaVuelosfechaLlegada.text = listaVuelos.fechaLlegada
                listaVuelosDuracion.text = "Duración: " + listaVuelos.duracion
                listaVuelosMoneda.text = listaVuelos.moneda
                listaVuelosParadas.text = "Escalas: " + listaVuelos.paradas.substringAfter("/")
                listaVuelosPrecio.text = listaVuelos.precio
//                listaVuelosTipo.text = listaVuelos.tipoViajero
//                listaVuelosCategoria.text = listaVuelos.categoriaVuela

                listaVuelosAerolinea.setOnClickListener {
                    val url = "https://www.google.com/search?q=${listaVuelos.codigoAerolinea}+airline&oq=${listaVuelos.codigoAerolinea}+airline&aqs=chrome..69i57j69i59.1416j0j7&sourceid=chrome&ie=UTF-8"
//                    val url = "https://www.${listaVuelos.codigoAerolinea}.com"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.context?.startActivity(intent)

                }

                listaVuelosAerolinea.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }




    }
}