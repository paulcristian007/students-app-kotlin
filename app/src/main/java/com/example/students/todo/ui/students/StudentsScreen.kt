package com.example.students.todo.ui.students

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.students.core.ui.UserPreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(onStudentsClick: (id: Long?) -> Unit, onAddItem: () -> Unit, onLogout: () -> Unit) {
    Log.d("StudentsScreen", "recompose")
    val studentsViewModel = viewModel<StudentsViewModel>(factory = StudentsViewModel.Factory)
    val userPreferencesViewModel = viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    //val studentsUiState = studentsViewModel.uiState
    val students by studentsViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = listOf()
    )
    var visible by remember { mutableStateOf(true) }
    
    Row {
        Column {
            Text("Is online: ${studentsViewModel.networkStatus}")
        }
    }
    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Is online: ${studentsViewModel.networkStatus}")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {

                    Text(text = "Proximity sensor: ${studentsViewModel.sensorStatus}")
                    Button(onClick = {
                        userPreferencesViewModel.deleteToken()
                        onLogout()
                    }) {
                        Text("Logout")
                    }
            }
        },
        
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("StudentsScreen", "add")
                    onAddItem()
                },
            ) { Icon(Icons.Rounded.Add, "Add") }
        }
    ) {
        StudentList(
                students = students,
                onStudentClick = onStudentsClick,
                modifier = Modifier.padding(it)
            )
    }
}

@Preview
@Composable
fun PreviewItemsScreen() {
    StudentsScreen(onStudentsClick = {}, onAddItem = {}, onLogout = {})
}
