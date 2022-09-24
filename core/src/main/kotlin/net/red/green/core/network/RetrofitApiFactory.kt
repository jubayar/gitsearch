package net.red.green.core.network

import retrofit2.Retrofit

class RetrofitApiFactory(private val retrofitClient: Retrofit) : ApiFactory {

    override fun <T> create(service: Class<T>): T = retrofitClient.create(service)
}
