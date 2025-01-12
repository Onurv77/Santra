package com.example.santra.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.entities.MessagesTable
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun TextMessage(
    navController: NavController, chatId: Int, userName: String, chatViewModel: ChatViewModel,
    loginViewModel: LoginViewModel
) {
    // Mesaj listesini ve yeni mesaj değerini tutan durum değişkenleri
//    val messages =
//        remember { mutableStateListOf<Pair<String, Boolean>>() } // (Mesaj, Kullanıcı mı?)

    val messages = remember { mutableStateListOf<MessagesTable>() }
    var newMessage by remember { mutableStateOf("") }
    val currentTimeMillis = System.currentTimeMillis()
    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    val messagesTable by chatViewModel.getMessagesTableByChatId.observeAsState(emptyList())
    val otherChatGroup by chatViewModel.getOtherGroupChat.observeAsState(emptyList())
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
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Başlık ve geri butonu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { navController.navigateUp() }) {
                Text(text = "Geri")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Mesaj Listesi
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.2f)),
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
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                placeholder = { Text("Mesaj yaz...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true
            )
            Button(
                onClick = {
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
                        scope.launch {
                            chatViewModel.refreshMessagesTable(userName)
                        }

                        Log.d("gonderilen mesaj", messagesTable.toString())

                        //messages.add(newMessage to true) // Yeni mesajı listeye ekle
                        newMessage = "" // Mesaj kutusunu temizle
                    }
                }
            ) {
                Text("Gönder")
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
        Column {
            Text(text = name, modifier = Modifier.align(if (isSentByUser) Alignment.End
            else Alignment.Start))
            Box(
                modifier = Modifier
                    .background(
                        color = if (isSentByUser) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .widthIn(max = 250.dp)
            ) {
                Text(
                    text = message,
                    color = Color.White
                )
            }
        }
    }
}




/*
@Composable
fun TextMessage(navController: NavController, chatId: Int, userName: String) {
    // Mesaj listesini ve yeni mesaj değerini tutan durum değişkenleri
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() } // (Mesaj, Kullanıcı mı?)
    var newMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Başlık ve Geri Dön Butonu
        androidx.compose.material.TopAppBar(
            title = { Text(text = userName, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                androidx.compose.material.IconButton(onClick = { navController.navigateUp() }) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.arrow_left), // Geri dön simgesi
                        contentDescription = "Geri Dön"
                    )
                }
            },
            backgroundColor = Color(0xFF128C7E),
            contentColor = Color.White
        )

        // Mesaj Listesi
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(10) { index ->
                    if (index % 2 == 0) {
                        ChatBubble(message = "Merhaba! Bu bir mesaj.", isSentByUser = true)
                    } else {
                        ChatBubble(message = "Merhaba! Cevap mesajı.", isSentByUser = false)
                    }
                }
            }
        }

        // Mesaj Gönderme Kutusu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { newText -> messageText = newText },
                placeholder = { Text("Mesaj yaz...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true
            )
            Button(onClick = {
                // Mesaj gönderme işlemi
                println("Gönderilen Mesaj: $messageText")
                messageText = "" // Mesaj gönderildikten sonra alanı temizle
            }) {
                Text("Gönder")
            }
        }
    }
}



@Composable
fun ChatBubble(message: String, isSentByUser: Boolean) {
    Row(
        horizontalArrangement = if (isSentByUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isSentByUser) Color.Blue else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = message,
                color = Color.White
            )
        }
    }
}
*/




