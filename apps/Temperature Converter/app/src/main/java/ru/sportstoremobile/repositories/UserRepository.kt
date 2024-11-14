package ru.sportstoremobile.repositories

class UserRepository(
    private val userService: Any
) {

    suspend fun getUsers(page:Int){
        val data = userService
    }
}