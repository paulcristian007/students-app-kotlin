package com.example.students.todo.data
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.students.core.TAG
import com.example.students.core.Token.token
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val tokenPreferences = stringPreferencesKey("token")

    init {
        Log.d(TAG, "init")
    }

    val loginStatusStream = flow {
        val preferences = dataStore.data.first()
        emit(preferences.asMap().isNotEmpty())
        token = preferences[tokenPreferences].toString()
        Log.d(TAG, "TOken is$token")
    }

    suspend fun save(tokenValue: String) {
        dataStore.edit { preferences ->
            preferences[tokenPreferences] = tokenValue
        }
        token = tokenValue
    }

    suspend fun delete() {
        dataStore.edit { preferences ->
            preferences.remove(tokenPreferences)
        }
        token = ""
    }
}