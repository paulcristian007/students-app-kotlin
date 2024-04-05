package com.example.students.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.students.todo.data.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentsDao {
    @Query("SELECT * FROM students")
    fun loadAll(): Flow<List<Student>>

    @Query("SELECT * FROM students")
    fun getAll(): List<Student>

    @Query("SELECT* FROM students WHERE _id = :studentId")
    suspend fun find(studentId: String?): Student?

    @Query("SELECT* FROM students WHERE name = :name")
    fun findByUsername(name: String): Student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Student>)

    @Update
    suspend fun update(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()
}