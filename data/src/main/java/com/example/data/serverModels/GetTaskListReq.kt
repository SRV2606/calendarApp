package com.example.data.serverModels

import com.google.gson.annotations.SerializedName


data class GetTaskListReq(
    @SerializedName("user_id")
    val userID: Int? = 0,
    @SerializedName("task_id")
    val taskId: Int? = 0,

    )


data class StoreTaskReq(
    @SerializedName("task")
    var task: Task?,
    @SerializedName("user_id")
    var userId: Int?
) {

    data class Task(
        @SerializedName("description")
        var description: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("date")
        var date: String?
    )
}