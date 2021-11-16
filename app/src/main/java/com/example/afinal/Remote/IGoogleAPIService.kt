package com.example.afinal.Remote

import com.example.afinal.VO.MyPlaces
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {

    @GET
    fun getNearbyPlaces(@Url url:String): Call<MyPlaces>
}