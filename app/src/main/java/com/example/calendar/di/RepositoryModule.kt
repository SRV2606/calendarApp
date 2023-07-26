package com.example.calendar.di

import com.example.data.repositoryImpl.CalendarRepoImpl
import com.example.domain.repository.CalendarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindCalendarRepo(calendarRepositoryImpl: CalendarRepoImpl): CalendarRepository

}