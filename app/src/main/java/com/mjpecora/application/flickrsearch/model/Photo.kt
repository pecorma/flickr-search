package com.mjpecora.application.flickrsearch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val id: String?,
    val owner: String?,
    val secret: String?,
    val server: String?,
    val farm: Int?,
    val title: String?,
    val isPublic: Int?,
    val isFriend: Int?,
    val isFamily: Int?,
    @Json(name = "url_s") val url: String?,
    @Json(name = "height_s") val height: Int?,
    @Json(name = "width_s") val width: Int?
)