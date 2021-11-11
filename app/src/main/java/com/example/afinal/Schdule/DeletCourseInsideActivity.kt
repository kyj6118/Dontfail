package com.example.afinal.Schdule

import android.content.ContentValues.TAG
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
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityDeletCourseInsideBinding
import com.example.afinal.databinding.ActivityFreeBoardInsideBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class DeletCourseInsideActivity : AppCompatActivity() {


    private lateinit var binding : ActivityDeletCourseInsideBinding


    private lateinit var databaseRef : DatabaseReference


    private lateinit var key : String

    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delet_course_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_delet_course_inside)



        val db = FirebaseFirestore.getInstance()


        // 두번째 방법
        key = intent.getStringExtra("key").toString()
        courseData(key.toString())






        binding.deletBtn.setOnClickListener{



            FBRef.CourseRef.child(key).removeValue()
            Toast.makeText(this, "Delete Success!", Toast.LENGTH_LONG).show()
            finish()

        }

    }

    private fun courseData(toString: String) {
        val db = Firebase.firestore
        val docRef = db.collection("mycourse").document(key)





        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(mycourse::class.java)

                    binding.TitleArea.text = dataModel!!.courseTitle
                    binding.Time1Area.text = dataModel!!.courseTime1
                    binding.Time2Area.text = dataModel!!.courseTime2
                    binding.Time3Area.text = dataModel!!.courseTime3


                } catch (e: Exception) {
                    Log.d(TAG, "getemail" +  FBAuth.getemail() )

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.CourseRef.child(key).addValueEventListener(postListener)
    }

}