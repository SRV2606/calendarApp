package com.example.domain.repository

import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult

interface CalendarRepository {


    companion object {
        const val USER_ID = 9001
    }

    suspend fun getCalendarTasks(userID: Int): ClientResult<CalendarTasksBean>

    suspend fun deleteCalendarTask(userID: Int, taskID: Int): ClientResult<Unit>

    suspend fun storeCalendarTask(
        userID: Int,
        tasksBean: CalendarTasksBean.Task
    ): ClientResult<Unit>

}