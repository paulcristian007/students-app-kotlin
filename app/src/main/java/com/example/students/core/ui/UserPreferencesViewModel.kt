package com.example.students.core.ui

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
import com.example.students.todo.data.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val userPreferencesRepository: PreferencesRepository) :
    ViewModel() {
    var loginStatus by mutableStateOf(false)

    init {
        Log.d(TAG, "init")
        loadToken()
    }

    private fun loadToken() {
        viewModelScope.launch {
            Log.d(TAG, "load token")
            userPreferencesRepository.loginStatusStream.collect {
                loginStatus = it
                Log.d(TAG, "login status = $loginStatus")
            }
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            Log.d(TAG, "delete token")
            userPreferencesRepository.delete()
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            Log.d(TAG, "delete token")
            userPreferencesRepository.save(token)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                UserPreferencesViewModel(app.container.preferencesRepository)
            }
        }
    }
}
