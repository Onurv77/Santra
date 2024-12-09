package com.example.santra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.santra.ui.components.AppNavigation



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                AppNavigation()
            }
        }
    }

/*
@Preview
@Composable
fun previewScreen() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}
*/

/*
@Composable
fun LoginScreen(navController: NavController) {
    // State'ler için remember
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFFFFFFFF)) // Açık gri arka plan
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), // Kenarlardan boşluk bırakmak için padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(R.drawable.santramain),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()

                )

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp)) // Oval
                    .background(color = Color(0xFF31B700)) // Header'ın arka plan rengi
                    .padding(vertical = 1.dp)
            ) {
                Text(
                    text = "Hoşgeldiniz",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp, top = 12.dp)
                        .align(Alignment.Center)
                )
            }


            // Kullanıcı adı TextField
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Kullanıcı Adı") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Şifre TextField
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Şifre") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }

            // Aynı satırda bulunan iki buton
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Butonlar arasında boşluk
            ) {
                Button(
                    onClick = { navController.navigate("home") },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF31B700)),
                    modifier = Modifier.weight(1f) // Eşit genişlikte buton
                ) {
                    Text("Giriş Yap")
                }
                Spacer(modifier = Modifier.width(16.dp)) // Butonlar arasında yatay boşluk
                Button(
                    onClick = { navController.navigate("register") },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF31B700)),
                    modifier = Modifier.weight(1f) // Eşit genişlikte buton
                ) {
                    Text("Kaydol")
                }
            }
        }
    }

}
 */

/*
@Composable
fun HomeScreen() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items) { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.LightGray)
                    .padding(16.dp)
            )
        }
    }
}*/