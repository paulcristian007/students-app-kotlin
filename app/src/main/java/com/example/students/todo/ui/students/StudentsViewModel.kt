package com.example.students.todo.ui.students

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.students.MyApplication
import com.example.students.core.TAG
import com.example.students.todo.data.Student
import com.example.students.todo.data.StudentsRepository
import com.example.students.utils.ConnectivityManagerNetworkMonitor
import com.example.students.utils.ProximitySensorMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StudentsViewModel(private val application: Application, private val studentsRepository: StudentsRepository,
    private val connectionManager: ConnectivityManagerNetworkMonitor) : ViewModel() {
    val uiState: Flow<List<Student>> = studentsRepository.studentsStream
    var networkStatus by mutableStateOf(false)
    var sensorStatus by mutableStateOf(false)

    init {
        Log.d(TAG, "init")
        collectNetworkStatus()
        collectSensorStatus()
    }

    private fun collectNetworkStatus() {
        viewModelScope.launch {
            connectionManager.isOnline.collect {
                networkStatus = it;
            }
        }
    }

    private fun collectSensorStatus() {
        viewModelScope.launch {
            ProximitySensorMonitor(application).isNear.collect {
                sensorStatus = it
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                StudentsViewModel(app, app.container.studentsRepository, app.container.connectionManager)
            }
        }
    }
}
