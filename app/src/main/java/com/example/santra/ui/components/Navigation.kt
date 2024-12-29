package com.example.santra.ui.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Database
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.LoginViewModelFactory
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.domain.viewmodels.PostViewModelFactory
import com.example.santra.domain.viewmodels.ProfileViewModel
import com.example.santra.domain.viewmodels.ProfileViewModelFactory
import com.example.santra.domain.viewmodels.RegisterViewModel
import com.example.santra.domain.viewmodels.RegisterViewModelFactory
import com.example.santra.ui.screens.AnnouncementDetailScreen
import com.example.santra.ui.screens.CreateMatch
import com.example.santra.ui.screens.HomeScreen
import com.example.santra.ui.screens.LoginScreen
import com.example.santra.ui.screens.ProfileScreen
import com.example.santra.ui.screens.RegisterScreen
import com.example.santra.ui.screens.RequestScreen

@Composable
fun AppNavigation(db:AppDatabase) {


    val navController = rememberNavController()
    val registerViewModelFactory = RegisterViewModelFactory(db.santraDao())
    val registerViewModel: RegisterViewModel = viewModel(factory = registerViewModelFactory)
    val loginViewModelFactory = LoginViewModelFactory(db.santraDao())
    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
    val profileViewModelFactory = ProfileViewModelFactory(db.santraDao())
    val profileViewModel: ProfileViewModel = viewModel(factory = profileViewModelFactory)
    val postViewModelFactory = PostViewModelFactory(db.santraDao())
    val postViewModel: PostViewModel = viewModel(factory = postViewModelFactory)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginViewModel) }
        composable("home") { HomeScreen(navController, postViewModel) }
        composable("register") { RegisterScreen(navController, registerViewModel) }
        composable(
            "announcement_detail/{announcementId}",
            arguments = listOf(navArgument("announcementId") { type = NavType.StringType })
        ) { backStackEntry ->
            val announcementId = backStackEntry.arguments?.getString("announcementId")
            AnnouncementDetailScreen(navController = navController, announcementId = announcementId, postViewModel)
        }
        composable("request") { RequestScreen(navController) }
        composable("creatematch") { CreateMatch(navController, postViewModel, loginViewModel, profileViewModel) }
        composable("profile") { ProfileScreen(navController, profileViewModel, loginViewModel) }
    }
}