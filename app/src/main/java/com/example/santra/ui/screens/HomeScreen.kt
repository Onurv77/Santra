package com.example.santra.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.data.entities.PostWithProfile
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.Pager
import com.example.santra.ui.components.TopBarContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, viewModel: PostViewModel) {
    val postsWithProfile by viewModel.postsWithProfile.observeAsState(emptyList())
    val sortedPosts = postsWithProfile.sortedBy { it.postDate }

    LaunchedEffect(Unit) {
        viewModel.deleteExpiredPosts()
        viewModel.startExpirationCheck()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerWidth = 107.dp // Çekmecenin genişliği
    val drawerOffset by animateDpAsState(
        targetValue = if (drawerState.isOpen) 0.dp else drawerWidth
    )
    BackgroundImage()

    Box {

        Surface(modifier = Modifier.fillMaxSize(),
            color = Color.Transparent) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = { TopBarContent(navController) },
                    bottomBar = { BottomBarContent(navController) }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Content(navController, drawerState, scope, sortedPosts)
                    }
                }
            }
        }

        LaunchedEffect(drawerState.currentValue) {
            if (drawerState.isClosed) {
                scope.launch { drawerState.close() }
            } else {
                scope.launch { drawerState.open() }
            }
        }
    }
}

@Composable
fun Content(navController: NavController, drawerState: DrawerState, scope: CoroutineScope, postsWithProfile: List<PostWithProfile>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        //WarningMessage()

        if (postsWithProfile.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Henüz bir gönderi yok.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            Pager(navController, postsWithProfile) // Yeni LazyRowContent fonksiyonu
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun WarningMessage() {
    Box(
        modifier = Modifier
            .background(Color.Red, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .width(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Süresi Doluyor!",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.timer_icon),
                contentDescription = "Timer",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    val navController = rememberNavController()
    val db = AppDatabase.getDatabase(LocalContext.current)
    val santraDao = db.santraDao()
    HomeScreen(navController,PostViewModel(santraDao))
}