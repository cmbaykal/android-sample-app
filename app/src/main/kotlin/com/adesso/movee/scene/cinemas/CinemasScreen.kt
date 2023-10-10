package com.adesso.movee.scene.cinemas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adesso.movee.R
import com.adesso.movee.scene.cinemas.components.ButtonComponent
import com.adesso.movee.scene.cinemas.components.GoogleMapsComponent
import com.adesso.movee.scene.cinemas.components.MapsMarker
import com.adesso.movee.scene.cinemas.components.MapsMarkerDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun CinemasScreen(viewModel: CinemasViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()
    val uiState by viewModel.uiModel.collectAsState()

    LaunchedEffect(uiState.lastLocation) {
        uiState.lastLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving && !uiState.cinemaList.isNullOrEmpty()) {
            viewModel.setSearchVisibility(
                uiState.lastLocation != cameraPositionState.position.target && !uiState.dialogVisibility
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        with(uiState) {
            GoogleMapsComponent(
                cameraPositionState = cameraPositionState,
                onMapClick = { viewModel.setDialogVisibility(false) }
            ) {
                lastLocation?.let {
                    MapsMarker(position = LatLng(it.latitude, it.longitude), R.drawable.ic_maps_marker_user) {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newCameraPosition(CameraPosition(it.position, 12f, 0f, 0f)), durationMs = 400
                            )
                        }
                    }
                }

                cinemaList?.forEach { cinema ->
                    MapsMarker(position = LatLng(cinema.lat, cinema.lon), title = cinema.name, iconRes = R.drawable.ic_maps_marker) {
                        coroutineScope.launch {
                            viewModel.setSelectedCinema(cinema)
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newCameraPosition(CameraPosition(it.position, 12f, 0f, 0f)), durationMs = 400
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = dialogVisibility,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                MapsMarkerDialog(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                    title = selectedCinema?.name.toString(),
                    subTitle = selectedCinema?.displayName.toString(),
                    webLink = "${selectedCinema?.name}.com"
                )
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = searchVisibility && !dialogVisibility,
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top),
            ) {
                ButtonComponent(
                    modifier = Modifier.padding(bottom = 18.dp),
                    text = stringResource(R.string.search_cinema_button_text)
                ) {
                    viewModel.getCinemasOnLocation(cameraPositionState.position.target)
                }
            }
        }
    }
}