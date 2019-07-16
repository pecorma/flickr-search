package com.mjpecora.application.flickrsearch.network

import com.mjpecora.application.flickrsearch.model.Photos
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("?method=flickr.photos.search&extras=url_s&format=json&nojsoncallback=1")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perpage: Int,
        @Query("text") subject: String
    ): Single<Photos>

}