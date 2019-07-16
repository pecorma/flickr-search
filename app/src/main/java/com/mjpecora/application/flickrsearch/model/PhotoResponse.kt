package com.mjpecora.application.flickrsearch.model

data class PhotoResponse(
    var page: Int = 1,
    var pages: Int?,
    var result: MutableList<Photo>?
)