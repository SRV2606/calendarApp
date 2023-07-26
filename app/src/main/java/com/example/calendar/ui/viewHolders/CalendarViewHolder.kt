package com.example.calendar.ui.viewHolders

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.calendar.R
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.LayoutCalendarDayBinding
import com.example.calendar.formatDateToDayOfMonth
import com.example.domain.models.CalendarDay

class CalendarViewHolder(
    private val binding: LayoutCalendarDayBinding,
    private val context: Context
) : BaseViewHolder<CalendarDay>(binding) {


    override fun setItem(data: CalendarDay?, itemClickListener: ((CalendarDay) -> Unit)?) {
        val day = formatDateToDayOfMonth(data?.date.toString())
        binding.dayOfWeekTextView.text = day

        data?.let { calendarDay ->
            if (!calendarDay.isCurrentMonth) {
                val color = ContextCompat.getColor(context, R.color.purple_200)
                binding.dayOfWeekTextView.setTextColor(color)

            } else {
                val color = ContextCompat.getColor(context, R.color.black)
                binding.dayOfWeekTextView.setTextColor(color)

                binding.dayOfWeekTextView.setOnClickListener {
                    itemClickListener?.let {
                        it(data)
                    }
                }

            }
        }

    }
}


