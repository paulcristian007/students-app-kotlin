package com.example.students
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.students.core.TAG
import com.example.students.todo.ui.messages.SplitScreen
import com.example.students.ui.theme.StudentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "onCreate")
            MyApp {
                //MyAppNavHost()
                SplitScreen()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "on pause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "on resume")

    }
}

@Composable
fun Greeting(message: String) {
    Column {
        Text(text = message)
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Log.d("MyApp", "recompose")
    StudentsTheme {
        Surface {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp {
        MyAppNavHost()
    }
}
