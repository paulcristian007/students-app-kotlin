package com.example.students.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    var _id: Long? = null,

    val name: String = "",
    val profile: String = "",
    val year: Int = 0,
    val lat: Double? = null,
    val long: Double? = null
    )
