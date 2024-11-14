package ru.sportstoremobile.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.sportstoremobile.data.LocalStorage

class tokenInterceptor(private  val tokenProvider:LocalStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        // если у нас есть токен мы должны добавить его в запрос
        // и отправить снова

        val newRequest = chain.request().signedRequest()
        return  chain.proceed(newRequest)


    }


    private fun Request.signedRequest():Request{
        return newBuilder()
            .addHeader("Authorization","Bearer ${tokenProvider.getToken()}")
            .build()
    }

}