package com.example.afinal

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.Remote.IGoogleAPIService
import com.example.afinal.Schdule.course
import com.example.afinal.Schdule.mycourse
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.PlaceDetail
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.evaluate
import com.example.afinal.board.FreeBoardInsideActivity
import com.example.afinal.databinding.ActivityViewPlaceBinding
import com.example.afinal.evaluate.EvaluateListAdater
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlace : AppCompatActivity() {

    private lateinit var binding: ActivityViewPlaceBinding

    private  var Xtitle: String? = null

    internal lateinit var mService: IGoogleAPIService
    var mPlace: PlaceDetail? = null

    private val reviewList = mutableListOf<ReviewVO>()
    private val reviewKeyList = mutableListOf<String>()


    private lateinit var reviewRVAdapter: ReviewListAdater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_place)

        mService = com.example.afinal.Common.Common.googleApiService






        binding.placeName.text = ""
        binding.placeAddress.text = ""
        binding.placeOpenHour.text = ""








        binding.btnShowMap.setOnClickListener {

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mPlace!!.result!!.url))
            startActivity(mapIntent)


        }


        if (com.example.afinal.Common.Common.currentResult!!.photos != null && com.example.afinal.Common.Common.currentResult!!.photos!!.size > 0)
            Picasso.with(this)
                .load(
                    getPhothOfPlace(
                        com.example.afinal.Common.Common.currentResult!!.photos!![0].photo_reference!!,
                        10000
                    )
                )
                .into(binding.photo)



        if (com.example.afinal.Common.Common.currentResult!!.rating != null)
            binding.ratingBar.rating =
                com.example.afinal.Common.Common.currentResult!!.rating.toFloat()
        else
            binding.ratingBar.visibility = View.GONE



        if (com.example.afinal.Common.Common.currentResult!!.rating != null)
            binding.ratingBar.rating =
                com.example.afinal.Common.Common.currentResult!!.rating.toFloat()
        else
            binding.ratingBar.visibility = View.GONE






        if (com.example.afinal.Common.Common.currentResult!!.opening_hours != null)
            binding.placeOpenHour.text =
                "Open now : " + com.example.afinal.Common.Common.currentResult!!.opening_hours!!.open_now
        else
            binding.placeOpenHour.visibility = View.GONE


        mService.getDetailPlace(getPlaceDetailUrl(com.example.afinal.Common.Common.currentResult!!.place_id!!))
            .enqueue(object : retrofit2.Callback<PlaceDetail> {
                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                    Toast.makeText(baseContext, "" + t.message, Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                    mPlace = response!!.body()

                    binding.placeAddress.text = mPlace!!.result!!.formatted_address
                    binding.placeName.text = mPlace!!.result!!.name

                    Xtitle = mPlace!!.result!!.name.toString()

                    getReviewData(Xtitle!!)
                    Log.d("XTitle", Xtitle.toString())
                }


            })

        binding.Review.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, ReviewInsideActivity::class.java)
            intent.putExtra("key", reviewKeyList[position])
            startActivity(intent)}









        binding.btnWriteReview.setOnClickListener {


            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("name", binding.placeName.text)
            intent.putExtra("Address", binding.placeAddress.text)
            startActivity(intent)


        }


    }

    private fun getReviewData(xtitle: String) {

        reviewRVAdapter = ReviewListAdater(reviewList)
        binding.Review.adapter = reviewRVAdapter


        val db = Firebase.firestore

        reviewList.clear()


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                reviewList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(ReviewVO::class.java)
                    reviewList.add(item!!)
                    reviewKeyList.add(dataModel.key.toString())

                }


                reviewKeyList.reverse()
                reviewList.reverse()
                reviewRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.ReviewRef.addValueEventListener(postListener)

    }


    private fun getPhothOfPlace(photo_reference: String, i: Int): String {

        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        url.append("?maxWidth=$i")
        url.append("&photo_reference=$photo_reference")
        url.append("&key=AIzaSyAEHJx5j_CUnp6jRgzLe3hoPiPVgLtxWrA")

        Log.d("URL_DEBUG", url.toString())
        return url.toString()
    }


    private fun getPlaceDetailUrl(placeId: String): String {
        val url = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
        url.append("?place_id=$placeId")
        url.append("&key=AIzaSyAEHJx5j_CUnp6jRgzLe3hoPiPVgLtxWrA")



        Log.d("URL_DEBUG", url.toString())
        return url.toString()
    }
}


