package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.cinema.OSMObject
import retrofit2.http.GET
import retrofit2.http.Query

interface CinemaService {

    @GET(SEARCH)
    suspend fun fetchCinemaObjects(
        @Query("q") q: String,
        @Query(QUERY_FORMAT) format: String = QUERY_FORMAT_DEFAULT,
        @Query(QUERY_FEATURE_TYPE) featureType: String = QUERY_FEATURE_TYPE_DEFAULT,
        @Query(QUERY_LIMIT) limit: Int = QUERY_LIMIT_DEFAULT,
    ): List<OSMObject>

    companion object {
        const val QUERY_FORMAT = "format"
        const val QUERY_FORMAT_DEFAULT = "json"
        const val QUERY_FEATURE_TYPE = "cinema"
        const val QUERY_FEATURE_TYPE_DEFAULT = "cinema"
        const val QUERY_LIMIT = "limit"
        const val QUERY_LIMIT_DEFAULT = 20
        const val SEARCH = "search"
    }
}
