package com.example.data.repositoryImpl

import com.example.data.mappers.CalendarMapper
import com.example.data.serverModels.GetTaskListReq
import com.example.data.serverModels.StoreTaskReq
import com.example.data.service.ApiService
import com.example.data.utlils.safeApiCall
import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import com.example.domain.repository.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalendarRepoImpl @Inject constructor(
    private val service: ApiService,
    private val mapper: CalendarMapper
) : CalendarRepository {

    companion object {

    }

    override suspend fun getCalendarTasks(userID: Int): ClientResult<CalendarTasksBean> {
        return withContext(Dispatchers.IO) {
            return@withContext mapper.toCalendarTasksBean(safeApiCall {
                service.geCalendarTaskList(GetTaskListReq(userID = userID))
            })
        }
    }

    override suspend fun deleteCalendarTask(userID: Int, taskID: Int): ClientResult<Unit> {
        return withContext(Dispatchers.IO) {
            return@withContext safeApiCall {
                service.deleteCalendarTask(GetTaskListReq(userID = userID, taskId = taskID))
            }
        }
    }

    override suspend fun storeCalendarTask(
        userID: Int,
        tasksBean: CalendarTasksBean.Task
    ): ClientResult<Unit> {
        return withContext(Dispatchers.IO) {
            return@withContext safeApiCall {
                service.storeCalendarTask(
                    StoreTaskReq(
                        userId = userID,
                        task = StoreTaskReq.Task(
                            description = tasksBean.taskDetail?.description,
                            title = tasksBean.taskDetail?.title,
                            date = tasksBean.taskDetail?.date
                        )
                    )
                )
            }
        }
    }


}