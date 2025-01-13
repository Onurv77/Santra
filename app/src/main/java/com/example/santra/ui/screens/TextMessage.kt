package com.example.santra.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.data.entities.MessagesTable
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun TextMessage(
    navController: NavController, chatId: Int, userName: String, chatViewModel: ChatViewModel,
    loginViewModel: LoginViewModel
) {
    val messages = remember { mutableStateListOf<MessagesTable>() }
    var newMessage by remember { mutableStateOf("") }
    val currentTimeMillis = System.currentTimeMillis()
    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    val messagesTable by chatViewModel.getMessagesTableByChatId.observeAsState(emptyList())
    val scope = rememberCoroutineScope()

    LaunchedEffect(userName) {
        chatViewModel.getMessagesTableByChatId(userName)
    }

    LaunchedEffect(messagesTable) {
        messages.clear()
        messages.addAll(messagesTable)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF013220)) // Koyu yeşil arka plan
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0B4100)) // Daha koyu yeşil tonunda bir arka plan
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back), // Geri butonu ikonu
                contentDescription = "Geri Dön",
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .clickable { navController.navigateUp() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = userName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 45.dp, top = 5.dp)
                    .height(40.dp)
            )
        }

        // Mesaj Listesi
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF002200)), // Daha açık bir yeşil ton
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messagesTable) { message ->
                    val isSentByUser = message.senderStudentId == loggedInStudentId
                    val userName by chatViewModel.getUserNameFromLoginTable(message.senderStudentId).observeAsState("")
                    ChatBubble(message = message.messageContent ?: "", isSentByUser = isSentByUser, userName)
                }
            }
        }

        // Mesaj Gönderme Kutusu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF56AE33))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                placeholder = { Text("Mesaj yaz...", color = Color.Gray, fontSize = 14.sp) },
                modifier = Modifier
                    .weight(0.75f)
                    .height(52.dp)
                    .padding(end = 18.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF56AE33),
                    unfocusedIndicatorColor = Color.Gray
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .height(42.dp)
                    .background(Color(0xFF5091B1), shape = RoundedCornerShape(15.dp))
                    .clickable {
                        if (newMessage.isNotBlank()) {
                            chatViewModel.insertMessageTable(
                                MessagesTable(
                                    chatId = chatId,
                                    groupName = userName,
                                    senderStudentId = loggedInStudentId!!,
                                    messageContent = newMessage,
                                    timestamp = currentTimeMillis
                                )
                            )
                            chatViewModel.updateLastTimeAndMessageFromGroupTableByPostId(newMessage, currentTimeMillis, userName)
                            scope.launch {
                                chatViewModel.refreshMessagesTable(userName)
                                chatViewModel.getMessagesTableByChatId(userName)
                            }
                            newMessage = ""
                        }
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Gönder",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Composable
fun ChatBubble(message: String, isSentByUser: Boolean, name: String) {
    Row(
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = if (isSentByUser) Alignment.End else Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Chat Bubble Container
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth()
                    .heightIn(min = 48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                // Message content inside the bubble
                Text(
                    text = message,
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .align(Alignment.CenterStart)
                        .wrapContentSize()
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewText(){
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    TextMessage(rememberNavController(), chatId = 1, userName = "HenryGroup", ChatViewModel(db.santraDao()), LoginViewModel(db.santraDao()))
}

@Preview
@Composable
fun ChatBubblePreview() {
    ChatBubble(message = "Merhaba, nasılsın?", isSentByUser = false, name = "Mert")
}




