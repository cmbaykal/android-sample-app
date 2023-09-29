package com.adesso.movee.data.remote.model.cinema

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class OSMObject(
    @Json(name = "place_id") val placeId: Long,
    @Json(name = "name") val name: String,
    @Json(name = "display_name") val displayName: String,
    @Json(name = "lat") val latitude: String,
    @Json(name = "lon") val longitude: String,
    @Json(name = "type") val type: String
) : BaseResponseModel() {
    val lat get() = latitude.toDouble()
    val lon get() = longitude.toDouble()
}

/*
{
    "place_id": 50968845,
    "licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
    "osm_type": "node",
    "osm_id": 6139099428,
    "lat": "40.9961062",
    "lon": "28.8646599",
    "class": "amenity",
    "type": "cinema",
    "place_rank": 30,
    "importance": 9.99999999995449e-06,
    "addresstype": "amenity",
    "name": "CineVIP",
    "display_name": "CineVIP, 1, Mehmetçik Sokağı, Yayla, Bahçelievler Mahallesi, Bahçelievler, İstanbul, Marmara Bölgesi, 34180, Türkiye",
    "boundingbox": [
    "40.9960562",
    "40.9961562",
    "28.8646099",
    "28.8647099"
    ]
}
 */
