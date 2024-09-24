package com.example.kotlinexample.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinexample.R
import com.example.kotlinexample.ui.theme.KotlinExampleTheme

@Composable
fun LoginForm() {

    var input by remember { mutableStateOf("Авторизация") }

    Column(modifier = Modifier
            .padding(all = 15.dp)
            .background(color = Color.Blue)
            .fillMaxSize(),

           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center

    )
    {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",

            modifier = Modifier
                // Set image size to 40 dp
                .size(100.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { input = "Готово" }

        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(text = "Login",
             color = MaterialTheme.colorScheme.surface,
             style = MaterialTheme.typography.bodyMedium)

        TextField(value = "", onValueChange = { }  )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Password",
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.bodyMedium)

        TextField(value = "", onValueChange = { }  )

        Spacer(modifier = Modifier.height(4.dp))

        Button(onClick = {input = "Готово"}) {
            Text(text = input, color= Color.White)
        }
    }
}

@Preview(name="Login", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginFormPreview(){

    KotlinExampleTheme {
        Surface(
            shape = MaterialTheme.shapes.small,
            shadowElevation = 1.dp,
            color = Color.Yellow,
            modifier = Modifier.animateContentSize().padding(1.dp)
        ) {

            LoginForm()
        }
    }
}




