package com.app.tripnary.ui.equipaje.listaequipaje

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.repositories.EquipajeRepositoryImpl
import com.app.tripnary.domain.models.EquipajeModel
import com.app.tripnary.domain.models.PlanViajeModel
import com.app.tripnary.domain.usecases.AddEquipajeUseCase
import com.app.tripnary.domain.usecases.DeleteEquipajeUseCase
import com.app.tripnary.domain.usecases.GetListEquipajeUseCase
import com.app.tripnary.domain.usecases.UpdateEquipajeUseCase
import com.app.tripnary.ui.equipaje.agregarequipaje.EquipajeViewModel
import com.app.tripnary.ui.equipaje.agregarequipaje.factories.AgregarEquipajeViewModelFactory
import com.app.tripnary.ui.equipaje.listaequipaje.adapters.ListaEquipajeAdapter
import com.app.tripnary.ui.equipaje.listaequipaje.viewmodels.ListaEquipajePlanesViewModel
import com.app.tripnary.ui.equipaje.listaequipaje.viewmodels.factories.ListaEquipajePlanesViewModelFactory
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson


class ListaEquipajeFragment : Fragment(), ListaEquipajeAdapter.EquipajeClickListener, ListaEquipajeAdapter.MenuClickListener {


    private val repositoryEquipaje by lazy { EquipajeRepositoryImpl() }
    private val getListEquipajeUseCase by lazy { GetListEquipajeUseCase(repositoryEquipaje) }
    private val addEquipajeUseCase by lazy { AddEquipajeUseCase(repositoryEquipaje) }
    private val updateEquipajeUseCase by lazy { UpdateEquipajeUseCase(repositoryEquipaje) }
    private val deleteEquipajeUseCase by lazy { DeleteEquipajeUseCase(repositoryEquipaje) }

    private val viewModelFactory by lazy {
        ListaEquipajePlanesViewModelFactory(
            getListEquipajeUseCase
        )

    }

    private val viewModelFactoryAgregar by lazy {
        AgregarEquipajeViewModelFactory(
            addEquipajeUseCase,
            updateEquipajeUseCase,
            deleteEquipajeUseCase
        )

    }

    private lateinit var viewModel: ListaEquipajePlanesViewModel
    private lateinit var viewModelEquipaje: EquipajeViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var equipajeRecyclerView: RecyclerView

    private lateinit var fabButton: FloatingActionButton
    private lateinit var imageNoData: ImageView
    private lateinit var titleNoData: TextView
    private lateinit var infoNoData: TextView
    private lateinit var titleModal: TextView
    private lateinit var titleInternet: TextView
    private lateinit var edtTextNombre: EditText
    private lateinit var edtTextCantidad: EditText
    private lateinit var buttonAgregarEquipaje: Button

