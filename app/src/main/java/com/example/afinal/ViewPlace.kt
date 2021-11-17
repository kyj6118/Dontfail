package com.example.afinal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.afinal.Remote.IGoogleAPIService
import com.example.afinal.VO.PlaceDetail
import com.example.afinal.databinding.ActivityViewPlaceBinding
import com.google.android.gms.common.internal.service.Common
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlace : AppCompatActivity() {

    private lateinit var binding:ActivityViewPlaceBinding


    internal lateinit var mService :IGoogleAPIService
    var mPlace:PlaceDetail?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_view_place)

            mService = com.example.afinal.Common.Common.googleApiService

        binding.placeName.text=""
        binding.placeAddress.text=""
        binding.placeOpenHour.text=""

        binding.btnShowMap.setOnClickListener{

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mPlace!!.result!!.url))
            startActivity(mapIntent)


        }


        if(com.example.afinal.Common.Common.currentResult!!.photos !=null && com.example.afinal.Common.Common.currentResult!!.photos!!.size > 0)
            Picasso.with(this)
                .load(getPhothOfPlace(com.example.afinal.Common.Common.currentResult!!.photos!![0].photo_reference!!,10000))
                .into(binding.photo)



/*

        if(com.example.afinal.Common.Common.currentResult!!.rating != null)
            binding.ratingBar.rating = com.example.afinal.Common.Common.currentResult!!.rating.toFloat()
        else
            binding.ratingBar.visibility= View.GONE



        if(com.example.afinal.Common.Common.currentResult!!.rating != null)
            binding.ratingBar.rating = com.example.afinal.Common.Common.currentResult!!.rating.toFloat()
        else
            binding.ratingBar.visibility= View.GONE
*/









        if (com.example.afinal.Common.Common.currentResult!!.opening_hours != null)
            binding.placeOpenHour.text="Open now : "+ com.example.afinal.Common.Common.currentResult!!.opening_hours!!.open_now
        else
            binding.placeOpenHour.visibility=View.GONE


        mService.getDetailPlace(getPlaceDetailUrl(com.example.afinal.Common.Common.currentResult!!.place_id!!))
            .enqueue(object :retrofit2.Callback<PlaceDetail> {
                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    Toast.makeText(baseContext,""+t.message,Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                    mPlace = response!!.body()

                    binding.placeAddress.text = mPlace!!.result!!.formatted_address
                    binding.placeName.text=mPlace!!.result!!.name
                }


            })

    }

    private fun getPhothOfPlace(photo_reference: String, i: Int): String {

        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        url.append("?maxWidth=$i")
        url.append("&photo_reference=$photo_reference")
        url.append("&key=AIzaSyAEHJx5j_CUnp6jRgzLe3hoPiPVgLtxWrA")

        Log.d("URL_DEBUG",url.toString())
        return url.toString()
    }

    }

    private fun getPlaceDetailUrl(placeId: String): String {
        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
            url.append("?place_id=$placeId")
            url.append("&key=AIzaSyAEHJx5j_CUnp6jRgzLe3hoPiPVgLtxWrA")



        Log.d("URL_DEBUG",url.toString())
        return url.toString()
    }
    

