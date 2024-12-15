package com.example.santra.ui.screens

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.santra.R
import com.example.santra.data.AppDatabase
import com.example.santra.data.dao.SantraDao
import com.example.santra.data.entities.LoginTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(navController: NavController, db:AppDatabase) {

    //val context = LocalContext.current

    //val db = AppDatabase.getDatabase(context)

    //val db = Room.databaseBuilder(
    //    context,
    //    AppDatabase::class.java, "Santra_Database"
    //).build()

    val coroutineScope = rememberCoroutineScope()

    val santraDao = db.santraDao()


    var StudentNumber by remember { mutableStateOf("") }
    var StudentMail by remember { mutableStateOf("") }
    var StudentPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painter = painterResource(R.drawable.santrabackground),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.santralogo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))
            //ID
            Text(
                text = "Öğrenci Numarasını Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = StudentNumber,
                onValueChange = {StudentNumber = it},
                //label = { Text("Öğrenci Numarası")},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )




            Spacer(modifier = Modifier.height(8.dp))
            //Mail
            Text(
                text = "Öğrenci Mail Adresinizi Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = StudentMail,
                onValueChange = {StudentMail = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            //Password
            Text(
                text = "Şifre Giriniz:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = StudentPassword,
                onValueChange = {StudentPassword = it},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val newUser = LoginTable(StudentNumber = StudentNumber, StudentMail = StudentMail, StudentPassword = StudentPassword )
                            santraDao.insertAll(newUser)
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

//@Preview
//@Composable
//fun PreviewScreen(){
//    val navController = rememberNavController()
//    RegisterScreen(navController)
//}
