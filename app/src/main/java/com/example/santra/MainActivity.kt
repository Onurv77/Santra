package com.example.santra

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.santra.data.AppDatabase
import com.example.santra.ui.components.AppNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(applicationContext)

        val santraDao = db.santraDao()


        CoroutineScope(Dispatchers.IO).launch {
            val allData = santraDao.getAll()
            allData.forEach { data ->
                Log.d("MainActivity", "StudentID: ${data.id}, StudentNumber: ${data.studentId}, StudentPassword: ${data.studentPassword}")
            }
        }

        setContent {
                AppNavigation(db)
            }
        }


    }