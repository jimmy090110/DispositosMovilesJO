package com.biometric.jo.data.network.endpoints

import com.biometric.jo.data.network.entities.dogs.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
        @GET
        suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>
    }
