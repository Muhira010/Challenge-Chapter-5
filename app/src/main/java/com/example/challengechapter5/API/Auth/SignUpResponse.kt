package com.example.challengechapter5.API.Auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("job") var job: String? = null,
    @SerializedName("image") var image: String? = null
)