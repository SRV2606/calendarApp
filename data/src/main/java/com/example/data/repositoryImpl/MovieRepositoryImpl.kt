package com.example.data.repositoryImpl

import com.example.data.mappers.MoviesMapper
import com.example.data.service.ApiService
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val mapper: MoviesMapper
) : MovieRepository {

    companion object {

    }


}