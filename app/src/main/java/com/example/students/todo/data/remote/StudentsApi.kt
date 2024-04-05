package com.example.students.todo.data.remote

import com.example.students.core.Token
import com.example.students.todo.data.Student
import com.example.students.todo.data.TokenDTO
import com.example.students.todo.data.UserCredentials
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentsApi {
    @GET("/student")
    suspend fun findAll(@Header("Authorization") token: String): List<Student>


    @Headers("Content-Type: application/json")
    @POST("/student")
    suspend fun save(@Header("Authorization") token: String, @Body student: Student): Student

    @Headers("Content-Type: application/json")
    @PUT("/student/{id}")
    suspend fun update(@Header("Authorization") token: String, @Path("id") studentId: Long?, @Body student: Student): Student

    @Headers("Content-Type: application/json")
    @GET("/student/{id}")
    suspend fun findOne(@Header("Authorization") token: String, @Path("id") studentId: Long?): Student

    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    suspend fun login(@Body credentials: UserCredentials): TokenDTO
}
