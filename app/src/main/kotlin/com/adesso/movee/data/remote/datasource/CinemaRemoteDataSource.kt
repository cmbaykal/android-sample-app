package com.adesso.movee.data.remote.datasource

import com.adesso.movee.data.remote.BaseRemoteDataSource
import com.adesso.movee.data.remote.api.CinemaService
import com.adesso.movee.data.remote.model.cinema.OSMObject
import javax.inject.Inject

class CinemaRemoteDataSource @Inject constructor(
    private val service: CinemaService
) : BaseRemoteDataSource() {

    suspend fun fetchCinemaObjects(query: String): List<OSMObject> = invoke {
        service.fetchCinemaObjects(query)
    }
}
