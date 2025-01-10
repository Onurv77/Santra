package com.example.santra.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBarContent(navController: NavController) {
    Column(
        modifier = Modifier.padding(top = 25.dp)
    ) {
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
                    modifier = Modifier.size(135.dp),
                    tint = Color(0xFF7BB928)
                )
                Icon(
                    painter = painterResource(id = R.drawable.settings_icon),
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            navController.navigate("settings")
                        },
                    tint = Color(0xFFFFFFFF)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)

        )
    }
}

@Preview
@Composable
fun PreTopBar() {
    val navController = rememberNavController()
    TopBarContent(navController)
}