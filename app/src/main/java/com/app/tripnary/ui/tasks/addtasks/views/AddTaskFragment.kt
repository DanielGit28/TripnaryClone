package com.app.tripnary.ui.tasks.addtasks.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseTaskDataSource
import com.app.tripnary.data.repositories.TaskRepositoryImpl
import com.app.tripnary.domain.usecases.AddTaskUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.tasks.addtasks.viewmodels.AddTaskViewModel
import com.app.tripnary.ui.tasks.addtasks.viewmodels.factories.AddTaskViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {

    private lateinit var tagCell: View

    private lateinit var chDone: CheckBox
    private lateinit var edtId: EditText
    private lateinit var edtText: EditText
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: AddTaskViewModel

    private val taskDao by lazy { AppDatabase.getInstance(requireContext()).getTaskDao() }
    private val taskDataSource by lazy { DatabaseTaskDataSource(taskDao) }
    private val viewModelFactory: AddTaskViewModelFactory by lazy {
        AddTaskViewModelFactory(AddTaskUseCase(TaskRepositoryImpl(taskDataSource)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        initViews(view)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[AddTaskViewModel::class.java]
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            edtId = findViewById(R.id.edt_id)
            edtText = findViewById(R.id.edt_text)
            chDone = findViewById(R.id.edt_done)
            fabAddNote = findViewById(R.id.fab_add_note)
            fabAddNote.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addTask(edtId.text.toString(), edtText.text.toString(), chDone.isChecked)
                }

                mainViewModel.navigateTo(NavigationScreen.TaskList)
            }
        }
    }




}