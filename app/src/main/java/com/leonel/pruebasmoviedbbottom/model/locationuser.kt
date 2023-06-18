package com.leonel.pruebasmoviedbbottom.model

import com.google.gson.annotations.SerializedName

data class locationuser(
    @SerializedName("latitude") val latitude: String="",
    @SerializedName("longitude") val longitude: String="",
    @SerializedName("date") val date: String=""
)
fun locationuser.add()=locationuser(latitude, longitude,date)