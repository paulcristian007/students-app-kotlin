package com.example.students.todo.ui.students

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.students.todo.data.Student

typealias OnStudentFn = (id: Long?) -> Unit

@Composable
fun StudentList(students: List<Student>, onStudentClick: OnStudentFn, modifier: Modifier) {
    Log.d("StudentsList", "recompose")
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        items(students) { student ->
            StudentDetail(student, onStudentClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StudentDetail(student: Student, onItemClick: OnStudentFn) {
    Log.d("StudentDetail", "recompose id = ${student._id}")

    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) /*{
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.body1
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Profile: ${student.profile}, Year: ${student.year}",
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { onItemClick(student._id) }) {
                    Text(text = "Edit student")
                }
            }
        }
    }*/

        {
            Row(
                modifier = Modifier
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                Column {

                    ClickableText(text = AnnotatedString(student.name),
                        style = TextStyle(
                            fontSize = 24.sp,
                        ), onClick = { onItemClick(student._id) })
                }
                Column {
                    ClickableText(text = AnnotatedString(student.profile),
                        style = TextStyle(
                            fontSize = 24.sp,
                        ), onClick = { onItemClick(student._id) })
                }
                Column {
                    ClickableText(text = AnnotatedString(student.year.toString()),
                        style = TextStyle(
                            fontSize = 24.sp,
                        ), onClick = { onItemClick(student._id) })
                }
            }
        }
    }
}
