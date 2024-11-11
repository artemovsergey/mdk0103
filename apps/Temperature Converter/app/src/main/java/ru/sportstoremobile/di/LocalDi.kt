package ru.sportstoremobile.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.sportstoremobile.data.LocalStorage
import ru.sportstoremobile.services.TokenService

object LocalDi {

    lateinit var localStorage: LocalStorage
    lateinit var userService: TokenService

    fun provide(context: Context){
        localStorage = LocalStorage(context)
        userService = createUserService()
    }


    private fun createUserService(): TokenService{

        val contentType = "application/json".toMediaType()
        return  Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory( Json {  ignoreUnknownKeys = true}.asConverterFactory(contentType))
            .build()
            .create(TokenService::class.java)
    }

}