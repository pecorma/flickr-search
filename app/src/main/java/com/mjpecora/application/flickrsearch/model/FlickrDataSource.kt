package com.mjpecora.application.flickrsearch.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mjpecora.application.flickrsearch.network.FlickrApi
import com.mjpecora.application.flickrsearch.util.PAGE_SIZE
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class FlickrDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val api: FlickrApi,
    private val subject: String
) : PageKeyedDataSource<Int, Photo>() {

    companion object {
        private const val INITIAL_PAGE = 1
    }

    var state: MutableLiveData<State> = MutableLiveData()
    private var completable: Completable? = null

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            api.getPhotos(params.key, params.requestedLoadSize, subject)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        response?.photos?.photo?.let {
                            callback.onResult(it, params.key + 1)
                        } ?: updateState(State.ERROR)
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {}

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            api.getPhotos(INITIAL_PAGE, PAGE_SIZE, subject)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        response?.photos?.photo?.let {
                            callback.onResult(it, null, 2)
                        } ?: updateState(State.ERROR)
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        completable?.let {
            compositeDisposable.add(it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        completable = if (action == null)
            null
        else Completable.fromAction(action)
    }

}