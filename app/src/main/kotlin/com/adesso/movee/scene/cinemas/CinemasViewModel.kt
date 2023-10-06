package com.adesso.movee.scene.cinemas

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.domain.CinemaSearchUseCase
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CinemasViewModel @Inject constructor(
    private val cinemaSearchUseCase: CinemaSearchUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _lastLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val lastLocation: StateFlow<LatLng> = _lastLocation.asStateFlow()

    private val _cinemaList = MutableStateFlow<List<OSMObject>?>(null)
    val cinemaList: StateFlow<List<OSMObject>?> = _cinemaList.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getLocation() {
        viewModelScope.launch {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                _lastLocation.value = LatLng(it.latitude, it.longitude)
                return@addOnSuccessListener
            }
        }
    }

    fun setLocation(latLng: LatLng) {
        _lastLocation.value = latLng
    }

    fun getCinemasOnLocation(coordinates: LatLng) {
        if (coordinates.latitude == 0.0 && coordinates.longitude == 0.0) return

        val query = "${coordinates.latitude},${coordinates.longitude} cinema"
        viewModelScope.launch {
            val result = cinemaSearchUseCase.run(query)

            runOnViewModelScope {
                result
                    .onSuccess {
                        _cinemaList.value = it
                    }
                    .onFailure {
                        _cinemaList.value = emptyList()
                    }
            }
        }
    }
}
