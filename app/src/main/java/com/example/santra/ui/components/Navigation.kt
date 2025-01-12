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
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.ChatViewModelFactory
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.LoginViewModelFactory
import com.example.santra.domain.viewmodels.PostParticipantsViewModel
import com.example.santra.domain.viewmodels.PostParticipantsViewModelFactory
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.domain.viewmodels.PostViewModelFactory
import com.example.santra.domain.viewmodels.ProfileViewModel
import com.example.santra.domain.viewmodels.ProfileViewModelFactory
import com.example.santra.domain.viewmodels.RegisterViewModel
import com.example.santra.domain.viewmodels.RegisterViewModelFactory
import com.example.santra.ui.screens.AnnouncementDetailScreen
import com.example.santra.ui.screens.ChatScreen
import com.example.santra.ui.screens.CreateMatch
import com.example.santra.ui.screens.HomeScreen
import com.example.santra.ui.screens.LoginScreen
import com.example.santra.ui.screens.ProfileScreen
import com.example.santra.ui.screens.RegisterScreen
import com.example.santra.ui.screens.RequestScreen
import com.example.santra.ui.screens.SettingsScreen
import com.example.santra.ui.screens.TextMessage

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
    val postParticipantsViewModelFactory = PostParticipantsViewModelFactory(db.santraDao())
    val postParticipantsViewModel: PostParticipantsViewModel = viewModel(factory = postParticipantsViewModelFactory)
    val chatViewModelFactory = ChatViewModelFactory(db.santraDao())
    val chatViewModel: ChatViewModel = viewModel(factory = chatViewModelFactory)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginViewModel) }
        composable("home") { HomeScreen(navController, postViewModel) }
        composable("register") { RegisterScreen(navController, registerViewModel) }
        composable("chat") { ChatScreen(navController, chatViewModel, loginViewModel) }
        composable(
            "announcement_detail/{announcementId}",
            arguments = listOf(navArgument("announcementId") { type = NavType.StringType })
        ) { backStackEntry ->
            val announcementId = backStackEntry.arguments?.getString("announcementId")
            AnnouncementDetailScreen(navController = navController,
                announcementId = announcementId,
                viewModel = postViewModel,
                loginViewModel = loginViewModel,
                postParticipantsViewModel = postParticipantsViewModel,
                chatViewModel = chatViewModel)
        }
        composable("request") { RequestScreen(navController) }
        composable("creatematch") { CreateMatch(navController,
            postViewModel,
            loginViewModel,
            profileViewModel,
            chatViewModel) }
        composable("profile") { ProfileScreen(navController, profileViewModel, loginViewModel) }
        composable("settings") { SettingsScreen(navController, profileViewModel, loginViewModel) }
        composable(
            "message/{chatId}/{userName}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.IntType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt("chatId") ?: -1
            val userName = backStackEntry.arguments?.getString("userName") ?: "Unknown"
            TextMessage(navController = navController, chatId = chatId, userName = userName, chatViewModel, loginViewModel)
        }

    }
}