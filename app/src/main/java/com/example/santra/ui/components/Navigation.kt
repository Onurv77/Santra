package com.example.santra.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.RegisterViewModel
import com.example.santra.domain.viewmodels.RegisterViewModelFactory
import com.example.santra.ui.screens.HomeScreen
import com.example.santra.ui.screens.LoginScreen
import com.example.santra.ui.screens.RegisterScreen

@Composable
fun AppNavigation(db:AppDatabase) {

    val navController = rememberNavController()
    val registerViewModelFactory = RegisterViewModelFactory(db.santraDao())
    val registerViewModel: RegisterViewModel = viewModel(factory = registerViewModelFactory)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, db) }
        composable("home") { HomeScreen(navController) }
        composable("register") { RegisterScreen(navController, registerViewModel) }
    }
}