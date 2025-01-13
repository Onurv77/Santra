package com.example.santra.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.santra.R
import com.example.santra.data.entities.GroupChatsTable
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.PostParticipantsViewModel
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AnnouncementDetailScreen(navController: NavController,
                             announcementId: String?,
                             viewModel: PostViewModel,
                             postParticipantsViewModel: PostParticipantsViewModel,
                             loginViewModel: LoginViewModel,
                             chatViewModel: ChatViewModel) {


    val postId = announcementId?.toIntOrNull() ?: 0
    val post by viewModel.getPostWithProfileById(postId).observeAsState()
    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    var isParticipant by remember { mutableStateOf(false) }
    var ownRoom by remember { mutableStateOf(false) }
    val loggedInUserName by loginViewModel.loggedInUserName.observeAsState()
    var isFull by remember { mutableStateOf(false) }

    val context = LocalContext.current


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(postId) {
        postParticipantsViewModel.refreshParticipants(postId)
    }

    BackgroundImage()

    post?.let { postDetails ->

        LaunchedEffect(loggedInStudentId) {

            loggedInStudentId?.let { id ->
                if(postDetails.postStudentId == id) {
                    ownRoom = true
                }
            }

        }

        LaunchedEffect(Unit) {
            isParticipant = postParticipantsViewModel.isUserParticipant(
                postId = postDetails.postId,
                studentId = loggedInStudentId!!
            )
            isFull = postParticipantsViewModel.isFull(postDetails.postId, postDetails.postParticipantNum)
        }


        val participant by postParticipantsViewModel.participantUsernames.observeAsState(
            emptyList()
        )

        val formattedDate = postDetails.postDate?.let {
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(it))
        } ?: "Belirtilmemiş"

        val avatarUri = byteArrayToUri(context, postDetails.profileAvatarUrl)


            Scaffold(
                containerColor = Color.Transparent,
                topBar = { TopBarContent(navController) },
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
                                .padding(30.dp)
                                .fillMaxSize(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.outlinedCardColors(Color.White)
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))

                            Image(
                                contentDescription = null,
                                painter = rememberAsyncImagePainter(
                                    avatarUri ?: R.drawable.account_circle
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = postDetails.profileUsername ?: "Bilinmeyen Kullanıcı",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(bottom = 30.dp, top = 5.dp),
                                style = MaterialTheme.typography.headlineLarge
                            )

                            Image(
                                painter = painterResource(R.drawable.rank5_3),
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Tarih: ${formattedDate ?: "Belirtilmemiş"}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(20.dp)
                            )


                            Text(
                                text = "İlan Açıklaması: ${postDetails.postDescription ?: "Açıklama Yok" }",
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Mevki: ${postDetails.postMevki ?: "Belirtilmemiş"}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(20.dp)
                            )

                            Text(
                                text = "Telefon: ${postDetails.profilePhone ?: "Belirtilmemiş"}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(start = 20.dp, bottom = 20.dp)
                            )

                            Column(
                                modifier = Modifier.padding(start = 30.dp, bottom = 35.dp)
                            ) {
                                Text(
                                    text = "Katılımcılar:",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                participant.forEachIndexed { index, name ->
                                    Text(
                                        text = "${index + 1}. $name",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }

                            if (!ownRoom) {
                                if (!isFull) {
                                    FilledTonalButton(
                                        onClick = {
                                            if (isParticipant) {

                                                postParticipantsViewModel.removeParticipant(
                                                    postId = postDetails.postId,
                                                    studentId = loggedInStudentId!!,
                                                    onSuccess = {
                                                        isParticipant = false
                                                        scope.launch {
                                                            postParticipantsViewModel.refreshParticipants(
                                                                postDetails.postId
                                                            )
                                                            isFull =
                                                                postParticipantsViewModel.isFull(
                                                                    postDetails.postId,
                                                                    postDetails.postParticipantNum
                                                                )
                                                            chatViewModel.removeGroupChatsTable(loggedInStudentId!!)
                                                        }
                                                    },
                                                    onFailure = {
                                                        //Hata kısmı
                                                    }
                                                )


                                            } else {
                                                postParticipantsViewModel.addParticipant(
                                                    postId = postDetails.postId,
                                                    studentId = loggedInStudentId!!,
                                                    userName = loggedInUserName!!,
                                                    maxParticipants = postDetails.postParticipantNum,
                                                    onSuccess = {
                                                        isParticipant = true
                                                        scope.launch {
                                                            postParticipantsViewModel.refreshParticipants(
                                                                postDetails.postId
                                                            )
                                                            isFull =
                                                                postParticipantsViewModel.isFull(
                                                                    postDetails.postId,
                                                                    postDetails.postParticipantNum
                                                                )
                                                            chatViewModel.insertGroupChatsTable(
                                                                GroupChatsTable(
                                                                    postId = postDetails.postId,
                                                                    studentId = loggedInStudentId!!,
                                                                    groupName = postDetails.postGroupName,
                                                                    lastMessage = null,
                                                                    lastMessageTime = null
                                                                )
                                                            )
                                                        }
                                                    },
                                                    onFailure = {
                                                        //Hata kısmı
                                                    }
                                                )

                                            }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .width(100.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .padding(top = 70.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            Color(0XFF5091B1)
                                        )
                                    ) {
                                        Text(
                                            if (isParticipant) "Geri Al" else "Katıl",
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    }
                                } else {
                                    if (isParticipant) {
                                        FilledTonalButton(
                                            onClick = {
                                                postParticipantsViewModel.removeParticipant(
                                                    postId = postDetails.postId,
                                                    studentId = loggedInStudentId!!,
                                                    onSuccess = {
                                                        isParticipant = false
                                                        scope.launch {
                                                            postParticipantsViewModel.refreshParticipants(
                                                                postDetails.postId
                                                            )
                                                            isFull =
                                                                postParticipantsViewModel.isFull(
                                                                    postDetails.postId,
                                                                    postDetails.postParticipantNum
                                                                )
                                                            chatViewModel.removeGroupChatsTable(loggedInStudentId!!)
                                                        }
                                                    },
                                                    onFailure = {
                                                        // Hata kısmı
                                                    }
                                                )
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier
                                                .width(100.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .padding(top = 70.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                Color(0XFF5091B1)
                                            )
                                        ) {
                                            Text(
                                                text = "Geri Al",
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    } else {

                                        FilledTonalButton(
                                            onClick = {},
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier
                                                .width(100.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .padding(top = 70.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                Color(0XFF5091B1)
                                            )
                                        ) {
                                            Text(
                                                text = "Dolu",
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

    }
}

fun byteArrayToUri(context: Context, byteArray: ByteArray?): Uri? {
    if (byteArray == null) return null


    val file = File(context.cacheDir, "avatar_image.jpg")
    try {
        FileOutputStream(file).use { fos ->
            fos.write(byteArray)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }


    return Uri.fromFile(file)
}