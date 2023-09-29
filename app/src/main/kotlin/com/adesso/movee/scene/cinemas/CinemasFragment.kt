package com.adesso.movee.scene.cinemas

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentCinemasBinding
import com.adesso.movee.scene.cinemas.components.GoogleMapsComponent
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CinemasFragment : BaseFragment<CinemasViewModel, FragmentCinemasBinding>() {

    override val layoutId: Int get() = R.layout.fragment_cinemas

    override fun initialize() {
        super.initialize()

        binder.layout.setContent {
            val cinemas by viewModel.cinemaList.observeAsState()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                GoogleMapsComponent(
                    permissionsState = locationPermission,
                    onLocationChange = {
                        viewModel.getCinemasOnLocation(it)
                    }
                ) {
                    cinemas?.forEach {
                        val markerState = MarkerState(position = LatLng(it.lat, it.lon))

                        Marker(
                            state = markerState,
                            title = it.name,
                            onClick = {
                                markerState.showInfoWindow()
                                true
                            }
                        )
                    }
                }
            }
        }
    }

    private val locationPermission by lazy {
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}
