package com.example.santra.ui.components

import android.app.Activity
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsContent(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Ana Sayfa",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { navController.navigate("home") }
        )
        Text(
            text = "Profil",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Profil */ }
        )
        Text(
            text = "Ayarlar",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Ayarlar */ }
        )
    CikisYapText()
    }
}
@Composable
fun CikisYapText() {
    var showDialog by remember { mutableStateOf(false) }
var current = LocalContext.current
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Çıkış Yap") },
            text = { Text(text = "Uygulamadan çıkmak istediğinize emin misiniz?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    (current as? Activity)?.finishAffinity()
                }) {
                    Text(text = "Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Hayır")
                }
            }
        )
    }
    Text(
        text = "Çıkış yap",
        color = Color.Red,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                showDialog = true // Diyaloğu aç
            }
    )
}

@Preview
@Composable
fun PreviewSettings() {
    val navController = rememberNavController()
    SettingsContent(navController)
}