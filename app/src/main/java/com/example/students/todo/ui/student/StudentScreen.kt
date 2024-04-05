package com.example.students.todo.ui.student
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.students.core.Result
import com.example.students.utils.MyMap
import com.example.students.utils.homeLat
import com.example.students.utils.homeLong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(studentId: String?, onClose: () -> Unit) {
    val studentViewModel =
        viewModel<StudentViewModel>(factory = StudentViewModel.Factory(studentId))
    val student = studentViewModel.student
    var name by rememberSaveable { mutableStateOf(student.name) }
    var profile by rememberSaveable { mutableStateOf(student.profile) }
    var year by rememberSaveable { mutableStateOf(student.year.toString()) }

    var nameFlag by remember { mutableStateOf(false) }
    val nameTransition = rememberInfiniteTransition()
    val nameAlpha by nameTransition.animateFloat(
        initialValue = if (nameFlag) 0f else 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                //0.5f at 500
            },
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    val nameColor = if (nameFlag) {
        Color.Red
    } else {
        Color.Black
    }


    var profileFlag by remember { mutableStateOf(false) }
    val profileTransition = rememberInfiniteTransition()
    val profileAlpha by profileTransition.animateFloat(
        initialValue = if (profileFlag) 0f else 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                //0.5f at 500
            },
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    val profileColor = if (profileFlag) {
        Color.Red
    } else {
        Color.Black
    }

    var yearFlag by remember { mutableStateOf(false) }
    val yearTransition = rememberInfiniteTransition()
    val yearAlpha by yearTransition.animateFloat(
        initialValue = if (yearFlag) 0f else 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                //0.5f at 500
            },
            repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    val yearColor = if (yearFlag) {
        Color.Red
    } else {
        Color.Black
    }



    //var lat by rememberSaveable { mutableDoubleStateOf(studentViewModel.lat)}
    //var long by rememberSaveable { mutableDoubleStateOf(studentViewModel.long)}

    Log.d("StudentScreen", "recompose, name = $name, profile = $profile, year = $year")


    /*LaunchedEffect(studentViewModel.lat, studentViewModel.long) {
        Log.d("StudentScreen", "${studentViewModel.lat} ${studentViewModel.long}")
        lat = studentViewModel.lat
        long = studentViewModel.long
    }*/

    LaunchedEffect(studentViewModel.saved) {
        Log.d("ItemScreen", "Submit = ${studentViewModel.saved}");
        if (studentViewModel.saved) {
            Log.d("ItemScreen", "Closing screen")
            onClose()
        }
    }

    LaunchedEffect(student) {
        Log.d("Student Screen", "student changed")
        name = student.name
        year = student.year.toString()
        profile = student.profile
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Student Screen") },
                actions = {
                    Button(onClick = {
                        Log.d(
                            "StudentScreen",
                            "save item name = ${student.name}, profile = $profile, year = $year"
                        );

                        if (!name.matches(Regex("^[a-zA-Z]+\$")))
                            nameFlag = true
                        if (!profile.matches(Regex("^[a-zA-Z ]+\$")))
                            profileFlag = true
                        val yearValue = year.toIntOrNull()
                        if  (yearValue == null || yearValue < 0)
                            yearFlag = true

                        if (!nameFlag && !profileFlag && !yearFlag)
                            studentViewModel.saveOrUpdateStudent(name, profile, year.toInt())
                    }) { Text("Save") }
                }
            )
        }
    ) {
        // Content that needs to appear/disappear goes here:
        //Text("Content to appear/disappear", Modifier.fillMaxWidth().requiredHeight(200.dp))
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row {
                TextField(
                    value = name,
                    onValueChange = {
                        nameFlag = false
                        Log.d("StudentScreen", "name changed $it")
                        name = it
                        Log.d("StudentScreen", "rendered name $name")
                    },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = nameColor.copy(alpha = nameAlpha)),
                    enabled = true
                )
            }

            Row {
                TextField(
                    value = profile,
                    onValueChange = {
                        profileFlag = false
                        profile = it },
                    label = { Text("Profile") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = profileColor.copy(alpha = profileAlpha)),
                    enabled = true
                )
            }
            Row {
                TextField(
                    value = year,
                    onValueChange = {
                        yearFlag = false
                        year = it },
                    label = { Text("Year") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = yearColor.copy(alpha = yearAlpha)),
                    enabled = true
                )
            }

            if (studentViewModel.errMsg != "") {
                Text(
                    text = "Failed to submit student - ${studentViewModel.errMsg}",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            //if (studentViewModel.loaded)
            // MyMap(studentViewModel.lat, studentViewModel.long, studentViewModel)
        }
    }
}


@Preview
@Composable
fun PreviewItemScreen() {
    StudentScreen(studentId = "0", onClose = {})
}
