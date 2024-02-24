package com.biometric.jo.data.network.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {


    private const val NOBEL_PRIZES = "http://api.nobelprize.org/2.1/"

    fun getNobelPrizesConnection(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NOBEL_PRIZES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

