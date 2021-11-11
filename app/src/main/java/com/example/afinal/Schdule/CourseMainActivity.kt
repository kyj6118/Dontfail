package com.example.afinal.Schdule

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.commentVO
import com.example.afinal.VO.evaluate
import com.example.afinal.board.BoardWriteActivity
import com.example.afinal.board.FreeBoardInsideActivity
import com.example.afinal.databinding.ActivityCourseMainBinding
import com.example.afinal.evaluate.EvaluateBoardInsideActivity
import com.example.afinal.evaluate.EvaluateBoardWrite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.notificationManager
import java.lang.Exception

class CourseMainActivity : AppCompatActivity() {

    private val TAG = CourseMainActivity::class.java.simpleName
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    private val CourseKeyList = mutableListOf<String>()

    private lateinit var binding: ActivityCourseMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_main)


        val mon1 = findViewById<TextView>(R.id.mon1)
        val mon2 = findViewById<TextView>(R.id.mon2)
        val mon3 = findViewById<TextView>(R.id.mon3)
        val mon4 = findViewById<TextView>(R.id.mon4)
        val mon5 = findViewById<TextView>(R.id.mon5)
        val mon6 = findViewById<TextView>(R.id.mon6)
        val tue1 = findViewById<TextView>(R.id.tue1)
        val tue2 = findViewById<TextView>(R.id.tue2)
        val tue3 = findViewById<TextView>(R.id.tue3)
        val tue4 = findViewById<TextView>(R.id.tue4)
        val tue5 = findViewById<TextView>(R.id.tue5)
        val tue6 = findViewById<TextView>(R.id.tue6)
        val wed1 = findViewById<TextView>(R.id.wed1)
        val wed2 = findViewById<TextView>(R.id.wed2)
        val wed3 = findViewById<TextView>(R.id.wed3)
        val wed4 = findViewById<TextView>(R.id.wed4)
        val wed5 = findViewById<TextView>(R.id.wed5)
        val wed6 = findViewById<TextView>(R.id.wed6)
        val thur1 = findViewById<TextView>(R.id.thur1)
        val thur2 = findViewById<TextView>(R.id.thur2)
        val thur3 = findViewById<TextView>(R.id.thur3)
        val thur4 = findViewById<TextView>(R.id.thur4)
        val thur5 = findViewById<TextView>(R.id.thur5)
        val thur6 = findViewById<TextView>(R.id.thur6)
        val fri1 = findViewById<TextView>(R.id.fri1)
        val fri2 = findViewById<TextView>(R.id.fri2)
        val fri3 = findViewById<TextView>(R.id.fri3)
        val fri4 = findViewById<TextView>(R.id.fri4)
        val fri5 = findViewById<TextView>(R.id.fri5)
        val fri6 = findViewById<TextView>(R.id.fri6)


        val ImageView1: ImageView = findViewById(R.id.CoursePlusBtn)



       binding.storageBtn.setOnClickListener {

            val intent = Intent(this, com.example.afinal.Schdule.CourseModifyActivity::class.java)
            startActivity(intent)

        }

        ImageView1.setOnClickListener {

            val intent = Intent(this, com.example.afinal.Schdule.CourseActivity::class.java)
            startActivity(intent)
        }




        val db = Firebase.firestore
        val currentID = FBAuth.getemail()


       /*  db.collection("mycourse")
                .whereEqualTo("email", currentID)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")


                        val time1 = document.getString("courseTime1")
                        val time2 =document.getString("courseTime2")
                        val time3 =document.getString("courseTime3")
                        val title = document.getString("courseTitle")

                        when(time1){
                            "mon1"->{
                                mon1.text = title

                            }
                            "mon2"->{
                                mon2.text = title

                            }

                            "mon3"->{
                                mon3.text = title

                            }

                            "mon4"->{
                                mon4.text = title

                            }

                            "mon5"->{
                                mon5.text = title

                            }

                            "mon6"->{
                                mon6.text = title

                            }
                            "tue1" ->{
                                tue1.text = title
                            }

                            "tue2" ->{
                                tue2.text = title
                            }
                            "tue3" ->{
                                tue3.text = title
                            }
                            "tue4" ->{
                                tue4.text = title
                            }
                            "tue5" ->{
                                tue5.text = title
                            }
                            "tue6" ->{
                                tue6.text = title
                            }


                            "fri1" ->{
                                fri1.text = title
                            }

                            "fri2" ->{
                                fri2.text = title
                            }
                            "fri3" ->{
                                fri3.text = title
                            }
                            "fri4" ->{
                                fri4.text = title
                            }
                            "fri5" ->{
                                fri5.text = title
                            }
                            "fir6" ->{
                                fri6.text = title
                            }



                        }

                        when(time2){

                            "mon1"->{
                                mon1.text = title

                            }
                            "mon2"->{
                                mon2.text = title

                            }

                            "mon3"->{
                                mon3.text = title

                            }

                            "mon4"->{
                                mon4.text = title

                            }

                            "mon5"->{
                                mon5.text = title

                            }

                            "mon6"->{
                                mon6.text = title

                            }

                            "tue1" ->{
                                tue1.text = title
                            }

                            "tue2" ->{
                                tue2.text = title
                            }
                            "tue3" ->{
                                tue3.text = title
                            }
                            "tue4" ->{
                                tue4.text = title
                            }
                            "tue5" ->{
                                tue5.text = title
                            }
                            "tue6" ->{
                                tue6.text = title
                            }
                            "fri1" ->{
                                fri1.text = title
                            }

                            "fri2" ->{
                                fri2.text = title
                            }
                            "fri3" ->{
                                fri3.text = title
                            }
                            "fri4" ->{
                                fri4.text = title
                            }
                            "fri5" ->{
                                fri5.text = title
                            }
                            "fir6" ->{
                                fri6.text = title
                            }
                        }


                        when(time3){

                            "mon1"->{
                                mon1.text = title

                            }
                            "mon2"->{
                                mon2.text = title

                            }

                            "mon3"->{
                                mon3.text = title

                            }

                            "mon4"->{
                                mon4.text = title

                            }

                            "mon5"->{
                                mon5.text = title

                            }

                            "mon6"->{
                                mon6.text = title

                            }

                            "tue1" ->{
                                tue1.text = title
                            }

                            "tue2" ->{
                                tue2.text = title
                            }
                            "tue3" ->{
                                tue3.text = title
                            }
                            "tue4" ->{
                                tue4.text = title
                            }
                            "tue5" ->{
                                tue5.text = title
                            }
                            "tue6" ->{
                                tue6.text = title
                            }
                            "fri1" ->{
                                fri1.text = title
                            }

                            "fri2" ->{
                                fri2.text = title
                            }
                            "fri3" ->{
                                fri3.text = title
                            }
                            "fri4" ->{
                                fri4.text = title
                            }
                            "fri5" ->{
                                fri5.text = title
                            }
                            "fir6" ->{
                                fri6.text = title
                            }
                        }




                    }

                }

                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }


*/

