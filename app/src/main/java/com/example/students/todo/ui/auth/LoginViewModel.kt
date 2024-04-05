package com.example.students.todo.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.students.todo.data.StudentsRepository
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.students.MyApplication
import com.example.students.core.TAG
import kotlinx.coroutines.launch

class LoginViewModel(private val studentsRepository: StudentsRepository): ViewModel() {
    var token: String? by mutableStateOf(null)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            Log.d(TAG, "Login")
            token = studentsRepository.login(username, password)
        }
    }


    companion object {
        fun Factory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                LoginViewModel(app.container.studentsRepository)
            }
        }
    }
}