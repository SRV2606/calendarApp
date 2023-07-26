package com.example.calendar.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.base.BaseViewHolder
import com.example.calendar.databinding.LayoutDaysOfWeekBinding

class DaysOfWeekAdapter(
    private val itemClickListener: (String) -> Unit,
    private val context: Context
) : ListAdapter<String, BaseViewHolder<*>>(DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return DaysOfWeekViewHolder(
            LayoutDaysOfWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as DaysOfWeekViewHolder).setItem(
            currentList[position], null
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