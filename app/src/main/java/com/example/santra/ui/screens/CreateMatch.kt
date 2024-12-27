package com.example.santra.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.santra.R
import com.example.santra.ui.components.BackgroundImage
import com.example.santra.ui.components.BottomBarContent
import com.example.santra.ui.components.TopBarContent

@Composable
fun CreateMatch(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

//    val matchData = MatchData(
//        profileImage = R.drawable.account_circle,
//        username = "Player123",
//        matchTime = "20:00",
//        leagueImage = R.drawable.rank5_3,
//        participants = listOf("PlayerA", "PlayerB", "PlayerC", "PlayerD", "PlayerE")
//    )

    BackgroundImage()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { TopBarContent(drawerState, scope) },
        bottomBar = { BottomBarContent(navController) }
    ) {
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }

}

//data class MatchData(
//    val profileImage: Int,
//    val username: String,
//    val matchTime: String,
//    val leagueImage: Int,
//    val participants: List<String>
//)

@Preview
@Composable
fun preMatch(){
    val navController = rememberNavController()
    CreateMatch(navController)
}