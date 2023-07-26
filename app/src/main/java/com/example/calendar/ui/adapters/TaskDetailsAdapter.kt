package com.example.calendar.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.ItemTaskDetailBinding
import com.example.calendar.ui.viewHolders.TaskDetailsViewHolder
import com.example.domain.models.CalendarTasksBean

class TaskDetailsAdapter(
    private val itemClickListener: (CalendarTasksBean.Task) -> Unit,
    private val context: Context
) : ListAdapter<CalendarTasksBean.Task, BaseViewHolder<*>>(DIFF_CALLBACK) {

    private val colors = arrayOf("#FF5733", "#33FF57", "#5733FF", "#FF33A9", "#33FFA9")

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
        giveRandomColorToMeetingCards(position, holder)
        (holder as TaskDetailsViewHolder).setItem(
            currentList[position], itemClickListener
        )
    }

    private fun giveRandomColorToMeetingCards(position: Int, holder: BaseViewHolder<*>) {
        val colorIndex = position % colors.size
        val selectedColor = Color.parseColor(colors[colorIndex])
        holder.itemView.setBackgroundColor(selectedColor)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


}
