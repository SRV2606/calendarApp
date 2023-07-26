package com.example.domain.models

import java.util.Date

data class CalendarDay(
    val id: Int? = Math.random().toInt(),
    val date: Date,
    val tasksCount: Int,
    val isCurrentMonth: Boolean
)