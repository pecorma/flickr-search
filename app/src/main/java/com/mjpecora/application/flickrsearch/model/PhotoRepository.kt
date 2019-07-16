package com.mjpecora.application.flickrsearch.model

import com.mjpecora.application.flickrsearch.network.BaseRepository
import com.mjpecora.application.flickrsearch.network.FlickrApi

class PhotoRepository(private val api : FlickrApi) : BaseRepository {

    suspend fun getPhotos(subject: String): MutableList<Photo>? {
        val photoResponse  = safeApiCall({ api.getPhotosAsync().await() }, "error fetching photos of $subject")
        return photoResponse?.result?.toMutableList()
    }

}