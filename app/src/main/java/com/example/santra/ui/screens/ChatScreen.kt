

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.entities.GroupChatsTable
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen(navController: NavController,
               chatViewModel: ChatViewModel,
               loginViewModel: LoginViewModel
) {
    val groupChat by chatViewModel.getChatTable.observeAsState(emptyList())
    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()

    LaunchedEffect(loggedInStudentId) {
        loggedInStudentId?.let { id ->
            chatViewModel.getChatTablebyPostId(id)
        }
    }


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFF0B4100))
            ) {
                // Geri Dön Butonu
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Geri Dön",
                        tint = Color.Black,
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                    )
                }

                // Başlık
                Text(
                    text = "Sohbetlerim",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Sohbet Listesi
                if (groupChat.isEmpty()) {
                    Text(
                        text = "Henüz sohbet yok.",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(groupChat) { chat ->
                            ChatListItem(chat = chat, onClick = {
                                navController.navigate("message/${chat.id}/${chat.groupName}")
                            })
                        }
                    }
                }
                Divider(
                    color = Color(0xFF000000), // Çizgi rengi
                    thickness = 1.dp, // Çizgi kalınlığı
                    //modifier = Modifier.padding(horizontal = 16.dp) // Yatay boşluk
                )
            }
        }
    )
}




@Composable
fun ChatListItem(chat: GroupChatsTable, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Resmi
        Image(
            painter = painterResource(id = R.drawable.account_circle),
            contentDescription = "Profil Resmi",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(36.dp)),
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            // Kullanıcı Adı
            Text(
                text = chat.groupName?:"Bilinmeyen Grup",
                fontSize = 25.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
            // Son Mesaj
            Text(
                text = chat.lastMessage?: " ",
                fontSize = 16.sp,
                color = Color(0xFF000000),
                maxLines = 1,
                modifier = Modifier.padding(top = 6.dp),
                fontWeight = FontWeight.W500
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Mesaj Zamanı
        Text(
            text = if (chat.lastMessageTime != null) formatTime(chat.lastMessageTime) else " ",
            fontSize = 12.sp,
            color = Color.Gray,
        )
    }
}

fun formatTime(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(timeInMillis)
}