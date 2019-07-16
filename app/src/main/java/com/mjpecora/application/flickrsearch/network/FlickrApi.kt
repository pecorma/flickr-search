package com.mjpecora.application.flickrsearch.network

import com.mjpecora.application.flickrsearch.model.PhotoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface FlickrApi {

    suspend fun getPhotosAsync(): Deferred<Response<PhotoResponse>>

}