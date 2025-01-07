package com.example.santra.ui.screens

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.ui.components.BackgroundImage

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    var StudentNumber by remember { mutableStateOf("") }
    var StudentPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Şifre göster/gizle için
    val context = LocalContext.current
    val loginState by loginViewModel.loginState.observeAsState()

    var toastShown by remember { mutableStateOf(false) }


    loginState?.let { isLoggedIn ->
        if (isLoggedIn) {

            if (!toastShown) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
                toastShown = true
            }
        } else {
            if (!toastShown) {
                Toast.makeText(
                    context,
                    "Kullanıcı Numarası veya Şifresi geçersiz",
                    Toast.LENGTH_LONG
                ).show()
                toastShown = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {

        BackgroundImage()

        // Logo
        Image(
            alignment = Alignment.TopCenter,
            painter = painterResource(R.drawable.santralogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            colorFilter = ColorFilter.tint(Color(0x8332C917))

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                value = StudentNumber,
                onValueChange = { StudentNumber = it },
                label = { Text("Öğrenci Numarası") },
                placeholder = { Text("Öğrenci numaranızı giriniz") },
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
                value = StudentPassword,
                onValueChange = { StudentPassword = it },
                label = { Text("Şifre") },
                placeholder = { Text("Şifrenizi giriniz") },
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
                    onClick = {
                        if (StudentNumber.isNotBlank() && StudentPassword.isNotBlank()) {
                            loginViewModel.login(StudentNumber, StudentPassword)
                            toastShown = false
                        } else {
                            Toast.makeText(
                                context,
                                "Kullanıcı Numarası veya Şifresi boş",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF31B700))
                ) {
                    Text("Giriş Yap")
                }


                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier.weight(1f),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            Color(
                                0xFF31B700
                            )
                        )
                    ) {
                        Text("Kaydol")
                    }

            }
        }
    }
}

//@Preview
//@Composable
//fun preLogin(){
//    val db = AppDatabase.getDatabase(LocalContext.current)
//    LoginScreen(rememberNavController(), LoginViewModel(db.santraDao()))
//}