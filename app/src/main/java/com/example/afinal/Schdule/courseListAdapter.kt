package com.example.afinal.Schdule

import android.app.AlertDialog
import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.VO.FBAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.afinal.R
import com.example.afinal.VO.board
import com.example.afinal.board.FreeBoardInsideActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.widget.Toast.makeText as makeText1
import android.widget.Toast.makeText as widgetToastMakeText


class courseListAdapter(val courseList:MutableList<course>) : BaseAdapter() {

    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    private val CourseKeyList = mutableListOf<String>()

    private  var Xtitle: String? = null
    private  var Xtime: String? = null



    override fun getCount(): Int {
        return courseList.size
    }

    override fun getItem(position: Int): Any {
        return  courseList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore

        var view = convertView
        if (view == null) {


            view = LayoutInflater.from(parent?.context)
                .inflate(com.example.afinal.R.layout.course, parent, false)
        }

        val Button = view?.findViewById<Button>(com.example.afinal.R.id.addButton)
        val course = courseList[position]


        val courseGrade = view?.findViewById<TextView>(com.example.afinal.R.id.courseGrade)
        val courseTitle = view?.findViewById<TextView>(com.example.afinal.R.id.courseTitle)
        val courseCredit = view?.findViewById<TextView>(com.example.afinal.R.id.courseCredit)
        val courseDivide = view?.findViewById<TextView>(com.example.afinal.R.id.courseDivide)
        val coursePersonnel = view?.findViewById<TextView>(com.example.afinal.R.id.coursePersonnel)
        val courseProfessor = view?.findViewById<TextView>(com.example.afinal.R.id.courseProfessor)
        val courseTime1 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime1)
        val courseTime2 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime2)
        val courseTime3 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime3)





        courseGrade!!.text = course.courseGrade
        courseTitle!!.text = course.courseTitle
        courseCredit!!.text = course.courseCredit
        courseDivide!!.text = course.courseDivide
        coursePersonnel!!.text = course.coursePersonnel
        courseProfessor!!.text = course.courseProfessor
        courseTime1!!.text = course.courseTime1
        courseTime2!!.text = course.courseTime2
        courseTime3!!.text = course.courseTime3


        //중복 체크

        val currentID = FBAuth.getemail()

     /*   db.collection("mycourse")
            .whereEqualTo("email", currentID)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")


                    val time1 = document.getString("courseTime1")
                    val time2 = document.getString("courseTime2")
                    val time3 = document.getString("courseTime3")
                    val title = document.getString("courseTitle")


                    Xtitle = document.getString("courseTitle").toString()
                    Xtime = document.getString("courseTime1").toString()


                }


            }
*/

        val myPostsQuery = FBRef.CourseRef.orderByChild("email").equalTo(currentID)

        myPostsQuery.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                val dataModel = snapshot.getValue(mycourse::class.java)

                val time1 = dataModel!!.courseTime1
                val time2 = dataModel!!.courseTime2
                val time3 = dataModel!!.courseTime3
                val title = dataModel!!.courseTitle

                Xtitle =dataModel!!.courseTitle.toString()
                Xtime =dataModel!!.courseTime1.toString()


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })










        Button!!.setOnClickListener {


            val email = FBAuth.getemail()
            val key = FBRef.CourseRef.push().key.toString()


            val mycourse = hashMapOf(
                "email" to email,
                "courseTitle" to course.courseTitle.toString(),
                "courseTime1" to course.courseTime1.toString(),
                "courseTime2" to course.courseTime2.toString(),
                "courseTime3" to course.courseTime3.toString()


            )






            if (Xtitle == course.courseTitle.toString()) {

                Toast.makeText(
                    view?.rootView?.context,
                    "Already same Course you have",
                    Toast.LENGTH_SHORT
                ).show()

            }

            else if(Xtime == course.courseTime1.toString()){

                Toast.makeText(
                    view?.rootView?.context,
                    "The timetable overlaps",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else if(Xtime == course.courseTime2.toString() ){


                Toast.makeText(
                    view?.rootView?.context,
                    "The timetable overlaps",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else if(Xtime == course.courseTime3.toString()){
                Toast.makeText(
                    view?.rootView?.context,
                    "The timetable overlaps",
                    Toast.LENGTH_SHORT
                ).show()

            }

            else {

/*

                db.collection("mycourse")
                    .document(key)
                    .set(mycourse)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                        Toast.makeText(
                            view?.rootView?.context,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                    .addOnFailureListener { e ->
                        Log.w(
                            ContentValues.TAG,
                            "Error writing document",
                            e
                        )
                    }

*/


                FBRef.CourseRef
                    .child(key)
                    .setValue(mycourse(course.courseTitle.toString(),
                        course.courseTime1.toString(),
                        course.courseTime2.toString(),
                        course.courseTime3.toString(), email))

                Toast.makeText(
                    view?.rootView?.context,
                    "Success",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }




        return view!!
    }




}

