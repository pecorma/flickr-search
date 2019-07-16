package com.mjpecora.application.flickrsearch.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mjpecora.application.flickrsearch.network.FlickrApi
import io.reactivex.disposables.CompositeDisposable

class FlickrDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val api: FlickrApi,
    private val subject: String
) : DataSource.Factory<Int, Photo>() {

    var flickrLiveData = MutableLiveData<FlickrDataSource>()

    override fun create(): DataSource<Int, Photo> {
        val flickrDataSource = FlickrDataSource(compositeDisposable, api, subject)
        flickrLiveData.postValue(flickrDataSource)
        return flickrDataSource
    }

}
