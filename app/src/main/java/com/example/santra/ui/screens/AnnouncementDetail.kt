package com.example.santra.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun AnnouncementDetailScreen(navController: NavController, announcementId: String?) {
    val toNumber = announcementId?.toIntOrNull()!!

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

                        Text(
                            text = "Username",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 30.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.rank5_3),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "${toNumber+1}. İlan",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "Bu, ilan açıklaması. Detaylı bilgi için tıklayın.",
                            modifier = Modifier
                                .padding(20.dp)
                                .padding(bottom = 50.dp),
                            style = MaterialTheme.typography.bodySmall
                        )

                        FilledTonalButton(
                            onClick = { },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.buttonColors(
                                Color(0XFF5091B1)
                            )
                        ) {
                            Text("Katıl", style = MaterialTheme.typography.labelLarge)
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