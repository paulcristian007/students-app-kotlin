package com.example.students.todo.ui.messages

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.students.core.TAG


@Composable
fun MessageDetail(message: String) {
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black) // You can customize the border width and color
            .padding(5.dp) // Padding around the text
    ) {
        Text(text = message)
    }
}

@Composable
fun UserDetail(username: String, onUserSelected: (String) -> Unit) {
    ClickableText(
        text = AnnotatedString(username),
        onClick = {
            Log.d("UserDetail", "onClick")
            onUserSelected(username)
            Log.d("UserDetail", username)
        })
}

@Composable
fun MessagesList(messages: List<String>) {
    Log.d("MessagesList", "recompose")
    LazyColumn(
        modifier = Modifier
            .padding(12.dp)
    ) {
        items(messages) { message ->
            MessageDetail(message)
        }
    }
}

@Composable
fun UserList(users: List<String>, onUserSelected: (String) -> Unit) {
    Log.d("StudentsList", "recompose")
    LazyColumn(
        modifier = Modifier
            .padding(12.dp)
    ) {
        items(users) { user ->
            UserDetail(user, onUserSelected)
        }
    }
}



@Composable
fun SplitScreen() {
    val messageViewModel = viewModel<MessageViewModel>(factory = MessageViewModel.Factory())
    val messages = messageViewModel.messages
    val users = messageViewModel.users
    var currentUser by rememberSaveable {mutableStateOf("")}
    LaunchedEffect(currentUser) {
        Log.d("Split Screen", "received update")
    }
    

    Row {

        Column(modifier = Modifier.fillMaxWidth(0.33f)) {
            Text("Users")
            UserList(users = users, onUserSelected = { selectedUser ->
                    currentUser = selectedUser
                })
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Messages from user $currentUser")
            MessagesList(messages = messages)
        }
    }
}

