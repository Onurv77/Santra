package com.example.santra.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent

@Composable
fun RequestScreen(navController: NavController) {
    
    BackgroundImage()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { TopBarContent() },
        bottomBar = { BottomBarContent(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    contentDescription = "matching",
                    painter = painterResource(R.drawable.rakipbul),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .clickable { }
                )

                Image(
                    contentDescription = null,
                    painter = painterResource(R.drawable.rakipbul),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .clickable { }
                )

            }
        }
    }

}

@Preview
@Composable
fun preReq() {
    val navController = rememberNavController()
    RequestScreen(navController)
}