package com.example.afinal.Common

import com.example.afinal.Remote.IGoogleAPIService
import com.example.afinal.Remote.RetrofitClient

object Common {

    private val GOOGLE_API_URI="https://maps.googleapis.com/"

    val googleApiService:IGoogleAPIService
    get() = RetrofitClient.getClient(GOOGLE_API_URI).create(IGoogleAPIService::class.java)
}