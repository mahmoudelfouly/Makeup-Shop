package com.melfouly.makeupshop.data.network

import com.melfouly.makeupshop.model.MakeupItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://makeup-api.herokuapp.com/"

interface MakeupService {
    @GET("api/v1/products.json")
    fun getAllProducts(): Call<List<MakeupItem>>
}

// Configure retrofit
object Network {
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val makeupCall = retrofit.create<MakeupService>()
}