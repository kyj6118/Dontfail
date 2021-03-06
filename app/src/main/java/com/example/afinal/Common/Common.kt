package com.example.afinal.Common

import com.example.afinal.Remote.IGoogleAPIService
import com.example.afinal.Remote.RetrofitClient
import com.example.afinal.VO.Results
import com.google.firebase.firestore.local.LruGarbageCollector

object Common {

    private val GOOGLE_API_URI="https://maps.googleapis.com/"


    var currentResult:Results?=null

    val googleApiService:IGoogleAPIService
    get() = RetrofitClient.getClient(GOOGLE_API_URI).create(IGoogleAPIService::class.java)
}