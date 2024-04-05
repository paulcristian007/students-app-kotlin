package com.example.students.todo.ui.messages

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.students.MyApplication
import com.example.students.core.TAG
import com.example.students.todo.ui.student.StudentViewModel

class MessageViewModel : ViewModel() {
    var users = listOf("a", "b", "c")
    var messages = listOf("hello", "salut", "willkommen", "bonjour", "salve")
    var user = ""



    companion object {
        fun Factory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                MessageViewModel()
            }
        }
    }
}