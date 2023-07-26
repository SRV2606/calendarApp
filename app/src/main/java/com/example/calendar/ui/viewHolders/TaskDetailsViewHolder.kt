package com.example.calendar.ui.viewHolders

import android.content.Context
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.ItemTaskDetailBinding
import com.example.domain.models.CalendarTasksBean

class TaskDetailsViewHolder(
    private val binding: ItemTaskDetailBinding,
    private val context: Context
) : BaseViewHolder<CalendarTasksBean.Task>(binding) {
    override fun setItem(
        data: CalendarTasksBean.Task?,
        itemClickListener: ((CalendarTasksBean.Task) -> Unit)?
    ) {
        binding.titleTV.text = data?.taskDetail?.title
        binding.descriptionTV.text = data?.taskDetail?.description



        binding.deleteCTA.setOnClickListener {
            if (itemClickListener != null) {
                data?.let { it1 -> itemClickListener(it1) }
            }
        }
    }
}