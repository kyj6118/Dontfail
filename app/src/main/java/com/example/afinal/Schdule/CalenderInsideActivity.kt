package com.example.afinal.Schdule

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.calenderVO
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityCalenderInsideBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class CalenderInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCalenderInsideBinding

    private lateinit var key: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender_inside)



        key = intent.getStringExtra("key").toString()

        getCalendarData(key)

        binding.deletBtn.setOnClickListener {

            FBRef.calRef.child(key).removeValue()
            Toast.makeText(this, "sucess delet", Toast.LENGTH_LONG).show()
            finish()
        }




    }

    private fun getCalendarData(key: String) {


        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val dataModel = dataSnapshot.getValue(calenderVO::class.java)

                binding.date.text = dataModel!!.date
                binding.content.text = dataModel!!.content


            }





            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.calRef.child(key).addValueEventListener(postListener)
    }

}