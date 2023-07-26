package com.example.calendar.ui

import android.content.Context
import android.util.Log
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.LayoutDaysOfWeekBinding

class DaysOfWeekViewHolder(
    private val binding: LayoutDaysOfWeekBinding,
    private val context: Context
) : BaseViewHolder<String>(binding) {


    override fun setItem(data: String?, itemClickListener: ((String) -> Unit)?) {
        Log.d("SHAW_TAG", "setItem: " + data)
        binding.dayOfWeekTextView.text = data
    }
}