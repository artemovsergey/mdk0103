package ru.sportstoremobile.interfaces

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.sportstoremobile.models.User

interface IUserService {

    @GET("http://localhost:5295/api/Users")
    suspend fun getUsers( @Query("page")  page:Int ): User


    @POST("http://localhost:5295/api/Users")
    @FormUrlEncoded
    suspend fun  postUser(@Field("nmae") name:String = "name"  )
}