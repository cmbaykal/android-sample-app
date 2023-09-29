package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.CinemaRemoteDataSource
import com.adesso.movee.data.remote.model.cinema.OSMObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CinemaRepository @Inject constructor(
    private val remoteDataSource: CinemaRemoteDataSource
) {

    suspend fun getCinemas(query: String): List<OSMObject> {
        return remoteDataSource.fetchCinemaObjects(query)
    }
}
