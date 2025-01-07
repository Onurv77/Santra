package com.example.santra.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R

@Composable
fun ChatScreen(navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Sohbetler") },
                backgroundColor = Color(0xFF128C7E),
                contentColor = Color.White,
                navigationIcon = {
                    // Geri dön butonu
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "Geri Dön"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Sohbet Listesi
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(dummyChatList) { chat ->
                        ChatListItem(chat = chat, onClick = {
                            // Sohbete tıklandığında bir mesajlaşma ekranına yönlendirilebilir
                            navController.navigate("message/${chat.id}/${chat.userName}")
                        })
                    }
                }
            }
        }
    )
}

@Composable
fun ChatListItem(chat: ChatItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Resmi
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profil Resmi",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Kullanıcı Adı
            Text(
                text = chat.userName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            // Son Mesaj
            Text(
                text = chat.lastMessage,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Mesaj Zamanı
        Text(
            text = chat.time,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

data class ChatItem(
    val id: Int,
    val userName: String,
    val lastMessage: String,
    val time: String
)

// Dummy Chat List
val dummyChatList = listOf(
    ChatItem(1, "Ahmet", "Merhaba, nasılsın?", "14:30"),
    ChatItem(2, "Mehmet", "Bugün görüşelim mi?", "13:45"),
    ChatItem(3, "Elif", "Teşekkürler!", "12:10"),
    ChatItem(4, "Zeynep", "Yarınki etkinlik ne zaman?", "09:15"),
    ChatItem(5, "Ali", "Görüşürüz!", "Dün")
)

@Preview
@Composable
fun PreviewChat() {
    val navController = rememberNavController()
    ChatScreen(navController)
}


