package ru.sportstoremobile.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sportstoremobile.data.LocalStorage
import ru.sportstoremobile.di.LocalDi
import ru.sportstoremobile.interfaces.ITokenService

class TokenService( private val store: LocalStorage) : ITokenService {


    override suspend fun fetchToken() {
        return withContext(Dispatchers.IO){
            val accessToken = LocalDi.userService.token()
            saveToken(accessToken)
            accessToken
        }
    }

    override fun refreshToken() {
       return Unit
    }

    override fun saveToken(token: String) {
       return store.saveToken(token)
    }

    override fun token(): String {
        return store.getToken()
    }

    override fun lock(): Any {
        return this
    }
}