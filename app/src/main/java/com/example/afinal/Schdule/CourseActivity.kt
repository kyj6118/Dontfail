package com.example.afinal.Schdule

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.afinal.R
import com.example.afinal.databinding.ActivityCourseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCourseBinding
    private val courseDataList = mutableListOf<course>()


    private lateinit var courseAdater: courseListAdapter
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView

    private var yearAdapter: ArrayAdapter<*>? = null
    private var yearSpinner: Spinner? = null
    private var termAdapter: ArrayAdapter<*>? = null
    private var termSpinner: Spinner? = null
    private var areaAdapter: ArrayAdapter<*>? = null
    private var areaSpinner: Spinner? = null
    private var majorAdapter: ArrayAdapter<*>? = null
    private var majorSpinner: Spinner? = null

    private var courseUniversity = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_course)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_course)



        courseAdater = courseListAdapter(courseDataList)
        binding.courseListView.adapter = courseAdater



        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()






        getFBNoticeData()
    }

    private fun getFBNoticeData() {

        val db = Firebase.firestore
        db.collection("course")
            .get()
            .addOnSuccessListener { result ->


                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${courseDataList.toString()}")
                for (document in result) {


                    courseDataList.add(
                        course(
                            document["courseCredit"].toString(),
                            document["courseDivide"].toString(),
                            document["courseGrade"].toString(),
                            document["courseMajor"].toString(),
                            document["coursePersonnel"].toString(),
                            document["courseProfessor"].toString(),
                            document["courseRoom"].toString(),
                            document["courseTime"].toString(),
                            document["courseTitle"].toString()
                        )
                    )

                }
                courseDataList.reverse()
                courseAdater.notifyDataSetChanged()


            }
    }

}
