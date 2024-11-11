package ru.sportstoremobile.interfaces

interface ITokenService {

    suspend fun fetchToken()

    fun refreshToken()

    fun saveToken(token: String)

    fun token(): String

    fun lock(): Any

}