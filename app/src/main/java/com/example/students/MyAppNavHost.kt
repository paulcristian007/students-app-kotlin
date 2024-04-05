package com.example.students

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.students.core.ui.UserPreferencesViewModel
import com.example.students.todo.ui.auth.LoginScreen
import com.example.students.todo.ui.student.StudentScreen
import com.example.students.todo.ui.students.StudentsScreen

val studentsRoute = "students"
val loginRoute = "auth"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val userPreferencesViewModel =
        viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    val onCloseStudent = {
        Log.d("MyAppNavHost", "navigate back to list")
        navController.popBackStack()
    }
    val onLogin = {
        Log.d("MyAppNavHost","navigate to list")
        navController.navigate(studentsRoute)
    }
    NavHost(
        navController = navController,
        startDestination = loginRoute
    ) {
        composable(loginRoute) {
            LoginScreen(onLogin = { onLogin() } )
        }
        composable(studentsRoute) {
            StudentsScreen(
                onStudentsClick = { studentId ->
                    Log.d("MyAppNavHost", "navigate to student $studentId")
                    navController.navigate("$studentsRoute/$studentId")
                },
                onAddItem = {
                    Log.d("MyAppNavHost", "navigate to new student")
                    navController.navigate("$studentsRoute-new")
                },
                onLogout = {
                    Log.d("MyAppNavHost", "navigate to new student")
                    navController.navigate(loginRoute)
                }

            )
        }
        composable(
            route = "$studentsRoute/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        {
            StudentScreen(
                studentId = it.arguments?.getString("id"),
                onClose = { onCloseStudent() }
            )
        }
        composable(route = "$studentsRoute-new")
        {
            StudentScreen(
                studentId = null,
                onClose = { onCloseStudent() }
            )
        }
    }

    LaunchedEffect(userPreferencesViewModel.loginStatus) {
        Log.d("MyAppNavHost", userPreferencesViewModel.loginStatus.toString())
        if (userPreferencesViewModel.loginStatus) {
            Log.d("MyAppNavHost", "Launched effect navigate to students")
            navController.navigate(studentsRoute) {
                popUpTo(0)
            }
        }
    }
}
