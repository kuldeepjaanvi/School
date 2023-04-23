package com.enabling.neeladri.network

import com.enabling.neeladri.network.model.Dashboard
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiService {

  //  @GET("school.json")
    @GET("devideen.json")
    suspend fun getDashboard(): Dashboard

    @GET("data-random.json")
    suspend fun getRandomDashboard(): Dashboard

}

object NetworkClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)

    val service: ApiService by lazy {
        retrofit.baseUrl("https://kuldeepjaanvi.github.io/data/")
            .build().create(ApiService::class.java)
    }

}