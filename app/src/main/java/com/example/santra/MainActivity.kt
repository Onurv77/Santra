package com.example.santra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.santra.ui.theme.SantraTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                AppNavigation()
            }
        }
    }

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreenWithDrawer(navController) }
        composable("register") { RegisterScreen(navController) }
    }
}
/*
@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("home") }) {
            Text("Giriş Yap")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("register") }) {
            Text("Kaydol")
        }
    }
}*/


@Composable
fun LoginScreen(navController: NavController) {
    // State'ler için remember
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFFF5F5F5)) // Açık gri arka plan
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Kenarlardan boşluk bırakmak için padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp)) // Oval
                    .background(color = Color(0xFFB7B400)) // Header'ın arka plan rengi
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
                    modifier = Modifier.weight(1f) // Eşit genişlikte buton
                ) {
                    Text("Giriş Yap")
                }
                Spacer(modifier = Modifier.width(16.dp)) // Butonlar arasında yatay boşluk
                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.weight(1f) // Eşit genişlikte buton
                ) {
                    Text("Kaydol")
                }
            }
        }
    }

}

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

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Kayıt Ol Sayfası")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Geri Dön")
        }
    }
}

@Composable
fun HomeScreenWithDrawer(navController: NavController) {

    val drawerState = remember { androidx.compose.material3.DrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed) }
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {

                LazyColumnContent(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )


                Button(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .width(56.dp)
                        .height(56.dp)
                ) {
                    Text("☰") // Çekmece simgesi
                }
            }
        }
    )
}

@Composable
fun LazyColumnContent(modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(20) { index ->
            Text(
                text = "Eleman $index",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color(0xFFF5F5F5))
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DrawerContent(navController: NavController) {

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
                .clickable { /* Profil'e gitmek için işlemler */ }
        )
        Text(
            text = "Ayarlar",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Ayarlar sayfasına yönlendir */ }
        )
    }
}