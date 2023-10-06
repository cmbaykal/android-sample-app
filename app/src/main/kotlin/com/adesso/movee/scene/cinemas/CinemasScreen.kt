package com.adesso.movee.scene.cinemas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R
import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.scene.cinemas.components.GoogleMapsComponent
import com.adesso.movee.scene.cinemas.components.MapsMarker
import com.adesso.movee.scene.cinemas.components.MapsMarkerDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CinemasScreen(viewModel: CinemasViewModel) {
    val scope = rememberCoroutineScope()
    val lastLocation by viewModel.lastLocation.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    var dialogState by remember { mutableStateOf(false) }
    var searchButtonState by remember { mutableStateOf(false) }

    val cinemasState = viewModel.cinemaList.collectAsState()
    var cinemaState by remember { mutableStateOf<OSMObject?>(null) }

    LaunchedEffect(lastLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(lastLocation, 10f)
        viewModel.getCinemasOnLocation(lastLocation)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving && cinemasState.value != null) {
            searchButtonState = lastLocation != cameraPositionState.position.target
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMapsComponent(cameraPositionState = cameraPositionState, onMapClick = {
            scope.launch {
                dialogState = false
                delay(300)
                cinemaState = null
            }
        }) {
            if (lastLocation != LatLng(0.0, 0.0)) {
                MapsMarker(position = LatLng(lastLocation.latitude, lastLocation.longitude), R.drawable.ic_maps_marker_user) {
                    scope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newCameraPosition(CameraPosition(it.position, 12f, 0f, 0f)), durationMs = 400
                        )
                    }
                }
            }

            cinemasState.value?.forEach { cinema ->
                MapsMarker(position = LatLng(cinema.lat, cinema.lon), title = cinema.name, iconRes = R.drawable.ic_maps_marker) {
                    scope.launch {
                        cinemaState = cinema
                        delay(100)
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newCameraPosition(CameraPosition(it.position, 12f, 0f, 0f)), durationMs = 400
                        )
                        delay(150)
                        dialogState = true
                    }
                }
            }
        }

        MapsMarkerDialog(
            state = dialogState, title = cinemaState?.name.toString(), subTitle = cinemaState?.displayName.toString(), webLink = "${cinemaState?.name}.com"
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = searchButtonState && !dialogState && cinemaState == null,
            enter = expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Top
            ),
        ) {
            Button(modifier = Modifier
                .padding(bottom = 18.dp)
                .size(200.dp, 32.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.colorPrimary)
            ), shape = RoundedCornerShape(11.dp), onClick = {
                searchButtonState = false
                viewModel.getCinemasOnLocation(cameraPositionState.position.target)
            }) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Search Cinema",
                )
            }
        }
    }
}