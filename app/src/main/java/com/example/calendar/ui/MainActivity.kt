package com.example.calendar.ui


import android.app.DatePickerDialog
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendar.R
import com.example.calendar.base.BaseActivity
import com.example.calendar.collectEvent
import com.example.calendar.databinding.ActivityMainBinding
import com.example.calendar.formatDateToDayOfMonth
import com.example.calendar.viewmodel.MainViewModel
import com.example.domain.models.ClientResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val daysList: MutableList<String> = mutableListOf()

    private val mainViewModel by viewModels<MainViewModel>()


    private val daysOfWeekAdapter by lazy {
        DaysOfWeekAdapter(itemClickListener = {
            // we can open any other view , or bottomsheet or anythign here if needed with data

        }, context = this@MainActivity)
    }

    private val calendarAdapter by lazy {
        CalendarAdapter(itemClickListener = {
            mainViewModel.storeCalendarTask(it)
            startTaskDetailsActivity(formatDateToDayOfMonth(it.date.toString()))
        }, context = this@MainActivity)
    }

    private fun startTaskDetailsActivity(date: String) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra("selectedDate", date)
        startActivity(intent)
    }

    override fun readArguments(extras: Intent) {

    }

    override fun setupUi() {
        setUpDaysOfWeekRV()
        setUpCalendarRV()
    }

    private fun setUpCalendarRV() {
        binding.calendarRV.layoutManager = GridLayoutManager(this, 7)
        binding.calendarRV.adapter = calendarAdapter

    }

    private fun setUpDaysOfWeekRV() {
        binding.daysOfWeekRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.daysOfWeekRV.adapter = daysOfWeekAdapter

        daysList.add("S")
        daysList.add("M")
        daysList.add("T")
        daysList.add("W")
        daysList.add("T")
        daysList.add("F")
        daysList.add("S")
        Log.d("SHAW_TAG", "setUpDaysOfWeekRV: " + daysList.toList())
        daysOfWeekAdapter.submitList(daysList.toList())
    }

    override fun observeData() {
        mainViewModel.calendarDaysLiveData.observe(this) {
            calendarAdapter.submitList(it)
        }

        collectEvent(mainViewModel.storeTask) {
            when (it) {
                is ClientResult.Success -> {
                    Log.d("SHAW_TAG", "observeData: " + it.data)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setListener() {
        binding.selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val datePickerDialog = DatePickerDialog(
            this@MainActivity,
            { _, selectedYear, selectedMonth, _ ->
                binding.yearMonthTV.text = "Selected $selectedYear and $selectedMonth"
                mainViewModel.fetchCalendarData(selectedYear, selectedMonth)

            },
            year,
            month,
            1 // The day doesn't matter, so we set it to 1
        )

        datePickerDialog.show()
    }


}