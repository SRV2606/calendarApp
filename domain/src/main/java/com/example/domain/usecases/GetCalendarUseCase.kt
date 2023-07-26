package com.example.domain.usecases

import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import com.example.domain.repository.CalendarRepository
import javax.inject.Inject

class GetCalendarUseCase @Inject constructor(private val repository: CalendarRepository) {


    suspend fun getCalendarTasks(
        userID: Int
    ): ClientResult<CalendarTasksBean> {
        return repository.getCalendarTasks(userID = userID)
    }


    suspend fun storeCalendarTask(
        userID: Int,
        tasksBean: CalendarTasksBean.Task
    ): ClientResult<Unit> {
        return repository.storeCalendarTask(userID, tasksBean = tasksBean)
    }

    suspend fun deleteCalendarTask(
        userID: Int,
        taskID: Int
    ): ClientResult<Unit> {
        return repository.deleteCalendarTask(userID = userID, taskID = taskID)
    }
}