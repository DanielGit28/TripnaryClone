package com.app.tripnary.ui.tasks.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.tripnary.databinding.TaskItemLayoutBinding
import com.app.tripnary.domain.models.TaskModel
import com.app.tripnary.ui.tasks.TaskListFragment

class TaskListAdapter (
    context: TaskListFragment, val onClickListener: TaskClickListener)
    :  RecyclerView.Adapter<TaskListAdapter.TaskItemViewHolder>(){

    private val data = mutableListOf<TaskModel>()

    interface TaskClickListener {
        fun onTaskClickListener(item: TaskModel, position: Int)
    }

    fun setData(dataSource: List<TaskModel>) {
        data.clear()
        data.addAll(dataSource)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskListAdapter.TaskItemViewHolder {
        return TaskItemViewHolder(
            TaskItemLayoutBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(taskItemViewHolder: TaskItemViewHolder, position: Int) {
        taskItemViewHolder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addTask(task: TaskModel) {
        if (data.isNotEmpty()) {
            data.add(task)
            notifyDataSetChanged()
        }
    }

    inner class TaskItemViewHolder(private val binding: TaskItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: TaskModel
        private var itemPosition: Int = 0
        private val itemText= binding.taskItemText
        private val itemId = binding.taskItemId
        private val done = binding.taskItemDone
        private val container = binding.root

        fun bind(item: TaskModel, position: Int) {
            this.item = item
            itemPosition = position

            if (!TextUtils.isEmpty(item.text)) {
                itemText.text = item.text
                itemId.text = item.id
                done.isChecked = item.done
            }

            container.setOnClickListener {
                onClickListener.onTaskClickListener(item, position)
            }
        }
    }

}