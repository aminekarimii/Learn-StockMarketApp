package com.plcoding.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query("apikey") apiKey:String = API_KEY
    ) : ResponseBody

    companion object {
        const val API_KEY = "hello world"
        const val BASE_URL = "https://alphavantage.co"
    }
}