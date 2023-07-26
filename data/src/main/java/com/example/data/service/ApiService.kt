package com.example.data.service

import com.example.data.serverModels.GetTaskListReq
import com.example.data.serverModels.ServerCalendarTasks
import com.example.data.serverModels.StoreTaskReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("/api/getCalendarTaskList")
    suspend fun geCalendarTaskList(
        @Body getCalendarTaskListReq: GetTaskListReq
    ): Response<ServerCalendarTasks>


    @POST("/api/deleteCalendarTask")
    suspend fun deleteCalendarTask(
        @Body getCalendarTaskListReq: GetTaskListReq
    ): Response<Unit>

    @POST("/api/storeCalendarTask")
    suspend fun storeCalendarTask(
        @Body storeTaskReq: StoreTaskReq
    ): Response<Unit>

}