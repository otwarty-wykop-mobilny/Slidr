package io.github.usefulness.slidr.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class AndroidOs(
    val name: String?,
    val version: String?,
    @Json(name = "sdk_int") val sdkInt: Int,
    val description: String?,
    val year: Int,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "icon_url") val iconUrl: String?,
) : Serializable {

    companion object {
        private const val serialVersionUID = 10L
    }
}
