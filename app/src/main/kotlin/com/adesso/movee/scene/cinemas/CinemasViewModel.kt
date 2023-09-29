package com.adesso.movee.scene.cinemas

import android.app.Application
import androidx.compose.ui.layout.LookaheadLayoutCoordinates
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.domain.CinemaSearchUseCase
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CinemasViewModel @Inject constructor(
    private val cinemaSearchUseCase: CinemaSearchUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _cinemaList = MutableLiveData<List<OSMObject>>()
    val cinemaList: LiveData<List<OSMObject>> = _cinemaList

    fun getCinemasOnLocation(coordinates: LatLng) {
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
