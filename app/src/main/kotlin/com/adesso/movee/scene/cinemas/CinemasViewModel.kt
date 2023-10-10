package com.adesso.movee.scene.cinemas

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.domain.CinemaSearchUseCase
import com.adesso.movee.uimodel.CinemaUiModel
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

    private val _uiModel = MutableStateFlow(CinemaUiModel())
    val uiModel: StateFlow<CinemaUiModel> = _uiModel

    @SuppressLint("MissingPermission")
    fun getLocation() {
        viewModelScope.launch {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                val coordinates = LatLng(it.latitude, it.longitude)
                _uiModel.value = _uiModel.value.copy(lastLocation = coordinates)
                getCinemasOnLocation(coordinates)
            }
        }
    }

    fun getCinemasOnLocation(coordinates: LatLng?) {
        if (coordinates == null) return

        _uiModel.value = _uiModel.value.copy(searchVisibility = false)
        val query = "${coordinates.latitude},${coordinates.longitude} cinema"
        viewModelScope.launch {
            val result = cinemaSearchUseCase.run(query)

            runOnViewModelScope {
                result
                    .onSuccess {
                        _uiModel.value = _uiModel.value.copy(cinemaList = it)
                    }
                    .onFailure {
                        _uiModel.value = _uiModel.value.copy(cinemaList = emptyList())
                    }
            }
        }
    }

    fun setSelectedCinema(cinema: OSMObject) {
        _uiModel.value = _uiModel.value.copy(selectedCinema = cinema, dialogVisibility = true)
    }

    fun setDialogVisibility(visibility: Boolean) {
        _uiModel.value = _uiModel.value.copy(dialogVisibility = visibility)
    }

    fun setSearchVisibility(visibility: Boolean) {
        _uiModel.value = _uiModel.value.copy(searchVisibility = visibility)
    }
}