// My top posts by number of stars
        val myPostsQuery = FBRef.CourseRef.orderByChild("email").equalTo(currentID)

        myPostsQuery.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                val dataModel = snapshot.getValue(mycourse::class.java)

                val time1 =dataModel!!.courseTime1
                val time2 =dataModel!!.courseTime2
                val time3 =dataModel!!.courseTime3
                val title = dataModel!!.courseTitle



                when(time1){
                    "mon1"->{
                        mon1.text = title

                    }
                    "mon2"->{
                        mon2.text = title

                    }

                    "mon3"->{
                        mon3.text = title

                    }

                    "mon4"->{
                        mon4.text = title

                    }

                    "mon5"->{
                        mon5.text = title

                    }

                    "mon6"->{
                        mon6.text = title

                    }
                    "tue1" ->{
                        tue1.text = title
                    }

                    "tue2" ->{
                        tue2.text = title
                    }
                    "tue3" ->{
                        tue3.text = title
                    }
                    "tue4" ->{
                        tue4.text = title
                    }
                    "tue5" ->{
                        tue5.text = title
                    }
                    "tue6" ->{
                        tue6.text = title
                    }


                    "fri1" ->{
                        fri1.text = title
                    }

                    "fri2" ->{
                        fri2.text = title
                    }
                    "fri3" ->{
                        fri3.text = title
                    }
                    "fri4" ->{
                        fri4.text = title
                    }
                    "fri5" ->{
                        fri5.text = title
                    }
                    "fir6" ->{
                        fri6.text = title
                    }



                }

                when(time2){

                    "mon1"->{
                        mon1.text = title

                    }
                    "mon2"->{
                        mon2.text = title

                    }

                    "mon3"->{
                        mon3.text = title

                    }

                    "mon4"->{
                        mon4.text = title

                    }

                    "mon5"->{
                        mon5.text = title

                    }

                    "mon6"->{
                        mon6.text = title

                    }

                    "tue1" ->{
                        tue1.text = title
                    }

                    "tue2" ->{
                        tue2.text = title
                    }
                    "tue3" ->{
                        tue3.text = title
                    }
                    "tue4" ->{
                        tue4.text = title
                    }
                    "tue5" ->{
                        tue5.text = title
                    }
                    "tue6" ->{
                        tue6.text = title
                    }
                    "fri1" ->{
                        fri1.text = title
                    }

                    "fri2" ->{
                        fri2.text = title
                    }
                    "fri3" ->{
                        fri3.text = title
                    }
                    "fri4" ->{
                        fri4.text = title
                    }
                    "fri5" ->{
                        fri5.text = title
                    }
                    "fir6" ->{
                        fri6.text = title
                    }
                }


                when(time3){

                    "mon1"->{
                        mon1.text = title

                    }
                    "mon2"->{
                        mon2.text = title

                    }

                    "mon3"->{
                        mon3.text = title

                    }

                    "mon4"->{
                        mon4.text = title

                    }

                    "mon5"->{
                        mon5.text = title

                    }

                    "mon6"->{
                        mon6.text = title

                    }

                    "tue1" ->{
                        tue1.text = title
                    }

                    "tue2" ->{
                        tue2.text = title
                    }
                    "tue3" ->{
                        tue3.text = title
                    }
                    "tue4" ->{
                        tue4.text = title
                    }
                    "tue5" ->{
                        tue5.text = title
                    }
                    "tue6" ->{
                        tue6.text = title
                    }
                    "fri1" ->{
                        fri1.text = title
                    }

                    "fri2" ->{
                        fri2.text = title
                    }
                    "fri3" ->{
                        fri3.text = title
                    }
                    "fri4" ->{
                        fri4.text = title
                    }
                    "fri5" ->{
                        fri5.text = title
                    }
                    "fir6" ->{
                        fri6.text = title
                    }

                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

                Log.d(TAG, "onChildRemoved:" + snapshot.key!!)

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


            // ...
        })



    }


}


