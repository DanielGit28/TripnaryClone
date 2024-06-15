package com.app.tripnary.ui.lugareshome.adapters

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.ui.listaDeseos.adapters.ListaDeseosAdapter
import com.app.tripnary.ui.lugareshome.adapters.models.LugaresItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeLugaresRecomendadosAdapter(private val arrayList: ArrayList<LugaresItemModel>,
                                     val onClickListener: LugaresRecomendadosClickListener,
                                        val onListaDeseosClickListener: ListaDeseosClickListener): RecyclerView.Adapter<HomeLugaresRecomendadosAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_lugar, parent, false)
        // Returning view
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Returning size of the arrayList
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Getting arrayList data by its position
        val homeCategoryItemModel = arrayList[position]

        Glide.with(holder.itemView.context)
            .load(homeCategoryItemModel.imagen)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .into(holder.lugarRecomendadoImagen)

        // Setting text
        holder.lugarRecomendadoNombre.text = homeCategoryItemModel.nombre

        // Setting click listener for the card
        holder.lugarRecomendadoCategoria.setOnClickListener {
            onClickListener.onLugaresRecomendadosClickListener(homeCategoryItemModel.reference)
        }

        holder.imagenFavorito.setOnClickListener{
            onListaDeseosClickListener.onListaDeseosClickListener(homeCategoryItemModel.reference)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Getting views by id from inflated layout
        val lugarRecomendadoImagen: ImageView = itemView.findViewById(R.id.image_category)
        val lugarRecomendadoNombre: TextView = itemView.findViewById(R.id.text_category_name)
        val lugarRecomendadoCategoria: CardView = itemView.findViewById(R.id.card_category)
        val imagenFavorito: ImageView = itemView.findViewById(R.id.image_heart_icon)
    }

    interface LugaresRecomendadosClickListener {
        fun onLugaresRecomendadosClickListener(reference: String)

    }

    interface ListaDeseosClickListener {
        fun onListaDeseosClickListener(reference: String)
    }
}