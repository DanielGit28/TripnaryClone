package com.app.tripnary.ui.lugares.listalugares.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.ItemListaLugaresBinding
import com.app.tripnary.domain.models.LugaresRecomendadosModel
import com.app.tripnary.ui.lugares.listalugares.ListaLugaresRecomendadosFragment
import com.squareup.picasso.Picasso

class ListaLugaresAdapter  (
    context: ListaLugaresRecomendadosFragment, val onClickListener: LugarClickListener
)
    :  RecyclerView.Adapter<ListaLugaresAdapter.LugarItemViewHolder>() {


    private val data = mutableListOf<LugaresRecomendadosModel>()

    fun setData(dataSource: List<LugaresRecomendadosModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }


    interface LugarClickListener {
        fun onLugarClickListener(lugar: LugaresRecomendadosModel, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LugarItemViewHolder {
        return LugarItemViewHolder(
            ItemListaLugaresBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(ciudadItemViewHolder: LugarItemViewHolder, position: Int) {
        ciudadItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class LugarItemViewHolder(private val binding: ItemListaLugaresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var lugar: LugaresRecomendadosModel
        private var lugarPosition: Int = 0
        private val lugarNombre = binding.textLugarNombre
        private val lugarImagen = binding.imageLugar
        private val lugarDescripcion = binding.textLugarDescripcion
        private val buttonAgregarLugar = binding.buttonAgregarLugarLista

        private val container = binding.root

        fun bind(lugar: LugaresRecomendadosModel, position: Int) {
            this.lugar = lugar
            lugarPosition = position

            if (!TextUtils.isEmpty(lugar.reference)) {
                lugarNombre.text = lugar.nombre
                lugarDescripcion.text = lugar.descripcion


                Picasso.get()
                    .load(lugar.imagen)
                    .into(lugarImagen)

            }

            buttonAgregarLugar.setOnClickListener {
                onClickListener.onLugarClickListener(lugar, position)
            }
        }
    }
}