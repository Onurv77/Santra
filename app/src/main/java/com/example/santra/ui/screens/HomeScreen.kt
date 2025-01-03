package com.example.santra.ui.screens

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
import com.example.santra.ui.components.DrawerContent
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

    BackgroundImage()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        }
    ) {

        Surface(modifier = Modifier.fillMaxSize(),
            color = Color.Transparent) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = { TopBarContent(drawerState, scope) },
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

        //NavigationArrows()
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
/*
@Composable
fun NavigationArrows() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "Previous",
            modifier = Modifier
                .size(40.dp)
                .clickable { /* Handle Previous */ }
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Next",
            modifier = Modifier
                .size(40.dp)
                .clickable { /* Handle Next */ }
        )
    }
}
*/
@Preview
@Composable
fun PreviewScreen() {
    val navController = rememberNavController()
    val db = AppDatabase.getDatabase(LocalContext.current)
    val santraDao = db.santraDao()
    HomeScreen(navController,PostViewModel(santraDao))
}

/*
@Composable
fun HomeTopBar() {
    Column {
        androidx.compose.material.TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home_screen_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(60.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.settings_icon),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { /* Handle click */ }
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}
*/


/*
@Composable
fun HomeBottomBar() {
    BottomNavigation(
        backgroundColor = Color(0xFF4CAF50),
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat_icon),
                    contentDescription = "Chat",
                    modifier = Modifier.size(50.dp)
                )
            },
            selected = false,
            onClick = { /* Handle click */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.add_announcement_1),
                    contentDescription = "Add",
                    modifier = Modifier.size(30.dp)
                )
            },
            selected = false,
            onClick = { /* Handle click */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "Profile",
                    modifier = Modifier.size(50.dp)
                )
            },
            selected = false,
            onClick = { /* Handle click */ }
        )
    }
}*/


/*package com.example.santra.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.ui.components.DrawerContent
import com.example.santra.ui.components.LazyColumnContent
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val drawerState = remember { androidx.compose.material3.DrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        },
        content = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column {
                    LazyColumnContent(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        navController = navController
                    )
                }

                Button(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .width(56.dp)
                        .height(56.dp)
                ) {
                    Text("☰")
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewScreen(){
    val navController = rememberNavController()
    HomeScreen(navController)
}*/