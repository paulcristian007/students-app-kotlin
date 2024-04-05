package com.example.students.todo.ui.student

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.students.MyApplication
import com.example.students.core.TAG
import com.example.students.core.Result
import com.example.students.todo.data.Student
import com.example.students.todo.data.StudentsRepository
import com.example.students.utils.homeLat
import com.example.students.utils.homeLong
import kotlinx.coroutines.launch


class StudentViewModel(private val studentId: String?, private val studentsRepository: StudentsRepository) : ViewModel() {

    var student by mutableStateOf(Student())
    var errMsg by mutableStateOf("")
    var saved by mutableStateOf(false)
    var lat  = homeLat
    var long = homeLong
    var loaded by mutableStateOf(false)

    init {
        Log.d(TAG, "init")
        loadStudent()
    }

    private fun loadStudent() {
        viewModelScope.launch {
            student = studentsRepository.find(studentId)?.copy() ?: Student()
            Log.d(TAG, student.toString())
            if (student.lat != null) {
                lat = student.lat!!
                long = student.long!!
            }
            loaded = true
        }
    }

    fun saveOrUpdateStudent(name: String, profile: String, year: Int) {
        viewModelScope.launch {
            Log.d(TAG, "saveOrUpdateItem...")
            try {
                student = student.copy(_id = studentId?.toLong(), name = name, profile = profile,
                    year = year, lat = lat, long = long)

                if (studentId == null)
                    student = studentsRepository.save(student)
                else
                    studentsRepository.update(student)
                Log.d(TAG, "saveOrUpdateStudent succeeded")
                saved = true
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateStudent failed")
                errMsg = e.message.toString()
            }
        }
        //Log.d(TAG, "from viewmodel: $lat $long")
    }

    companion object {
        fun Factory(studentId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                StudentViewModel(studentId, app.container.studentsRepository)
            }
        }
    }
}
