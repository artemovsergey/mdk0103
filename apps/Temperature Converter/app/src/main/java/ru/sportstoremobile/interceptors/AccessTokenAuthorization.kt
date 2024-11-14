package ru.sportstoremobile.interceptors

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.sportstoremobile.services.TokenService

class AccessTokenAuthorization(private val tokenService : TokenService) : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {
       synchronized(tokenService.lock()){
           return when{
               response.retryCount > 2 -> null
               else -> runBlocking { response.createSignedRequest() }
           }
       }
    }

    private suspend fun Response.createSignedRequest() : Request? = try{
        tokenService.fetchToken()
        request
    } catch (e: Exception){
        e.printStackTrace()
        null
    }

    private val Response.retryCount: Int
        get(){
            var currentResponse = priorResponse
            var result = 0
            while (currentResponse != null){
                result ++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }


}