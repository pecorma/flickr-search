package com.mjpecora.application.flickrsearch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjpecora.application.flickrsearch.model.Photo
import com.mjpecora.application.flickrsearch.model.PhotoRepository
import com.mjpecora.application.flickrsearch.network.ApiFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PhotosViewModel : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default
    private val scope: CoroutineScope
        get() = CoroutineScope(coroutineContext)
    private val repo = PhotoRepository(ApiFactory.api)

    val photosLiveData = MutableLiveData<MutableList<Photo>>()

    fun fetchPhotos(subject: String) {
        scope.launch {
            val photos = repo.getPhotos(subject)
            photosLiveData.postValue(photos)
        }
    }

    fun cancelRequests() = coroutineContext.cancel()

}