package com.example.kotlinexample.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinexample.R

@Composable
fun TitleImage(drawing: Int) {
    Image(
        painter = painterResource(drawing),
        contentDescription = "title image",
        modifier = Modifier.size(150.dp)
    )
}

@Preview
@Composable
fun ImagePreview(){
    TitleImage(drawing = R.drawable.baseline_cloud_24)
}