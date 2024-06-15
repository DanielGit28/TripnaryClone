package com.app.tripnary.ui.documentos.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.databinding.ItemDocumentoBinding
import com.app.tripnary.domain.models.DocumentosModel
import com.app.tripnary.ui.documentos.views.ListaDocumentosFragment

class DocumentosPlanViajeAdapter(private val context: ListaDocumentosFragment, val onClickListener: DocumentosListClickListener):RecyclerView.Adapter<DocumentosPlanViajeAdapter.DocumentoItemViewHolder>() {
    private val data = mutableListOf<DocumentosModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocumentosPlanViajeAdapter.DocumentoItemViewHolder {
        return DocumentoItemViewHolder(
            ItemDocumentoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun setData(dataSource: List<DocumentosModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DocumentoItemViewHolder, position: Int) {
        holder.bind(data[position], position)

    }

    inner class DocumentoItemViewHolder(val binding: ItemDocumentoBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var documento: DocumentosModel
        private var documentoPosition: Int = 0
        val documentoNombre = binding.textDocumentoNombre
        val cardView = binding.root

        fun bind(documento: DocumentosModel, position: Int){
            this.documento = documento
            documentoPosition = position

            if (!TextUtils.isEmpty(documento.reference)) {
                documentoNombre.text = documento.nombre
            }
            cardView.setOnClickListener {
                onClickListener.onDocumentosClickListener(documento, position)
            }
        }
    }
    interface DocumentosListClickListener {
        fun onDocumentosClickListener(documento: DocumentosModel, position: Int)
    }
}