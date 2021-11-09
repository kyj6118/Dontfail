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
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.afinal.R
import com.example.afinal.VO.board
import com.example.afinal.board.FreeBoardInsideActivity
import android.widget.Toast.makeText as makeText1


class courseListAdapter(val courseList:MutableList<course>) : BaseAdapter() {

    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    private val CourseKeyList = mutableListOf<String>()



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

        var view =convertView
        if (view == null) {


            view = LayoutInflater.from(parent?.context).inflate(com.example.afinal.R.layout.course, parent, false)
        }

        val  Button = view?.findViewById<Button>(com.example.afinal.R.id.addButton)
        val course=courseList[position]



        val courseGrade = view?.findViewById<TextView>(com.example.afinal.R.id.courseGrade)
        val courseTitle = view?.findViewById<TextView>(com.example.afinal.R.id.courseTitle)
        val courseCredit = view?.findViewById<TextView>(com.example.afinal.R.id.courseCredit)
        val courseDivide = view?.findViewById<TextView>(com.example.afinal.R.id.courseDivide)
        val coursePersonnel = view?.findViewById<TextView>(com.example.afinal.R.id.coursePersonnel)
        val courseProfessor = view?.findViewById<TextView>(com.example.afinal.R.id.courseProfessor)
        val courseTime1 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime1)
        val courseTime2 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime2)
        val courseTime3 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime3)



        courseGrade!!.text=course.courseGrade
        courseTitle!!.text=course.courseTitle
        courseCredit!!.text=course.courseCredit
        courseDivide!!.text=course.courseDivide
        coursePersonnel!!.text=course.coursePersonnel
        courseProfessor!!.text=course.courseProfessor
        courseTime1!!.text=course.courseTime1
        courseTime2!!.text=course.courseTime2
        courseTime3!!.text=course.courseTime3



        Button!!.setOnClickListener{


            val email = FBAuth.getemail()
            val key = FBRef.CourseRef.push().key.toString()



            val mycourse = hashMapOf(
                "email" to email,
                "courseTitle" to course.courseTitle.toString(),
                "courseTime1" to course.courseTime1.toString(),
                "courseTime2"  to course.courseTime2.toString(),
                "courseTime3"  to course.courseTime3.toString()



            )


            db.collection("mycourse")
                .document(key)
                .set(mycourse)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }


        }







        return view!!
    }



}

