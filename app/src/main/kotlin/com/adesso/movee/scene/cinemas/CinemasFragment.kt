package com.adesso.movee.scene.cinemas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.adesso.movee.R
import com.adesso.movee.base.BaseFragment
import com.adesso.movee.databinding.FragmentCinemasBinding
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

class CinemasFragment : BaseFragment<CinemasViewModel, FragmentCinemasBinding>() {

    override val layoutId: Int get() = R.layout.fragment_cinemas

    override fun initialize() {
        super.initialize()
        binder.layout.setContent {
            val kocaeli = LatLng(40.7711717, 29.8933016)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(kocaeli, 11f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            )
        }
    }
}
