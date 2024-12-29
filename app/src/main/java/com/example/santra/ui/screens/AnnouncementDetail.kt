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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent
import kotlinx.coroutines.CoroutineScope

@Composable
fun AnnouncementDetailScreen(navController: NavController, announcementId: String?, viewModel: PostViewModel) {
    val postId = announcementId?.toIntOrNull() ?: 0
    val post by viewModel.getPostWithProfileById(postId).observeAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

        BackgroundImage()

    post?.let { postDetails ->
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
                            painter = rememberAsyncImagePainter(postDetails.profileAvatarUrl?: R.drawable.account_circle),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = postDetails.profileUsername ?: "Bilinmeyen Kullanıcı",
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
                            text = "Tarih: ${postDetails.postDate?: "Belirtilmemiş"}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = postDetails.postDescription?:"Açıklama Yok",
                            modifier = Modifier
                                .padding(20.dp)
                                .padding(bottom = 50.dp),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = "Mevki: ${postDetails.postMevki?: "Belirtilmemiş"}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )

                        Text(
                            text = "Telefon: ${postDetails.profilePhone?: "Belirtilmemiş"}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 20.dp)
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
}