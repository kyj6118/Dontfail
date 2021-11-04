package com.example.afinal.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.afinal.R
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityEvaluateBoardBinding
import com.example.afinal.databinding.ActivityRestaurantBinding
import com.example.afinal.evaluate.EvaluateListAdater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRestaurantBinding
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView
    private val ResList = mutableListOf<Restaurant>()



    private lateinit var Resdapter: ResListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)





    }
}