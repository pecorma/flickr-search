package com.mjpecora.application.flickrsearch.model

import com.google.gson.annotations.SerializedName

data class Photo(
    var id: String?,
    var owner: String?,
    var secret: String?,
    var server: String?,
    var farm: Int?,
    var title: String?,
    var isPublic: Int?,
    var isFriend: Int?,
    var isFamily: Int?,
    @SerializedName("url_s") var url: String?,
    @SerializedName("height_s") var height: Int?,
    @SerializedName("width_s") var width: Int?
)