package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarTasksBean(
    var tasks: List<Task?>?
) : Parcelable {

    @Parcelize
    data class Task(
        var taskDetail: TaskDetail?,
        var taskId: Int?
    ) : Parcelable {
        @Parcelize
        data class TaskDetail(
            var date: String?,
            var description: String?,
            var title: String?,
        ) : Parcelable
    }
}