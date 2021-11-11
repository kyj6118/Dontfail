package com.example.afinal.Schdule

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityCourseModifyBinding
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

class CourseModifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseModifyBinding
    var firestore: FirebaseFirestore? = null
    private val mycourseList = mutableListOf<mycourse>()
    private val mycourseKeyList = mutableListOf<String>()

    private lateinit var mycourseRVAdapter: CourseDeletListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_modify)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_modify)






        getCourseData()

        binding.deletList.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, DeletCourseInsideActivity::class.java)
            intent.putExtra("key", mycourseKeyList[position])
            startActivity(intent)
        }






    }

    private fun getCourseData() {
        mycourseRVAdapter = CourseDeletListAdapter(mycourseList)
        binding.deletList.adapter = mycourseRVAdapter

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mycourseList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(mycourse::class.java)
                    mycourseList.add(item!!)
                    mycourseKeyList.add(dataModel.key.toString())

                }


                mycourseKeyList.reverse()
                mycourseList.reverse()
                mycourseRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.CourseRef.addValueEventListener(postListener)




    }

}

