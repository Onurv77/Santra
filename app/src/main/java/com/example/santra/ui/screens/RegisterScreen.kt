package com.example.santra.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.domain.viewmodels.RegisterViewModel
import com.example.santra.ui.components.BackgroundImage


@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel) {

    val context = LocalContext.current

    var studentId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var studentPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var avatar by remember { mutableStateOf<ByteArray?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
        uri?.let {
            avatar = context.contentResolver.openInputStream(it)?.readBytes()
        }
    }


    val studentIdPattern = "^[0-9]{11}$".toRegex()
    val emailPattern = "^[a-zA-Z0-9._%+-]+@std\\.yeditepe\\.edu\\.tr$".toRegex()
    val phonePattern = "^[0-9]{11}$".toRegex()

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

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Resim Seçmek İçin Tıklayınız",
                modifier = Modifier.padding(top = 20.dp),
                color = Color.White
            )
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.account_circle),
                    contentDescription = "Unselected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { launcher.launch("image/*") }

                )
            }


            val avatarToSend = if (selectedImageUri != null) {
                context.contentResolver.openInputStream(selectedImageUri!!)?.readBytes()
            } else {
                null
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Öğrenci Numarasını Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = studentId,
                onValueChange = { studentId = it },
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
                onValueChange = { userName = it },
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
                onValueChange = { mail = it },
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
                onValueChange = { phone = it },
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
                onValueChange = { studentPassword = it },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        when {
                            studentId.isEmpty() || userName.isEmpty() || mail.isEmpty() || phone.isEmpty() || studentPassword.isEmpty() -> {
                                Toast.makeText(context, "Lütfen gerekli alanları doldurun", Toast.LENGTH_LONG).show()
                            }
                            !studentId.matches(studentIdPattern) -> {
                                Toast.makeText(context, "Öğrenci numarası geçerli değil", Toast.LENGTH_LONG).show()
                            }
                            !mail.matches(emailPattern) -> {
                                Toast.makeText(context, "Mail adresi geçerli değil", Toast.LENGTH_LONG).show()
                            }
                            !phone.matches(phonePattern) -> {
                                Toast.makeText(context, "Telefon numarası geçerli değil", Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                registerViewModel.registerUser(
                                    studentId = studentId,
                                    studentPassword = studentPassword,
                                    mail = mail,
                                    phone = phone,
                                    userName = userName,
                                    avatar = avatarToSend
                                )
                                navController.navigate("success")
                            }
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
fun PreviewRegister() {
    val navController = rememberNavController()
    val db = AppDatabase.getDatabase(LocalContext.current)
    RegisterScreen(navController, RegisterViewModel(db.santraDao()))
}
