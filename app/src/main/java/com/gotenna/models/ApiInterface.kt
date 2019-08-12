package com.gotenna.models

import com.gotenna.data.MarkerObject
import retrofit2.http.GET
import retrofit2.Call


interface ApiInterface {
    @GET("development/scripts/get_map_pins.php")
    fun getValues(): Call<List<MarkerObject>>
}