package com.example.santra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.align
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R

@Composable
fun LazyRowContent(navController: NavController) {

    // LazyRow'daki kart genişliğini ekranın tamamına değil, belirli bir boyuta ayarlayalım
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp), // Kartları merkezle// Kartların sağ ve sol kenarlarından boşluk
        horizontalArrangement = Arrangement.Center
    ) {
        items(5) { index ->

            Card(
                modifier = Modifier
                    .padding(end = 10.dp) // Kartlar arasındaki boşluk
                    .height(475.dp)
                    .width(350.dp) // Kart genişliği tek bir ilan için uygun sınırlandı
                    .clickable { navController.navigate("announcement_detail/$index") },
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        contentDescription = null,
                        painter = painterResource(R.drawable.account_circle),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .height(125.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(text = "Username", style = MaterialTheme.typography.headlineSmall, modifier = Modifier
                        .align(Alignment.CenterHorizontally))
                    Text(text = "${index+1}. ilan", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Bu, ilan açıklaması. Detaylı bilgi için tıklayın.", modifier = Modifier.padding(20.dp))

                    Spacer(modifier = Modifier.weight(1f)) // Bu Spacer kartın içeriğini yukarı iter

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Buton içindeki yatay boşluğu azaltıyoruz
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF5091B1), // Butonun arka plan rengi
                            contentColor = Color(0xFFFFFFFF) // Butonun metin rengi
                        )
                    ) {
                        Text(
                            text = "Katıl",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    val navController = rememberNavController()
    LazyRowContent(navController)
}