package com.example.students.utils

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.students.todo.ui.student.StudentViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

val TAG = "My Map"

@Composable
fun MyMap(lat: Double, long: Double, studentViewModel: StudentViewModel) {
    Log.d("MyMap", "lat=$lat long=$long")
    val markerState = rememberMarkerState(position = LatLng(lat, long))
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

    Log.d("MyMap", "${markerState.position.latitude} and ${markerState.position.longitude}")
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            Log.d(TAG, "onMapClick $it ")
        },
        onMapLongClick = {
            Log.d(TAG, "onMapLongClick $it")
            markerState.position = it
            studentViewModel.lat = it.latitude
            studentViewModel.long = it.longitude
        },
    ) {

        Marker(
            state = MarkerState(position = markerState.position),
            title = "User location title",
            snippet = "User location",
        )
    }
}