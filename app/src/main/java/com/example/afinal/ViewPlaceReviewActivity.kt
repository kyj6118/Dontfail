package com.example.afinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.afinal.VO.ReviewVO
import com.example.afinal.databinding.ActivityViewPlaceReviewBinding
import com.example.afinal.evaluate.EvaluateBoardInsideActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewPlaceReviewActivity : AppCompatActivity() {

    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView

    private val reviewList = mutableListOf<ReviewVO>()
    private val reviewKeyList = mutableListOf<String>()


    private lateinit var reviewRVAdapter: ReviewListAdater
    private lateinit var binding : ActivityViewPlaceReviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place_review)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_place_review)


        val place =   intent.getStringExtra("name").toString()

        getReviewData(place)


        binding.ReviewListView.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, ReviewInsideActivity::class.java)
            intent.putExtra("key", reviewKeyList[position])
            startActivity(intent)}





}



    private fun getReviewData(place: String) {


        reviewRVAdapter = ReviewListAdater(reviewList)
        binding.ReviewListView.adapter = reviewRVAdapter


        val db = Firebase.firestore

        reviewList.clear()


      db.collection("review")
          .whereEqualTo("place_name",place)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {



                    reviewList.add(
                        ReviewVO(
                            document["place_name"].toString(),
                            document["place_address"].toString(),
                            document["place_rating"].toString().toFloat()
                        )
                    )

                    reviewKeyList.add(document.id.toString())

                }
                reviewKeyList.reverse()
                reviewList.reverse()
               reviewRVAdapter.notifyDataSetChanged()

            }


    }
}