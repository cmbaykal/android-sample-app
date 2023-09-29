package com.adesso.movee.scene.cinemas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.databinding.FragmentCinemasBinding
import com.adesso.movee.scene.cinemas.components.GoogleMapsComponent
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CinemasFragment : BaseFragment<CinemasViewModel, FragmentCinemasBinding>() {

    override val layoutId: Int get() = R.layout.fragment_cinemas

    override fun initialize() {
        super.initialize()

        binder.layout.setContent {
            val cinemasState = viewModel.cinemaList.observeAsState()
            val cinemaDialogState by remember { mutableStateOf<OSMObject?>(null) }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                GoogleMapsComponent(
                    permissionsState = locationPermission,
                    cinemasState = cinemasState,
                    onLocationChange = {
                        viewModel.getCinemasOnLocation(it)
                    },
                    onMarkerClick = {

                    }
                )
            }
        }
    }

    private val locationPermission by lazy {
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}