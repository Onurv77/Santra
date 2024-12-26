package com.example.santra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.santra.R

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.santra_background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur(16.dp),
        contentScale = ContentScale.FillBounds
    )
}