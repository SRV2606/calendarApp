package com.example.calendar.di

import com.example.data.mappers.CalendarMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCalendarMapper(): CalendarMapper {
        return CalendarMapper()
    }
}