package com.coding.acronymism.networrk

import com.coding.acronymism.interfaces.APIInterface
import com.coding.acronymism.networrk.Endpoints.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {
    private val client = OkHttpClient
        .Builder()
        .build()

    val requestInterface: APIInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(APIInterface::class.java)
}