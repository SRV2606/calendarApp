package com.example.data.serverModels

import com.google.gson.annotations.SerializedName


data class ServerCalendarTasks(
    @SerializedName("tasks")
    var tasks: List<Task?>?
) {

    data class Task(
        @SerializedName("task_detail")
        var taskDetail: TaskDetail?,
        @SerializedName("task_id")
        var taskId: Int?
    ) {
        data class TaskDetail(
            @SerializedName("date")
            var date: String?,
            @SerializedName("description")
            var description: String?,
            @SerializedName("title")
            var title: String?,
        )
    }
}