package com.app.tripnary.ui.lugares.agregarlugares.agregarlugaresplanes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class AutoCompleteAdapter(
    context: Context,
    private val placesClient: PlacesClient
) : ArrayAdapter<AutocompletePrediction>(context, android.R.layout.simple_dropdown_item_1line) {

    private val inflater = LayoutInflater.from(context)
    private val predictions = mutableListOf<AutocompletePrediction>()

    override fun getCount(): Int = predictions.size

    override fun getItem(position: Int): AutocompletePrediction? = predictions[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        val prediction = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = prediction?.getFullText(null)
        return view
    }

    fun updatePredictions(predictions: List<AutocompletePrediction>) {
        this.predictions.clear()
        this.predictions.addAll(predictions)
        notifyDataSetChanged()
    }

    // Método para realizar la búsqueda de autocompletado
    fun performAutocomplete(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            updatePredictions(response.autocompletePredictions)
        }.addOnFailureListener { exception ->
            // Manejo de errores
        }
    }
}