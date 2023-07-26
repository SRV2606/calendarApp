package com.example.calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mappers.CalendarMapper
import com.example.domain.models.ApiError
import com.example.domain.models.CalendarDay
import com.example.domain.models.ClientResult
import com.example.domain.repository.CalendarRepository
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
) : ViewModel() {

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

        val daysFromPrevMonth =
            (firstDayOfMonth - Calendar.SUNDAY + 7) % 7

        calendar.add(Calendar.DAY_OF_MONTH, -daysFromPrevMonth)
        for (i in 1..daysFromPrevMonth) {
            val date = calendar.time
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        for (i in 1..daysInMonth) {
            val date = calendar.time
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = true))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val daysFromNextMonth = 35 - calendarDaysList.size

        for (i in 1..daysFromNextMonth) {
            val date = calendar.time
            calendarDaysList.add(CalendarDay(date = date, tasksCount = 0, isCurrentMonth = false))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return calendarDaysList
    }

    fun storeCalendarTask(calendarDay: CalendarDay, title: String, description: String) {
        viewModelScope.launch {
            val taskBean = calendarMapper.toTaskBean(calendarDay, title, description)

            when (val req = getCalendarUseCase.storeCalendarTask(
                userID = CalendarRepository.USER_ID,
                taskBean
            )) {
                is ClientResult.Success -> {
                    req.data.let {
                        _storeTask.emit(ClientResult.Success(req.data))
                    }
                }

                else -> {
                    _storeTask.emit(ClientResult.Error(ApiError("Failed")))
                }
            }
        }
    }


}
