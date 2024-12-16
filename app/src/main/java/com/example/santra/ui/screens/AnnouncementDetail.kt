package com.example.santra.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AnnouncementDetailScreen(navController: NavController, announcementId: String?) {
    Text(
        text = "Announcement Detayı: $announcementId", // Burada ilan detaylarını gösterebilirsiniz
        modifier = Modifier.padding(16.dp)
    )
}