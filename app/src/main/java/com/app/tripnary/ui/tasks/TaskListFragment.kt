package com.app.tripnary.ui.tasks

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.R
import com.app.tripnary.data.database.database.AppDatabase
import com.app.tripnary.data.datasources.DatabaseTaskDataSource
import com.app.tripnary.data.repositories.TaskRepositoryImpl
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.domain.usecases.GetListUseCase
import com.app.tripnary.ui.main.viewmodels.MainViewModel
import com.app.tripnary.ui.main.viewmodels.NavigationScreen
import com.app.tripnary.ui.tasks.adapters.TaskListAdapter
import com.app.tripnary.ui.tasks.viewmodels.TaskListViewModel
import com.app.tripnary.ui.main.factories.TaskListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(), TaskListAdapter.TaskClickListener {


    private val taskDao by lazy { AppDatabase.getInstance(requireContext()).getTaskDao() }
    private val taskDataSource by lazy { DatabaseTaskDataSource(taskDao) }


    private val repository by lazy { TaskRepositoryImpl(taskDataSource = taskDataSource) }

    private val getTaskListUseCase by lazy { GetListUseCase(repository) }

    private lateinit var fabButton: FloatingActionButton



    private val viewModelFactory by lazy {
        TaskListViewModelFactory(
            getTaskListUseCase
        )
    }

    private lateinit var viewModel: TaskListViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var taskRecyclerView: RecyclerView

    var adapter = TaskListAdapter(this, this)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        initViews(view)
        viewModel = ViewModelProvider(this, viewModelFactory)[TaskListViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        observe()
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            taskRecyclerView = findViewById(R.id.task_list)
            taskRecyclerView.adapter = adapter
            taskRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
            taskRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            fabButton = findViewById(R.id.floatingActionButton)
            fabButton.setOnClickListener { mainViewModel.navigateTo(NavigationScreen.AddTask) }

        }
    }

    private fun observe() {
        viewModel.taskListLiveData.observe(viewLifecycleOwner) { list ->
            adapter.setData(list)
            if (list.isEmpty()) {
                taskRecyclerView.visibility = View.VISIBLE
                taskRecyclerView.visibility = View.GONE
            } else {
                taskRecyclerView.visibility = View.GONE
                taskRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewReady()
    }

    override fun onTaskClickListener(item: TaskModel, position: Int) {
        AlertDialog.Builder(context)
            .setTitle(R.string.remove_note_confirm_title)
            .setMessage(getString(R.string.remove_note_confirm_message, item.reference))
            .setPositiveButton(R.string.confirm_alert_positive_action) { _, _ ->

            }
            .setNegativeButton(R.string.confirm_alert_negative_action, null)
            .show()
    }

}