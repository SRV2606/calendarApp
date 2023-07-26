package com.example.calendar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mappers.CalendarMapper
import com.example.domain.models.ApiError
import com.example.domain.models.CalendarDay
import com.example.domain.models.ClientResult
import com.example.domain.usecases.GetCalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarUseCase,
    private val calendarMapper: CalendarMapper
) :
    ViewModel() {

    private val _calendarDaysLiveData: MutableLiveData<List<CalendarDay>> = MutableLiveData()
    val calendarDaysLiveData: LiveData<List<CalendarDay>> get() = _calendarDaysLiveData

    private val _storeTask: MutableStateFlow<ClientResult<Unit>> =
        MutableStateFlow(ClientResult.InProgress)
    val storeTask = _storeTask.asStateFlow()

    fun fetchCalendarData(selectedYear: Int, selectedMonth: Int) {
        val calendarDaysList = calculateCalendarDays(selectedYear, selectedMonth)
        _calendarDaysLiveData.value = calendarDaysList
    }

    private fun calculateCalendarDays(year: Int, month: Int): List<CalendarDay> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)

        val calendarDaysList = mutableListOf<CalendarDay>()

        // Calculate the number of days from the previous month to be shown
        val daysFromPrevMonth =
            (firstDayOfMonth - Calendar.SUNDAY + 7) % 7 // Adjust to start from Monday (Sunday=1, Monday=2, ..., Saturday=7)

        // Handle empty cells from the previous month
        calendar.add(Calendar.DAY_OF_MONTH, -daysFromPrevMonth)
        for (i in 1..daysFromPrevMonth) {
            val date = calendar.time
            // Set isCurrentMonth to false as it belongs to the previous month
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Handle days of the current month
        for (i in 1..daysInMonth) {
            val date = calendar.time
            // Set isCurrentMonth to true as it belongs to the current month
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = true))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Calculate the number of days from the next month to be shown
        val daysFromNextMonth = 35 - calendarDaysList.size

        // Handle empty cells from the next month
        for (i in 1..daysFromNextMonth) {
            val date = calendar.time
            // Set isCurrentMonth to false as it belongs to the next month
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendarDaysList
    }

    fun storeCalendarTask(calendarDay: CalendarDay) {
        viewModelScope.launch {

            val taskBean = calendarMapper.toTaskBean(calendarDay)

            when (val req = getCalendarUseCase.storeCalendarTask(userID = 9001, taskBean)) {
                is ClientResult.Success -> {
                    req.data.let {
                        _storeTask.emit(ClientResult.Success(req.data))
                        Log.d("SHAW_TAG", "storeCalendarTask: " + it)
                    }
                }

                else -> {
                    _storeTask.emit(ClientResult.Error(ApiError("Failed")))
                }
            }
        }
    }


}
