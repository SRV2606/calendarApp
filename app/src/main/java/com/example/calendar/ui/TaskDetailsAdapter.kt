package com.example.calendar.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.ItemTaskDetailBinding
import com.example.domain.models.CalendarTasksBean

class TaskDetailsAdapter(
    private val itemClickListener: (CalendarTasksBean.Task) -> Unit,
    private val context: Context
) : ListAdapter<CalendarTasksBean.Task, BaseViewHolder<*>>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CalendarTasksBean.Task>() {
            override fun areItemsTheSame(
                oldItem: CalendarTasksBean.Task,
                newItem: CalendarTasksBean.Task
            ): Boolean {
                return oldItem.taskId == newItem.taskId
            }

            override fun areContentsTheSame(
                oldItem: CalendarTasksBean.Task,
                newItem: CalendarTasksBean.Task
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return TaskDetailsViewHolder(
            ItemTaskDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as TaskDetailsViewHolder).setItem(
            currentList[position], itemClickListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }

    override fun getItemCount(): Int {
        Log.d("SHAW_TAG", "getItemCount: " + currentList)
        return currentList.size
    }


}
