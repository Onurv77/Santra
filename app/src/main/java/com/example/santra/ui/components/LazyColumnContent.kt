package com.example.santra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R

@Composable
fun LazyRowContent(navController: NavController) {

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { index ->

            Card(
                modifier = Modifier.padding(end = 10.dp)
                    .fillMaxWidth()
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
                            .padding(bottom = 5.dp)
                    )
                    Text(text = "Username", style = MaterialTheme.typography.headlineSmall, modifier = Modifier
                        .align(Alignment.CenterHorizontally))
                    Text(text = "${index+1}. ilan", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Bu, ilan açıklaması. Detaylı bilgi için tıklayın.", modifier = Modifier.padding(20.dp))

                }
            }

            /*Text(
                text = "Eleman $index",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color(0xFFF5F5F5))
                    .padding(16.dp)
                    .clickable { navController.navigate("announcement_detail/$index") }
            )
            Text("selma")*/
        }
    }
}

@Preview
@Composable
fun preview(){
    val navController = rememberNavController()
    LazyRowContent(navController)
}