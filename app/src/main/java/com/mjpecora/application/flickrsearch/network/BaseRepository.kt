package com.mjpecora.application.flickrsearch.network

import android.util.Log
import retrofit2.Response
import java.io.IOException

interface BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        return when(val result : Output<T> = safeApiResult(call,errorMessage)) {
            is Output.Success ->
                result.data
            is Output.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
                null
            }
        }
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : Output<T>{
        val response = call.invoke()
        if (response.isSuccessful)
            return Output.Success(response.body()!!)
        return Output.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

}