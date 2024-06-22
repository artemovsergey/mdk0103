package com.example.kotlinexample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlinexample.R.drawable.profile_picture
import com.example.kotlinexample.models.User
import com.example.kotlinexample.ui.theme.KotlinExampleTheme

import androidx.compose.foundation.clickable
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            KotlinExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val users = mutableListOf<User>()
                    users.add(User("asv", 3))
                    users.add(User("asv", 4))
                    users.add(User("asv", 5))
                    UserList(users = users)
                }
            }


        }
    }
}

@Composable
fun UserCard(user: User) {

        Row(modifier = Modifier.padding(all = 8.dp)){

            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))


            var isExpanded by remember { mutableStateOf(false) }

            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            )

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = "${user.name}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(shape = MaterialTheme.shapes.medium,
                        shadowElevation = 1.dp,
                        color = surfaceColor,
                        modifier = Modifier.animateContentSize().padding(1.dp)
                ){
                    Text(
                        text = "${user.age} лет",
                        modifier = Modifier.padding(all = 4.dp),
                        //color = MaterialTheme.colorScheme.secondary,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }

}



@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user -> UserCard(user)
        }
    }
}



//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO,
//    showBackground = true,
//    name = "Light Mode")
//@Composable
//fun UserCardPreview() {
//
//    KotlinExampleTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            UserCard(user = User("Sergey",35))
//        }
//    }
//
//}

@Preview(name = "Список пользователей")
@Composable
fun UserListPreview(){
    KotlinExampleTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val users = mutableListOf<User>()
            users.add(User("asv", 3))
            users.add(User("asv", 4))
            users.add(User("asv", 5))
            UserList(users = users)
        }
    }
}


