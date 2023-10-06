package com.adesso.movee.scene.cinemas.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.adesso.movee.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@SuppressLint("MissingPermission")
@Composable
fun GoogleMapsComponent(
    cameraPositionState: CameraPositionState,
    onMapClick: (LatLng) -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 18f,
                minZoomPreference = 5f,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.google_map_style
                )
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                compassEnabled = false,
                zoomControlsEnabled = false,
            )
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick,
        content = content
    )
}