package com.example.santra.ui.components


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R

@Composable
fun BottomBarContent(navController: NavController) {

    Card(
        shape = RoundedCornerShape(topStart = 22.dp,
            topEnd = 22.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp)
    ) {
        BottomNavigation(
            backgroundColor = Color(0xFF4CAF50),
            elevation = 8.dp
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.futbol),
                        contentDescription = "home",
                        modifier = Modifier.size(45.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.White
                    )
                },
                selected = false,
                onClick = { navController.navigate("home") }
            )

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "profile",
                        modifier = Modifier.size(45.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.White
                    )
                },
                selected = false,
                onClick = { /* Handle click */ }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.request),
                        contentDescription = "request",
                        modifier = Modifier.size(45.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.White
                    )
                },
                selected = false,
                onClick = { navController.navigate("request") }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "chat",
                        modifier = Modifier.size(45.dp)
                            .align(Alignment.CenterVertically),
                        tint = Color.White
                    )
                },
                selected = false,
                onClick = { /* Handle click */ }
            )
        }
    }
}

@Preview
@Composable
fun preBot() {
    val navController = rememberNavController()
    BottomBarContent(navController)
}