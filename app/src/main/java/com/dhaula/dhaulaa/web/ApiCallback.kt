package com.dhaula.dhaulaa.web

import com.google.gson.Gson
import com.paytag.web.ApiException
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * [ApiCallback]
 */
abstract class ApiCallback<T : ApiResponse?> : Callback<T> {
    override fun onResponse(
            call: Call<T>,
            response: Response<T>
    ) {
        onComplete()
        val status = response.code()
        //check for error body
        if (!response.isSuccessful) {
            onFailure(getAPIException(response, null))
            return
        }
        val apiResponse = response.body()
        if (apiResponse == null) {
            onFailure(ApiException(ApiException.Kind.NULL, status, "IResponse can't be null"))
            return
        }
        //        if (TextUtils.isEmpty(apiStatus) /*&& apiResponse.getResponseCode() != 200 && apiResponse.getResponseCode() != 201 apiStatus/*&& apiStatus.equalsIgnoreCase("failure")*/) {
//            onFailure(new ApiException(ApiException.Kind.INVALID_RESPONSE, apiResponse.getMessage()));
//            return;
//        }
        if (!apiResponse.isValid) {
            val validationError = ApiException(
                    ApiException.Kind.INVALID_RESPONSE,
                    status,
                    "Validation error for the given scenario"
            )
            onFailure(validationError)
            //            return;
        }
        onSuccess(call, apiResponse)
    }

    override fun onFailure(call: Call<T>, cause: Throwable) {
        onComplete()
        val apiException: ApiException = getAPIException(null, cause)
        onFailure(apiException)
    }

    private fun getAPIException(
            response: Response<T>?,
            cause: Throwable?
    ): ApiException {
        if (cause != null) {
            return when (cause) {
                is SocketTimeoutException -> {
                    ApiException(
                        ApiException.Kind.SOCKET_TIMEOUT,
                        "Internet Connection is slow, Try Again.",
                        cause
                    )
                }
                is JSONException -> {
                    return ApiException(ApiException.Kind.CONVERSION, "Conversion Error", cause)
                }
                is IOException -> {
                    return ApiException(ApiException.Kind.NETWORK, "Network Error", cause)
                }
                is ApiException -> {
                    return cause
                }
                else -> {
                    return ApiException(ApiException.Kind.UNEXPECTED, "Unknown", cause)
                }
            }
        }
        return try {
            val error = Gson().fromJson(response?.errorBody()?.string(), CustomError::class.java)
            ApiException(ApiException.Kind.HTTP, error.message)
        } catch (e: Exception) {
            ApiException(ApiException.Kind.UNEXPECTED, "Unknown Error")
        }


        //  return ApiException(ApiException.Kind.UNEXPECTED, "Unknown Error")
    }

    abstract fun onSuccess(call: Call<T>?, response: T)
    abstract fun onComplete()
    abstract fun onFailure(e: ApiException?)
}