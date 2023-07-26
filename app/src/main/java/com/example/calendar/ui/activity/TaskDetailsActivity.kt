package com.example.calendar.ui.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendar.R
import com.example.calendar.base.BaseActivity
import com.example.calendar.collectEvent
import com.example.calendar.databinding.ActivityTaskDetailsBinding
import com.example.calendar.ui.adapters.TaskDetailsAdapter
import com.example.calendar.viewmodel.TaskDetailsViewModel
import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import com.example.domain.repository.CalendarRepository
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TaskDetailsActivity :
    BaseActivity<ActivityTaskDetailsBinding>(R.layout.activity_task_details) {


    private val taskDetailsViewModel by viewModels<TaskDetailsViewModel>()


    private val selectedDate by lazy {
        intent.getStringExtra(MainActivity.SELECTED_DATE)
    }

    private val taskListAdapter by lazy {
        TaskDetailsAdapter(itemClickListener = {
            taskDetailsViewModel.deleteTask(it)

        }, context = this@TaskDetailsActivity)
    }


    override fun readArguments(extras: Intent) {

    }

    override fun setupUi() {
        binding.taskDateHeadingTV.text = "Selected Date : $selectedDate"
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
                    Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
                }

                is ClientResult.InProgress -> {

                }
            }
        }

        collectEvent(taskDetailsViewModel.deleteTask) {
            when (it) {
                is ClientResult.Success -> {
                    taskDetailsViewModel.getAllTasks(CalendarRepository.USER_ID)
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
        val taskList = data.tasks?.filter {
            it?.taskDetail?.date == selectedDate
        }
        if (taskList != null) {
            taskListAdapter.submitList(taskList.toList())
        }
    }

    override fun setListener() {

    }
}