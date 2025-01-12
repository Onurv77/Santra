package com.example.santra.ui.screens

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.ProfileViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent

@Composable
fun SettingsScreen(navController: NavController,
                   profileViewModel: ProfileViewModel,
                   loginViewModel: LoginViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var phone by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword1 by remember { mutableStateOf("") }
    var newPassword2 by remember { mutableStateOf("") }
    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()

    Box {
        // Arka plan görüntüsü
        BackgroundImage()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = { TopBarContent(navController) },
            bottomBar = { BottomBarContent(navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Hesap Ayarları Bölümü
                Image(
                    painter = painterResource(id = R.drawable.account_settings),
                    contentDescription = "Hesap Ayarları",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {phone = it},
                    placeholder = { Text("Telefon Numarası Değiştir", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000)
                    ),
                )
                ActionButton("Onayla"){
                    profileViewModel.updatePhoneFromProfileTableByStudentId(phone,loggedInStudentId!!)
                }

                Spacer(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White)
                )

                // Şifre Ayarları Bölümü
                Image(
                    painter = painterResource(id = R.drawable.password_settings),
                    contentDescription = "Şifre Ayarları",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                )
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = {oldPassword = it},
                    placeholder = { Text("Güncel şifre", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000)
                    )
                )
                OutlinedTextField(
                    value = newPassword1,
                    onValueChange = {newPassword1 = it},
                    placeholder = { Text("Yeni şifre", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000)
                    )
                )
                OutlinedTextField(
                    value = newPassword2,
                    onValueChange = {newPassword2 = it},
                    placeholder = { Text("Yeni şifreyi onayla", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000)
                    )
                )
                ActionButton("Değiştir") {
                    if(newPassword1 == newPassword2) {
                        profileViewModel.updatePasswordFromLoginTableByStudentId(oldPassword,newPassword2, loggedInStudentId!!)
                    }
                }

                // Çıkış Yap
                ExitApp()
            }
        }
    }
}

@Composable
fun ActionButton(text: String,onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .width(150.dp)
            .height(60.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF56AE33)
        ),
        shape = RoundedCornerShape(28.dp),
    ) {
        Text(text = text, color = Color.White, fontSize = 21.sp)
    }
}

@Composable
fun ExitApp() {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Çıkış Yap") },
            shape = MaterialTheme.shapes.medium,
            text = { Text(text = "Uygulamadan çıkış yapmak istediğinize emin misiniz?") },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End // Düğmeleri sağa hizala
                ) {
                    TextButton(
                        onClick = {
                            showDialog = false
                            (context as? Activity)?.finishAffinity()
                        },
                        modifier = Modifier.padding(end = 8.dp) // "Evet" butonu
                    ) {
                        Text(text = "Evet", color = Color(0xFFB71C1C))
                    }
                    TextButton(onClick = { showDialog = false }) { // "Hayır" butonu
                        Text(text = "Hayır", color = Color.Gray)
                    }
                }
            }
        )
    }

    Button(
        onClick = { showDialog = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB71C1C),
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(150.dp)
            .height(60.dp)
            .padding(vertical = 8.dp),
    ) {
        Text(text = "Çıkış yap", color = Color.White, fontSize = 20.sp)
    }
}


@Preview
@Composable
fun SettingsPreview() {
    val navController = rememberNavController()
    val db = AppDatabase.getDatabase(LocalContext.current)
    val santradao = db.santraDao()
    SettingsScreen(navController, ProfileViewModel(santradao), LoginViewModel(santradao))
}