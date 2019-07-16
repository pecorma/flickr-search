package com.mjpecora.application.flickrsearch.model

data class PhotoResponse(var photo: MutableList<Photo>?)

data class Photos(
    var photos: PhotoResponse
)