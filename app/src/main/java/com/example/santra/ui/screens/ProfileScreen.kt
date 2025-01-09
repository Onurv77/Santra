package com.example.santra.ui.screens

import android.content.Context
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.text.TextStyle
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
import com.example.santra.ui.components.SettingsContent
import com.example.santra.ui.components.TopBarContent
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel, loginViewModel: LoginViewModel) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var avatar by remember { mutableStateOf<ByteArray?>(null) }

    val context = LocalContext.current


    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    val profile by profileViewModel.profile.observeAsState()

    LaunchedEffect(profile) {
        profile?.let {
            userName = it.userName ?: "Kullanıcı adı yok"
            mail = it.mail ?: "E-posta yok"
            phone = it.phone ?: "Telefon numarası yok"
            avatar = it.avatar
        }
    }

    LaunchedEffect(loggedInStudentId) {
        loggedInStudentId?.let { id ->
            profileViewModel.fetchProfile(studentId = id)
        }
    }

    BackgroundImage()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SettingsContent(navController)
        }
    ) {
        Scaffold(containerColor = Color.Transparent,
            topBar = { TopBarContent(drawerState, scope) },
            bottomBar = { BottomBarContent(navController) }) { paddingValues ->
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
                            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.outlinedCardColors(Color.White)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                                .padding(30.dp)
                        ) {

                            ProfileAvatar(avatar = avatar, context = context)

                            Image(
                                painter = painterResource(R.drawable.rank5_3),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .height(60.dp)
                                    .weight(1f)
                            )
                            Text(
                                text = userName,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 0.dp)
                                    .weight(1f),
                                style = MaterialTheme.typography.headlineMedium,
                                fontSize = 25.sp
                            )
                        }



                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "Mail: $mail",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text ="Telefon: $phone",
                            modifier = Modifier
                                .padding(top = 50.dp, start = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
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
    )
}


@Preview
@Composable
fun pre(){
    val db = AppDatabase.getDatabase(LocalContext.current)
    ProfileScreen(rememberNavController(), ProfileViewModel(db.santraDao()), LoginViewModel(db.santraDao()))
}