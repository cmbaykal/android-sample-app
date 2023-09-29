package com.adesso.movee.scene.cinemas.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.adesso.movee.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@Composable
fun GoogleMapsComponent(
    permissionsState: Boolean,
    onLocationChange: (LatLng) -> Unit,
    markers: @Composable () -> Unit
) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 11f)
    }

    if (permissionsState) {
        var locationProvider: FusedLocationProviderClient? = LocationServices.getFusedLocationProviderClient(context)

        DisposableEffect(locationProvider) {
            locationProvider?.lastLocation?.addOnSuccessListener {
                if (it.latitude != 0.0 && it.longitude != 0.0) {
                    val coordinates = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(coordinates, 11f)
                    onLocationChange.invoke(coordinates)
                }
            }

            onDispose {
                locationProvider = null
            }
        }
    }

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
        content = markers,
    )
}
