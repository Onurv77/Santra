package com.example.santra.ui.components


import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.santra.R

@Composable
fun BottomBarContent() {
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
}