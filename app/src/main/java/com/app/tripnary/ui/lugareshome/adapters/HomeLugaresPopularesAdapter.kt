package com.app.tripnary.ui.lugareshome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.ui.lugareshome.adapters.models.LugaresItemModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class HomeLugaresPopularesAdapter(private val arrayList: ArrayList<LugaresItemModel>, val onClickListener: LugaresPopularesClickListener):RecyclerView.Adapter<HomeLugaresPopularesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_lugar_popular, parent, false)
        // Returning view
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Returning size of the arrayList
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Getting arrayList data by its position
        val homeLugarPopularItemModel = arrayList[position]

        Glide.with(holder.itemView.context)
            .load(homeLugarPopularItemModel.imagen)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .into(holder.lugarPopularImagen)

        holder.lugarPopularNombre.text = homeLugarPopularItemModel.nombre

        holder.lugarPopularCategoria.text = homeLugarPopularItemModel.categoriaViaje

        holder.lugarPopularRating.text = homeLugarPopularItemModel.puntuacion

        // Setting click listener for the card
        holder.lugarPopular.setOnClickListener {
            onClickListener.onLugaresPopularesClickListener(homeLugarPopularItemModel.reference)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Getting views by id from inflated layout
        val lugarPopularImagen: ImageView = itemView.findViewById(R.id.image_product)
        val lugarPopularNombre: TextView = itemView.findViewById(R.id.text_product_title)
        val lugarPopularRating: TextView = itemView.findViewById(R.id.rating)
        val lugarPopularCategoria: TextView = itemView.findViewById(R.id.text_selling_price)
        val lugarPopular : CardView = itemView.findViewById(R.id.card_product)
    }

    interface LugaresPopularesClickListener {
        fun onLugaresPopularesClickListener(reference: String)
    }
}