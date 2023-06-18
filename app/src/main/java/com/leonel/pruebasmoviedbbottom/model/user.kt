package com.leonel.pruebasmoviedbbottom.model

import com.google.gson.annotations.SerializedName

data class user (
                 @SerializedName("name") val name: String,
                 @SerializedName("phone") val phone: String,
                 @SerializedName("email") val email: String,
                 @SerializedName("address") val address: String,
                 @SerializedName("image") val image: String)

fun user.add()=user(name,phone,email,address,image)
