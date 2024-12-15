package com.example.santra.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.data.dao.SantraDao

@Composable
fun LoginScreen(navController: NavController, db:AppDatabase) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Şifre göster/gizle için

    val santraDao = db.santraDao()

    val coroutineScope = rememberCoroutineScope()




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.santrabackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(R.drawable.santralogo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            // Başlık
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    //.background(color = Color(0xFF31B700))
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Hoşgeldiniz",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Kullanıcı Adı OutlinedTextField
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Kullanıcı Adı") },
                placeholder = { Text("Kullanıcı adınızı girin") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp), // Oval kenarlar
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF31B700),
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.White
                )
            )

            // Şifre OutlinedTextField
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Şifre") },
                placeholder = { Text("Şifrenizi girin") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Şifreyi Gizle" else "Şifreyi Göster"
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF31B700),
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Giriş Yap ve Kaydol Butonları
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF31B700))
                ) {
                    Text("Giriş Yap")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF31B700))
                ) {
                    Text("Kaydol")
                }
            }
        }
    }
}

