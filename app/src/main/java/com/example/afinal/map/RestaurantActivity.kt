package com.example.afinal.map

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.ReviewInsideActivity
import com.example.afinal.ReviewListAdater
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityEvaluateBoardBinding
import com.example.afinal.databinding.ActivityRestaurantBinding
import com.example.afinal.evaluate.EvaluateBoardInsideActivity
import com.example.afinal.evaluate.EvaluateListAdater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRestaurantBinding
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView

    private val reviewList = mutableListOf<ReviewVO>()
    private val reviewKeyList = mutableListOf<String>()


    private lateinit var reviewRVAdapter: ReviewListAdater



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant)


        getFBReviewData()


        binding.ReviewListView.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, ReviewInsideActivity::class.java)
            intent.putExtra("key", reviewKeyList[position])
            startActivity(intent)}


    }



    private fun getFBReviewData() {

        reviewRVAdapter = ReviewListAdater(reviewList)
        binding.ReviewListView.adapter = reviewRVAdapter

        val db = Firebase.firestore

        reviewList.clear()


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                reviewList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(ContentValues.TAG, dataModel.toString())
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
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.ReviewRef.addValueEventListener(postListener)
    }

}
