package com.usefulness.slidr.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AndroidOs(
    val name: String?,
    val version: String?,
    @SerialName("sdk_int") val sdkInt: Int,
    val description: String?,
    val year: Int,
    @SerialName("image_url") val imageUrl: String?,
    @SerialName("icon_url") val iconUrl: String?,
)
