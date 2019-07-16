package com.mjpecora.application.flickrsearch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mjpecora.application.flickrsearch.model.FlickrDataSource
import com.mjpecora.application.flickrsearch.model.FlickrDataSourceFactory
import com.mjpecora.application.flickrsearch.model.Photo
import com.mjpecora.application.flickrsearch.model.State
import com.mjpecora.application.flickrsearch.network.ApiFactory
import com.mjpecora.application.flickrsearch.util.PAGE_SIZE
import io.reactivex.disposables.CompositeDisposable

class PhotosViewModel(subject: String) : ViewModel() {

    private val api = ApiFactory.api
    var photosList: LiveData<PagedList<Photo>>? = null
    private val compositeDisposable = CompositeDisposable()
    private val dataSourceFactory = FlickrDataSourceFactory(compositeDisposable, api, subject)

    val isListEmpty: Boolean
        get() = photosList?.value?.isEmpty() ?: true

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        photosList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> =
        Transformations.switchMap<FlickrDataSource, State>(dataSourceFactory.flickrLiveData, FlickrDataSource::state)

    fun retry() {
        dataSourceFactory.flickrLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}