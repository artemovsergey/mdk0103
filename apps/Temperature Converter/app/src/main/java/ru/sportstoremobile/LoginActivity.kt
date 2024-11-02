package ru.sportstoremobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sportstoremobile.ui.theme.SportStoreMobileTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Greeting(name = "Sergey")
            }
        }
    }
}


@Composable
fun AppTheme(content: @Composable () -> Unit){

    MaterialTheme {
        Surface(color = Color.Blue, contentColor = Color.White) {
            content.invoke()
        }
    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    //var count:Int = 0;

    val count:MutableState<Int> = remember {mutableStateOf(0)}

    Row(modifier = Modifier.clickable { count.value += 1 }){
        Text(text = "Количество: ",modifier = modifier)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "${count.value}",modifier = modifier)
    }
    Log.i(",","Render composable function Greeting ")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting(name = "Sergey")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreviewWithoutUI() {
    AppTheme {
        Greeting(name = "Sergey")
    }
}