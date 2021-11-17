package com.example.afinal.Remote

import com.example.afinal.VO.MyPlaces
import com.example.afinal.VO.PlaceDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {

    @GET
    fun getNearbyPlaces(@Url url:String): Call<MyPlaces>

    @GET
    fun getDetailPlace(@Url url:String):Call <PlaceDetail>
}