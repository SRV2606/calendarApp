package com.example.calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CalendarTasksBean
import com.example.domain.models.ClientResult
import com.example.domain.usecases.GetCalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarUseCase
) : ViewModel() {


    private val _getAllTaskResp: MutableStateFlow<ClientResult<CalendarTasksBean>> =
        MutableStateFlow(ClientResult.InProgress)
    val getAllTasks = _getAllTaskResp.asStateFlow()

    private val _deleteTaskResp: MutableStateFlow<ClientResult<Unit>> =
        MutableStateFlow(ClientResult.InProgress)
    val deleteTask = _deleteTaskResp.asStateFlow()

    init {
        getAllTasks(9001)
    }

    fun getAllTasks(userID: Int) {
        viewModelScope.launch {
            val req = getCalendarUseCase.getCalendarTasks(userID)
            when (req) {
                is ClientResult.Error -> {

                }

                ClientResult.InProgress -> {

                }

                is ClientResult.Success -> {
                    _getAllTaskResp.emit(ClientResult.Success(req.data))
                }
            }
        }
    }

    fun deleteTask(it: CalendarTasksBean.Task) {

        viewModelScope.launch {
            val req = getCalendarUseCase.deleteCalendarTask(9001, taskID = it.taskId!!)

            when (req) {
                is ClientResult.Success -> {
                    _deleteTaskResp.emit(ClientResult.Success(req.data))
                }

                is ClientResult.Error -> {
                    _deleteTaskResp.emit(ClientResult.Error(req.error))
                }

                else -> {
                    ClientResult.InProgress
                }
            }
        }
    }


}