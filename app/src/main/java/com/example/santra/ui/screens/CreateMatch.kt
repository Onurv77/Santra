package com.example.santra.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.santra.data.entities.GroupChatsTable
import com.example.santra.data.entities.PostTable
import com.example.santra.domain.viewmodels.ChatViewModel
import com.example.santra.domain.viewmodels.LoginViewModel
import com.example.santra.domain.viewmodels.PostViewModel
import com.example.santra.domain.viewmodels.ProfileViewModel
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CreateMatch(
    navController: NavController,
    postViewModel: PostViewModel,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    chatViewModel: ChatViewModel
) {

    var groupName by remember { mutableStateOf("") }
    var participantNum by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var mevki by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long>(System.currentTimeMillis()) }
    var selectedTime by remember { mutableStateOf<Pair<Int, Int>>(Pair(15, 0)) }
    val studentId: String

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val loggedInStudentId by loginViewModel.loggedInStudentId.observeAsState()
    val loggedInStudentName by loginViewModel.loggedInUserName.observeAsState()
    val profile by profileViewModel.profile.observeAsState()

    studentId = profile?.studentId ?: loggedInStudentId ?: "bilinmiyor"

    var toastMessage by remember { mutableStateOf("") }

    val context = LocalContext.current


    val datePickerDialog = remember { DatePickerDialog(context) }
    datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        selectedDate = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }.timeInMillis
    }


    val timePickerDialog = remember {
        TimePickerDialog(context, { _, hourOfDay, minute ->
            selectedTime = Pair(hourOfDay, minute)
        }, selectedTime.first, selectedTime.second, true)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "İlan Oluştur",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.W700
                )

                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Grup Adı") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000),
                    ),
                    shape = RoundedCornerShape(15.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Açıklama") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000),
                    ),
                    shape = RoundedCornerShape(15.dp)
                )


                OutlinedTextField(
                    value = mevki,
                    onValueChange = { mevki = it },
                    label = { Text("Mevki") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000),
                    ),
                    shape = RoundedCornerShape(15.dp)
                )

                OutlinedTextField(
                    value = participantNum,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\$"))) {
                            participantNum = it
                        }
                    },
                    label = { Text("Katılım Sayısı") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedTextColor = Color(0xFF000000),
                    ),
                    shape = RoundedCornerShape(15.dp)
                )


                TextButton(onClick = { datePickerDialog.show() }) {
                    Text(
                        "Tarih Seç: ${
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date(selectedDate)
                            )
                        }"
                    )
                }


                TextButton(onClick = { timePickerDialog.show() }) {
                    Text(
                        "Saat Seç: ${
                            String.format(
                                "%02d:%02d",
                                selectedTime.first,
                                selectedTime.second
                            )
                        }"
                    )
                }

                Button(
                    onClick = {

                        scope.launch {
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = selectedDate
                            calendar.set(Calendar.HOUR_OF_DAY, selectedTime.first)
                            calendar.set(Calendar.MINUTE, selectedTime.second)
                            val finalDate = calendar.timeInMillis

                            if (description.isNotBlank() && mevki.isNotBlank() && participantNum.isNotBlank()) {
                                try {
                                    val participantNumInt = participantNum.toInt()

                                    val postTable = PostTable(
                                        studentId = studentId,
                                        groupName = groupName,
                                        participantNum = participantNumInt,
                                        description = description,
                                        date = finalDate,
                                        mevki = mevki
                                    )
                                    val groupChatsTable = GroupChatsTable(
                                        postId = 0,
                                        studentId = studentId,
                                        groupName = groupName,
                                        lastMessage = null,
                                        lastMessageTime = null
                                    )
                                    postViewModel.createPostAndGroupChat(postTable, groupChatsTable)

                                    toastMessage = "İlan başarıyla oluşturuldu."
                                    navController.navigate("Home")
                                } catch (e: NumberFormatException) {
                                    toastMessage = "Katılım sayısı geçerli bir tam sayı olmalıdır."
                                } catch (e: Exception) {
                                    toastMessage = "Bir hata oluştu: ${e.message}"
                                }
                            } else {
                                toastMessage = "Tüm alanları doldurun."
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7B00FF),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text("İlan Oluştur")
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