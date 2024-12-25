package com.example.santra.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent

@Composable
fun AnnouncementDetailScreen(navController: NavController, announcementId: String?) {
    val toNumber = announcementId?.toIntOrNull()!!

        Image(
            painter = painterResource(R.drawable.santra_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(16.dp),
            contentScale = ContentScale.FillBounds
        )


        Scaffold(
            containerColor = Color.Transparent,
            topBar = { TopBarContent() },
            bottomBar = { BottomBarContent() }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(30.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.outlinedCardColors(Color.White)
                    ) {
                        Image(
                            contentDescription = null,
                            painter = painterResource(R.drawable.account_circle),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Text(text = "Username", modifier = Modifier.align(Alignment.CenterHorizontally))

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "${toNumber+1}. İlan",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 60.dp)
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "Bu, ilan açıklaması. Detaylı bilgi için tıklayın.",
                            modifier = Modifier
                                .padding(20.dp)
                                .padding(bottom = 50.dp),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                            modifier = Modifier.padding(50.dp)
                        ) {

                            FilledTonalButton(onClick = { }) {
                                Text("Katıl", style = MaterialTheme.typography.labelLarge)
                            }

                            Spacer(modifier = Modifier.width(50.dp))

                            FilledTonalButton(onClick = { navController.navigate("home") }) {
                                Text("Geri Dön", style = MaterialTheme.typography.labelLarge)
                            }

                        }
                    }

                }
            }
        }
    }

@Preview
@Composable
fun preAnnoun(){
    val navController = rememberNavController()
    AnnouncementDetailScreen(navController,"1")
}