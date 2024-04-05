package com.example.students.todo.data.remote

import com.example.students.todo.data.Student

data class StudentEvent(val type: String, val payload: Student)
