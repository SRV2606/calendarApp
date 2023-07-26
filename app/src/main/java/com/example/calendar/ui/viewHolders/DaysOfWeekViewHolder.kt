package com.example.calendar.ui.viewHolders

import android.content.Context
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.LayoutDaysOfWeekBinding

class DaysOfWeekViewHolder(
    private val binding: LayoutDaysOfWeekBinding,
    private val context: Context
) : BaseViewHolder<String>(binding) {


    override fun setItem(data: String?, itemClickListener: ((String) -> Unit)?) {
        binding.dayOfWeekTextView.text = data
    }
}