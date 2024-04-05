package com.example.students.utils

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.students.todo.data.StudentsRepository

class SyncWorkerFactory(private val studentsRepository: StudentsRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> SyncWorker(appContext, workerParameters, studentsRepository)
            // Add more cases for other custom workers if needed
            else -> null
        }
    }
}