package com.adesso.movee.uimodel

import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.google.android.gms.maps.model.LatLng

data class CinemaUiModel(
    val lastLocation: LatLng? = null,
    val cinemaList: List<OSMObject>? = null,
    val selectedCinema: OSMObject? = null,
    val dialogVisibility: Boolean = false,
    val searchVisibility: Boolean = false
)
