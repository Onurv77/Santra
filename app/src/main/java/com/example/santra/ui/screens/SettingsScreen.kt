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
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent

@Composable
fun SettingsScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Telefon Numarası Değiştir", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        focusedContainerColor = Color(0xFF00FF00),
                        unfocusedTextColor = Color(0xFFFFFFFF)
                    ),
                )
                ActionButton("Onayla")

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
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Güncel şifre", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        focusedContainerColor = Color(0xFF00FF00),
                        unfocusedTextColor = Color(0xFFFFFFFF)
                    )
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Yeni şifre", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        focusedContainerColor = Color(0xFF00FF00),
                        unfocusedTextColor = Color(0xFFFFFFFF)
                    )
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Yeni şifreyi onayla", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .shadow(4.dp, shape = RoundedCornerShape(50.dp), ambientColor = Color.Gray, spotColor = Color.DarkGray)
                        .blur(0.75.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFFFFFFF),
                        focusedContainerColor = Color(0xFF00FF00),
                        unfocusedTextColor = Color(0xFFFFFFFF)
                    )
                )
                ActionButton("Değiştir")

                // Çıkış Yap
                ExitApp()
            }
        }
    }
}

@Composable
fun ActionButton(text: String) {
    Button(
        onClick = { /* TODO: Add your action */ },
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
    SettingsScreen(navController)
}