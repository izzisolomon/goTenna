package com.gotenna.models

import com.gotenna.data.MarkerObject
import com.squareup.moshi.Moshi
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit



class Api {
    companion object {
        fun newInstance(): Api = Api()
    }

    private fun createInterface(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://annetog.gotenna.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    private var apiService = createInterface()

    fun getData (receiver: Receiver<List<MarkerObject>>) {
        val call = apiService.getValues()
        call.enqueue(SingleTypeCallback(receiver))
    }

}