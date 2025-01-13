package com.example.santra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.example.santra.R
import com.example.santra.data.entities.PostWithProfile
import com.example.santra.ui.screens.byteArrayToUri

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(navController: NavController, posts: List<PostWithProfile>) {

    // PagerState: Pager'ın durumunu kontrol etmek için
    val pagerState = rememberPagerState()

    val context = LocalContext.current

    // Pager (HorizontalPager)
    HorizontalPager(
        count = posts.size, // Toplam ilan sayısı
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page -> // Her sayfa için içerik oluşturuluyor
        val post = posts[page]
        val timeRemaining = post.postDate?.let { it - System.currentTimeMillis() } ?: 0L
        val isAboutToExpire = timeRemaining in 0..3_600_000
        val avatarUri = byteArrayToUri(context, post.profileAvatarUrl)
        // Tek bir ilanı kart şeklinde gösteriyoruz
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (isAboutToExpire) {
                    Row (
                        modifier = Modifier
                            .background(Color.Red, shape = RoundedCornerShape(25.dp))
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.timer_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Text(
                            text = "Süresi Doluyor!",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                // Profil Resmi
                Image(
                    painter = rememberAsyncImagePainter(
                        avatarUri ?: R.drawable.account_circle
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(125.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                )
                // Kullanıcı Adı
                Text(
                    text = post.profileUsername ?: "Bilinmeyen Kullanıcı",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                // Açıklama
                Text(
                    text = post.postDescription ?: "Açıklama Yok",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                // Detay Butonu
                Button(
                    onClick = { navController.navigate("announcement_detail/${post.postId}") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text("Detayları Gör")
                }
            }
        }
    }
}


