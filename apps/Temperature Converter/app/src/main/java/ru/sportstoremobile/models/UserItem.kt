package ru.sportstoremobile.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserItem(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("login")
    val login: String,
    @SerialName("name")
    val name: String,
    @SerialName("passwordHash")
    val passwordHash: String,
    @SerialName("passwordSalt")
    val passwordSalt: String,
    @SerialName("photo")
    val photo: String,
    @SerialName("token")
    val token: String,
    @SerialName("updatedAt")
    val updatedAt: String
)