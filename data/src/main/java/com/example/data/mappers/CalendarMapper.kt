package com.example.data.mappers

import com.example.data.serverModels.ServerCalendarTasks
import com.example.domain.models.CalendarDay
import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CalendarMapper @Inject constructor() {


    fun toCalendarTasksBean(serverResponse: ClientResult<ServerCalendarTasks>): ClientResult<CalendarTasksBean> {
        return serverResponse.let { clientRes ->
            when (clientRes) {
                is ClientResult.Success -> {
                    val result = clientRes.data.let {
                        val tasks = it.tasks?.map { serverTask ->
                            // Perform the mapping for each task in the list
                            CalendarTasksBean.Task(
                                taskId = serverTask?.taskId,
                                taskDetail = CalendarTasksBean.Task.TaskDetail(
                                    date = serverTask?.taskDetail?.date,
                                    description = serverTask?.taskDetail?.description,
                                    title = serverTask?.taskDetail?.title
                                )
                            )
                        }
                        // Create the CalendarTasksBean with the mapped tasks
                        CalendarTasksBean(tasks = tasks)
                    }
                    return ClientResult.Success(result)
                }

                is ClientResult.Error -> {
                    return ClientResult.Error(clientRes.error)

                }

                else -> {
                    ClientResult.InProgress
                }
            }
        }
    }

    fun toTaskBean(calendarDay: CalendarDay): CalendarTasksBean.Task {
        return CalendarTasksBean.Task(
            taskId = formatDateToDayOfMonth(calendarDay.date.toString()).toInt(),
            taskDetail = CalendarTasksBean.Task.TaskDetail(
                title = "This is title",
                description = "This is descruption",
                date = formatDateToDayOfMonth(calendarDay.date.toString())
            )
        )
    }
}

fun formatDateToDayOfMonth(dateString: String): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val date: Date = inputFormat.parse(dateString)
    val outputFormat = SimpleDateFormat("dd", Locale.ENGLISH)
    return outputFormat.format(date)
}










