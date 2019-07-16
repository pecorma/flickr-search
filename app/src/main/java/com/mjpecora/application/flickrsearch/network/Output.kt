package com.mjpecora.application.flickrsearch.network

sealed class Output<out T: Any> {
    data class Success<out T : Any>(val data: T) : Output<T>()
    data class Error(val exception: Exception) : Output<Nothing>()
}