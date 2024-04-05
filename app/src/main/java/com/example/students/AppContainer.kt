package com.example.students
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewModelScope
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.students.core.Api
import com.example.students.core.TAG
import com.example.students.todo.data.PreferencesRepository
import com.example.students.todo.data.StudentsRepository
import com.example.students.todo.data.remote.StudentWsClient
import com.example.students.todo.data.remote.StudentsApi
import com.example.students.utils.ConnectivityManagerNetworkMonitor
import com.example.students.utils.Notifications
import com.example.students.utils.SyncWorker
import com.example.students.utils.SyncWorkerFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

class AppContainer(val context: Context): Configuration.Provider {
    private val studentsApi: StudentsApi = Api.retrofit.create(StudentsApi::class.java)
    private val studentWsClient: StudentWsClient = StudentWsClient(Api.okHttpClient)
    private val database =  StudentDatabase.getDatabase(context)
    private val notification  = Notifications(context)
    val connectionManager = ConnectivityManagerNetworkMonitor(context)
    private var connected = false
    val studentsRepository = StudentsRepository(studentsApi, studentWsClient, database.studentDao(),
        connectionManager, notification)
    val preferencesRepository = PreferencesRepository(context.userPreferencesDataStore)

    init {
        Log.d(TAG, "init")
        WorkManager.initialize(context, workManagerConfiguration)
        launchJobs()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val myWorkerFactory: WorkerFactory = SyncWorkerFactory(studentsRepository)
        return Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()
    }

    private fun launchJobs() {
        MainScope().launch {
            connectionManager.isOnline.collect {
                if (it) {
                    val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                        .build()
                    WorkManager.getInstance(context).enqueue(workRequest)
                }
                Log.d(TAG, "Connection Status: $it")
            }
        }
    }
}
