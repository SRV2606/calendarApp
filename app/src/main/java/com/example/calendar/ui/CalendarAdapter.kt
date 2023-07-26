package com.example.calendar.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.LayoutCalendarDayBinding
import com.example.domain.models.CalendarDay

class CalendarAdapter(
    private val itemClickListener: (CalendarDay) -> Unit,
    private val context: Context
) : ListAdapter<CalendarDay, BaseViewHolder<*>>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CalendarDay>() {
            override fun areItemsTheSame(
                oldItem: CalendarDay,
                newItem: CalendarDay
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CalendarDay,
                newItem: CalendarDay
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CalendarViewHolder(
            LayoutCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as CalendarViewHolder).setItem(
            currentList[position], itemClickListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }
}