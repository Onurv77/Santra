package com.example.santra.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.RegisterViewModel
import com.example.santra.ui.components.BackgroundImage


@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    val current = LocalContext.current

    var studentId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var studentPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    BackgroundImage()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopStart
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp, bottom = 40.dp),
            painter = painterResource(R.drawable.santralogo),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Öğrenci Numarasını Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = studentId,
                onValueChange = {studentId = it},
                //label = { Text("Öğrenci Numarası")},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kullanıcı Adınızı Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = userName,
                onValueChange = {userName = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Öğrenci Mail Adresinizi Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = mail,
                onValueChange = {mail = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Telefon Numaranızı Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = phone,
                onValueChange = {phone = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Şifre Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = studentPassword,
                onValueChange = {studentPassword = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (mail != "" && studentId != "" && studentPassword != "" && phone != "" && userName != "") {
                            registerViewModel.registerUser(studentId = studentId, studentPassword = studentPassword,
                                mail = mail, phone = phone, userName = userName)
                            navController.navigate("login")
                        } else {
                            Toast.makeText(
                                current,
                                "Lütfen tüm alanları doldurun",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF31B700)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Kayıt Ol")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = { navController.navigate(route = "login") },
                    colors = ButtonDefaults.buttonColors(Color(0xFF31B700)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Geri Dön")
                }
            }
        }
    }
}

@Preview
@Composable
fun asdf() {
    val navController = rememberNavController()
    val db = AppDatabase.getDatabase(LocalContext.current)
    RegisterScreen(navController, RegisterViewModel(db.santraDao()))
}
