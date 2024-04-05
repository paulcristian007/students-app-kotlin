package com.example.students.todo.data

import android.util.Log
import com.example.students.todo.data.remote.StudentsApi
import com.example.students.core.TAG
import com.example.students.core.Token
import com.example.students.core.Token.token
import com.example.students.todo.data.local.StudentsDao
import com.example.students.todo.data.remote.StudentWsClient
import com.example.students.utils.ConnectivityManagerNetworkMonitor
import com.example.students.utils.Notifications
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentsRepository(private val studentsApi: StudentsApi, private val studentWsClient: StudentWsClient,
    private val studentDao: StudentsDao, private val connectionManager: ConnectivityManagerNetworkMonitor,
    private val notification: Notifications) {
    //private var cachedStudents: MutableList<Student> = listOf<Student>().toMutableList()
    val studentsStream by lazy { studentDao.loadAll() }
    var connected = true


    init {
        Log.d(TAG, "init")
        collectNetworkStatus()
    }

    private fun collectNetworkStatus() {
        MainScope().launch {
            connectionManager.isOnline.collect {
                connected = it
            }
        }
    }

    private fun equals(studentOne: Student, studentTwo: Student): Boolean {
        if (studentOne.name != studentTwo.name)
            return false
        if (studentOne.profile != studentTwo.profile)
            return false
        if (studentOne.year != studentTwo.year)
            return false
        if (studentOne.lat != studentTwo.lat)
            return false
        if (studentOne.long != studentTwo.long)
            return false
        return true
    }

    suspend fun sync() {
        for (student in studentDao.getAll()) {
            try {
                val prevStudent = studentsApi.findOne("Bearer $token", student._id)
                if (!equals(prevStudent, student))
                    studentsApi.update("Bearer $token", student._id, student)
            }
            catch (e: Exception) {
                studentsApi.save("Bearer $token", student)
            }
        }
        notification.showSimpleNotification(textTitle = "StudentsApp Notification", "The sync has been successful")
    }

    suspend fun login(username: String, password: String): String {
        return try {
            Log.d(TAG, "login username: $username, password: $password")
            token = studentsApi.login(UserCredentials(username, password)).token
            Log.d(TAG, "username: $username, token: $token")
            token
        } catch (e: Exception) {
            ""
        }
    }

    suspend fun save(student: Student): Student {
        Log.d(TAG, "save $student")
        var newStudent: Student
        withContext(Dispatchers.IO) {
            studentDao.insert(student)
            newStudent = studentDao.findByUsername(student.name)
            if (connected)
                studentsApi.save("Bearer $token", newStudent)
        }
        return newStudent
    }

    suspend fun find(studentId: String?): Student? {
        var student: Student?
        withContext(Dispatchers.IO) {
            student = studentDao.find(studentId)
            Log.d(TAG, student.toString() + " " + studentId)
        }
        return student
    }

    suspend fun update(student: Student) {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "update $student")
            studentDao.update(student)
            if (connected)
                studentsApi.update("Bearer $token", student._id, student)
        }
    }

    suspend fun loadAll(): List<Student> {
        return studentDao.getAll()
    }
}

/*suspend fun loadAll(): Flow<Result<List<Student>>> = flow {
    Log.d(TAG, "loadAll started")
    emit(Result.Loading)
    if (cachedStudents.size > 0) {
        Log.d(TAG, "loadAll emit cached students")
        emit(Result.Success(cachedStudents as List<Student>))
    }
    while (coroutineContext.isActive) {
        try {
            val items = studentsApi.findAll("Bearer $token")
            cachedStudents = items.toMutableList()
            Log.d(TAG, "loadAll emit remote items")
            emit(Result.Success(cachedStudents as List<Student>))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
        delay(5000)
    }
    Log.d(TAG, "loadAll completed")
}*/


/*suspend fun find(studentId: String?): Student? {
    Log.d(TAG, "load $studentId")
    return studentsApi.findOne(studentId)
}

    suspend fun listenSocketEvents() {
        withContext(Dispatchers.IO) {
            getStudentsEvents().collect {
                Log.d(TAG, "Item event collected ${currentCoroutineContext().javaClass} $it")
            }
        }
    }

     private fun getStudentsEvents(): Flow<kotlin.Result<String>> = callbackFlow {
        Log.d(TAG, "getStudentsEvents started")
        studentWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                trySend(kotlin.Result.success(it))
            },
            onClosed = { close() },
            onFailure = { close() })
        awaitClose { studentWsClient.closeSocket() }
    }

*/