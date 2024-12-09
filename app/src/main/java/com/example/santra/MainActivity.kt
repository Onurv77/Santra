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