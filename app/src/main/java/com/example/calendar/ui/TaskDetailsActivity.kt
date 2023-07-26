package com.example.calendar.ui

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendar.R
import com.example.calendar.base.BaseActivity
import com.example.calendar.collectEvent
import com.example.calendar.databinding.ActivityTaskDetailsBinding
import com.example.calendar.viewmodel.TaskDetailsViewModel
import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailsActivity :
    BaseActivity<ActivityTaskDetailsBinding>(R.layout.activity_task_details) {


    private val taskDetailsViewModel by viewModels<TaskDetailsViewModel>()

    private var taskList: MutableList<CalendarTasksBean.Task>? = mutableListOf()

    private val selectedDate by lazy {
        intent.getStringExtra("selectedDate")
    }

    private val taskListAdapter by lazy {
        TaskDetailsAdapter(itemClickListener = {
            taskDetailsViewModel.deleteTask(it)

        }, context = this@TaskDetailsActivity)
    }


    override fun readArguments(extras: Intent) {

    }

    override fun setupUi() {
        setupRV()
    }

    private fun setupRV() {
        binding.taskListRV.layoutManager = LinearLayoutManager(this)
        binding.taskListRV.adapter = taskListAdapter

    }

    override fun observeData() {
        collectEvent(taskDetailsViewModel.getAllTasks) {
            when (it) {
                is ClientResult.Success -> {
                    filterByDate(it.data)
                }

                is ClientResult.Error -> {

                }

                is ClientResult.InProgress -> {

                }
            }
        }

        collectEvent(taskDetailsViewModel.deleteTask) {
            when (it) {
                is ClientResult.Success -> {
                    Log.d("SHAW_TAG_DELETE", "observeData: " + it)
                    taskDetailsViewModel.getAllTasks(9001)
                }

                is ClientResult.Error -> {

                }

                else -> {
                    ClientResult.InProgress
                }
            }
        }
    }

    private fun filterByDate(data: CalendarTasksBean) {
        Log.d("SHAW_TAG", "filterByDate: " + selectedDate)
        val taskList = data.tasks?.filter {
            it?.taskDetail?.date == selectedDate
        }
        Log.d("SHAW_TAG", "filterByDate: " + taskList)
        if (taskList != null) {
            taskListAdapter.submitList(taskList.toList())
        }
    }

    override fun setListener() {

    }
}