package com.example.kotlinexample.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlinexample.models.User
import com.example.kotlinexample.ui.theme.KotlinExampleTheme

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user -> UserCard(user)
        }
    }
}

@Preview(name = "Список пользователей")
@Composable
fun UserListPreview(){
    KotlinExampleTheme {
        Surface() {
            val users = mutableListOf<User>()
            users.add(User("asv", 3))
            users.add(User("asv", 4))
            users.add(User("asv", 5))
            UserList(users = users)
        }
    }
}