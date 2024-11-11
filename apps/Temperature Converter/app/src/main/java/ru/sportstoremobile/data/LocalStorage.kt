package ru.sportstoremobile.data

import android.content.Context
import ru.sportstoremobile.R

class LocalStorage(context: Context) {


    private val options = context.getSharedPreferences(context.getString(R.string.app_name),
                                                        Context.MODE_PRIVATE)

    fun getToken(): String =
        options.getString("user_id", null) ?: ""

    fun saveToken(token: String){
        options.edit().apply{
            putString("user_id", token)
            apply()
        }
    }

}