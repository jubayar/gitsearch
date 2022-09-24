package net.red.green.core.network

import net.red.green.core.network.model.BaseResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RestApiCall<S : Any, E : Any>(private val delegate: Call<S>, private val errorConverter: Converter<ResponseBody, E>) : Call<BaseResponse<S, E>> {

    override fun clone(): Call<BaseResponse<S, E>> = RestApiCall(delegate.clone(), errorConverter)

    override fun execute(): Response<BaseResponse<S, E>> {
        throw UnsupportedOperationException("BaseResponse call doesn't support execute")
    }

    override fun enqueue(callback: Callback<BaseResponse<S, E>>) = delegate.enqueue(object : Callback<S> {

        override fun onResponse(call: Call<S>, response: Response<S>) {
            val body = response.body()
            val code = response.code()
            val error = response.errorBody()

            if (response.isSuccessful) {
                if (body != null) {
                    callback.onResponse(this@RestApiCall, Response.success(BaseResponse.Success(body)))
                } else {
                    callback.onResponse(this@RestApiCall, Response.success(BaseResponse.UnknownError(Throwable("Response is successful but the body is null"))))
                }
            } else {
                val errorBody = when {
                    error == null -> null
                    error.contentLength() == 0L -> null
                    else -> try {
                        errorConverter.convert(error)
                    } catch (ex: Exception) {
                        null
                    }
                }

                if (errorBody != null) {
                    callback.onResponse(this@RestApiCall, Response.success(BaseResponse.ApiError(errorBody, code)))
                } else {
                    callback.onResponse(this@RestApiCall, Response.success(BaseResponse.UnknownError(Throwable("Fail to decode error"))))
                }
            }
        }

        override fun onFailure(call: Call<S>, throwable: Throwable) {
            val networkFailureResponse = when (throwable) {
                is IOException -> BaseResponse.NetworkError(throwable)
                else -> BaseResponse.UnknownError(throwable)
            }
            callback.onResponse(this@RestApiCall, Response.success(networkFailureResponse))
        }
    })

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
