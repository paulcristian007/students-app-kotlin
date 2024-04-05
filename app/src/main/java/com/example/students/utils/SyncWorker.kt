package com.example.students.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.students.core.TAG
import com.example.students.todo.data.StudentsRepository

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val studentsRepository: StudentsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "Hello from Worker")
        studentsRepository.sync()
        return Result.success()
    }
}