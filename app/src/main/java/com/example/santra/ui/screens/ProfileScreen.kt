package com.example.santra.ui.screens

import android.content.Context
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.ProfileViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel, loginViewModel: LoginViewModel) {
    var userName by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }
    var newAboutMe by remember { mutableStateOf("") }
    var isEditingAboutMe by remember { mutableStateOf(false) }
    var avatar by remember { mutableStateOf<ByteArray?>(null) }
    var toastMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    val profile by profileViewModel.profile.observeAsState()

    LaunchedEffect(profile) {
        profile?.let {
            userName = it.userName ?: "Kullanıcı adı yok"
            avatar = it.avatar
            aboutMe = it.aboutMe
        }
    }

    LaunchedEffect(loggedInStudentId) {
        loggedInStudentId?.let { id ->
            profileViewModel.fetchProfile(studentId = id)
        }
    }

    BackgroundImage()

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
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileAvatar(avatar = avatar, context = context)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = userName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.rank5_3),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .height(50.dp)
                                    .weight(1f)
                                    .padding(bottom = 10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // "Hakkımda" Kısmı
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Hakkımda",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 45.dp),
                                fontWeight = FontWeight.W700,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )

                            IconButton(onClick = { isEditingAboutMe = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.aboutme_icon),
                                    contentDescription = "Save",
                                    tint = Color(0xFF000000),
                                    modifier = Modifier
                                        .size(10.dp)
                                        .weight(0.15f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (isEditingAboutMe) {
                            androidx.compose.material3.TextField(
                                value = newAboutMe,
                                onValueChange = { newAboutMe = it },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(35.dp)),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFFF0F0F0),
                                    unfocusedTextColor = Color.Black,
                                    cursorColor = Color.DarkGray,
                                    focusedIndicatorColor = Color(0xFFFFFFFF),
                                    unfocusedIndicatorColor = Color.Gray,
                                    focusedContainerColor = Color(0xFFD9D9D9)
                                )
                            )


                        } else {
                            Text(
                                text = aboutMe,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(18.dp))
                                    .fillMaxWidth()
                                    .padding(start = 5.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.W400
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Güncelle Butonu
                    FilledTonalButton(
                        onClick = {
                            isEditingAboutMe = false
                            try {
                                scope.launch {
                                    profileViewModel.updateAboutMe(newAboutMe, loggedInStudentId!!)
                                    profileViewModel.fetchProfile(studentId = loggedInStudentId!!)
                                }
                            } catch (e: Exception) {
                                toastMessage = "Bir hata oluştu: ${e.message}"
                            }
                        },
                        modifier = Modifier
                            .width(130.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.filledTonalButtonColors(Color(0xFF5091B1))
                    ) {
                        Text(text = "Güncelle", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W700), color = Color.White)
                    }
                    if (!isEditingAboutMe) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = aboutMe,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color(0xFFD9D9D9)) // Gri arka plan eklendi
                                .padding(10.dp)
                        )
                    }
                        Spacer(modifier = Modifier.height(20.dp))
                }
                if (toastMessage.isNotEmpty()) {
                    Toast.makeText(LocalContext.current, toastMessage, Toast.LENGTH_SHORT)
                        .show()
                    toastMessage = ""
                }
            }
        }
    }
}


@Composable
fun ProfileAvatar(avatar: ByteArray?, context: Context) {
    val avatarUri = avatar?.let { byteArrayToUri(context, it) }
    Image(
        contentDescription = null,
        painter = rememberAsyncImagePainter(
            avatarUri ?: R.drawable.account_circle
        ),
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .clip(CircleShape)
            .padding(bottom = 5.dp)
    )
}

@Preview
@Composable
fun PreviewProfileScreen(){
    val db = AppDatabase.getDatabase(LocalContext.current)
    ProfileScreen(rememberNavController(), ProfileViewModel(db.santraDao()), LoginViewModel(db.santraDao()))
}