package com.example.santra.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
            Box(modifier = Modifier.fillMaxSize()) {

                LazyColumnContent(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )


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