    var adapter = ListaEquipajeAdapter(this, this, this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_equipaje, container, false)
        initViews(view)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ListaEquipajePlanesViewModel::class.java]
        viewModelEquipaje =
            ViewModelProvider(this, viewModelFactoryAgregar)[EquipajeViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        observe()
        return view
    }

    private fun initViews(view: View) {
        with(view) {

            equipajeRecyclerView = findViewById(R.id.recycler_view_equipaje_planes)
            equipajeRecyclerView.adapter = adapter

            equipajeRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            fabButton = findViewById(R.id.fab_add_equipaje)
            imageNoData = findViewById(R.id.image_no_data_lista_equipaje)
            titleNoData = findViewById(R.id.title_no_data_lista_equipaje)
            infoNoData = findViewById(R.id.info_no_data_lista_equipaje)
            titleInternet = findViewById(R.id.title_internet_lista_equipaje)
            fabButton.setOnClickListener {

                showInputModal()

            }

            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.setNavigationOnClickListener {
                mainViewModel.navigateTo(NavigationScreen.MenuPlanViaje)
            }


        }
    }

    private fun observe() {
        viewModel.equipajePlanesListLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
            if (list.isEmpty()) {
                imageNoData.visibility = View.VISIBLE
                titleNoData.visibility = View.VISIBLE
                infoNoData.visibility = View.VISIBLE
                equipajeRecyclerView.visibility = View.VISIBLE
                equipajeRecyclerView.visibility = View.GONE
            } else {
                imageNoData.visibility = View.GONE
                titleNoData.visibility = View.GONE
                infoNoData.visibility = View.GONE
                equipajeRecyclerView.visibility = View.GONE
                equipajeRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPlanViaje()?.let { viewModel.onViewReady(it.reference) }



    }

    private fun getPlanViaje(): PlanViajeModel? {
        val sharedPref = requireContext().getSharedPreferences(
            "TripnaryPreferences",
            Context.MODE_PRIVATE
        )

        val planJson = sharedPref.getString("planSelected", null)


        return if (planJson != null) {
            val gson = Gson()
            gson.fromJson(planJson, PlanViajeModel::class.java)
        } else {
            null
        }
    }

    private fun showInputModal() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_agregar_equipaje, null)

        buttonAgregarEquipaje = view.findViewById(R.id.button_agregar_equipaje_modal)
        edtTextNombre = view.findViewById(R.id.edit_text_name_equipaje)
        edtTextCantidad = view.findViewById(R.id.edit_text_cantidad_equipaje)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        buttonAgregarEquipaje.setOnClickListener {

            val equipaje = getPlanViaje()?.let { it1 ->
                EquipajeModel(
                    "", edtTextNombre.text.toString(),
                    edtTextCantidad.text.toString(), it1.reference, false, "Activo"
                )
            }
            val planLiveData =
                equipaje?.let { it1 -> viewModelEquipaje.agregarLugarPlan(it1) }

            viewModelEquipaje.equipajeplanAddedLiveData.observe(viewLifecycleOwner) { newEquipaje ->

                if (newEquipaje != null) {

                    dialog.dismiss()

                    getPlanViaje()?.let { viewModel.onViewReady(it.reference) }
                    observe()

                }
            }


        }

    }


    override fun onEquipajeClickListener(equipaje: EquipajeModel, position: Int) {
        Log.e("Update Equipaje", equipaje.toString())
    }

    override fun onMenuClick(equipaje: EquipajeModel, position: Int, anchorView: View) {
        showPopupMenu(equipaje, position, anchorView)
    }

    private fun showPopupMenu(equipaje: EquipajeModel, position: Int, anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.inflate(R.menu.menu_bottom_item_equipaje)
        val updateMenuItem = popupMenu.menu.findItem(R.id.action_completar_equipaje)

        if (equipaje.completado) {
            updateMenuItem?.title = "Marcar como incompleto"
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_update_equipaje -> {
                    showInputUpdate(equipaje)
                    return@setOnMenuItemClickListener true
                }
                R.id.action_completar_equipaje -> {
                    if (equipaje.completado) {
                        equipaje.completado = false
                        Log.e("Update Equipaje", equipaje.toString())
                        val planLiveData = viewModelEquipaje.updateEquipaje(equipaje)
                        adapter.notifyItemChanged(position)
                    } else   {
                        equipaje.completado = true
                        Log.e("Update Equipaje", equipaje.toString())
                        val planLiveData = viewModelEquipaje.updateEquipaje(equipaje)
                        adapter.notifyItemChanged(position)
                    }

                    return@setOnMenuItemClickListener true
                }
                R.id.action_eliminar_equipaje -> {
                    val planLiveData = viewModelEquipaje.deleteEquipaje(equipaje.reference)

                    viewModelEquipaje.deleteEquipajeLiveData.observe(viewLifecycleOwner) { updateEquipaje ->

                        if (updateEquipaje != null) {

                            getPlanViaje()?.let { viewModel.onViewReady(it.reference) }
                            observe()

                        }
                    }

                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun showInputUpdate(equipaje: EquipajeModel) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_modal_agregar_equipaje, null)

        buttonAgregarEquipaje = view.findViewById(R.id.button_agregar_equipaje_modal)
        edtTextNombre = view.findViewById(R.id.edit_text_name_equipaje)
        edtTextCantidad = view.findViewById(R.id.edit_text_cantidad_equipaje)
        titleModal = view.findViewById(R.id.text_title_equipaje)

        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        if (equipaje != null) {
            edtTextNombre.setText(equipaje.nombre)
            edtTextCantidad.setText(equipaje.cantidad)
            buttonAgregarEquipaje.setText("Actualizar")
            titleModal.setText("Actualice los detalles del equipaje")
        }

        buttonAgregarEquipaje.setOnClickListener {

            equipaje.nombre = edtTextNombre.text.toString()
            equipaje.cantidad = edtTextCantidad.text.toString()

            val planLiveData = viewModelEquipaje.updateEquipaje(equipaje)

            viewModelEquipaje.equipajeUpdateLiveData.observe(viewLifecycleOwner) { updateEquipaje ->

                if (updateEquipaje != null) {
                    dialog.dismiss()

                    getPlanViaje()?.let { viewModel.onViewReady(it.reference) }
                    observe()

                }
            }


        }

    }


}