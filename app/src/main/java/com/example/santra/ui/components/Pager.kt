package com.example.santra.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.example.santra.R
import com.example.santra.data.entities.PostWithProfile

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(navController: NavController, posts: List<PostWithProfile>) {

    // PagerState: Pager'ın durumunu kontrol etmek için
    val pagerState = rememberPagerState()

    // Pager (HorizontalPager)
    HorizontalPager(
        count = posts.size, // Toplam ilan sayısı
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page -> // Her sayfa için içerik oluşturuluyor
        val post = posts[page]
        val timeRemaining = post.postDate?.let { it - System.currentTimeMillis() } ?: 0L
        val isAboutToExpire = timeRemaining in 0..600_000
        Log.d("PostDate", "Post Date: ${post.postDate}")
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
                    Box(
                        modifier = Modifier
                            .background(Color.Red, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Süresi Doluyor!",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                // Profil Resmi
                Image(
                    painter = painterResource(R.drawable.account_circle),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(125.dp)
                        .align(Alignment.CenterHorizontally)
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

    // Sayfa Göstergesi (Opsiyonel)
    /*Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = Color.Blue,
            inactiveColor = Color.Gray
        )
    }*/

}

@Preview
@Composable
fun PreviewLazyRowContent() {
    // Örnek bir PostWithProfile listesi oluşturuyoruz
    val samplePosts = listOf(
        PostWithProfile(
            postId = 1,
            postStudentId = "123",
            postParticipantNum = 1,
            postDescription = "Bu bir açıklama örneğidir.",
            postDate = 1672531200000, // Örnek tarih (epoch time)
            postMevki = "Mevki1",
            profileUsername = "Kullanıcı1",
            profileAvatarUrl = null, // Avatar URL'si yoksa null kullanabilirsiniz
            profileRank = "Rütbe1",
            profilePhone = "1234567890"
        ),
        PostWithProfile(
            postId = 2,
            postStudentId = "123",
            postParticipantNum = 1,
            postDescription = "Başka bir açıklama örneği.",
            postDate = 1672617600000, // Örnek tarih (epoch time)
            postMevki = "Mevki2",
            profileUsername = "Kullanıcı2",
            profileAvatarUrl = null,
            profileRank = "Rütbe2",
            profilePhone = "0987654321"
        ),
        PostWithProfile(
            postId = 3,
            postStudentId = "123",
            postParticipantNum = 1,
            postDescription = "Bir başka açıklama örneği daha.",
            postDate = null, // Tarih bilinmiyorsa null
            postMevki = "Mevki3",
            profileUsername = "Kullanıcı3",
            profileAvatarUrl = null,
            profileRank = "Rütbe3",
            profilePhone = "1122334455"
        )
    )

    // Preview sırasında bir NavController oluşturuyoruz
    val navController = rememberNavController()

    Pager(navController = navController, posts = samplePosts)
}

