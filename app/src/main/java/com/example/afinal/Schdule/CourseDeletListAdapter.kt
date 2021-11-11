package com.example.afinal.Schdule

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.afinal.VO.FBAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.custom.asyncResult

class CourseDeletListAdapter (val courseList:MutableList<mycourse>) : BaseAdapter() {

    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth


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
        var view = convertView
        if (view == null) {


            view = LayoutInflater.from(parent?.context)
                .inflate(com.example.afinal.R.layout.course_delet, parent, false)
        }

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore



        val course = courseList[position]

        val courseTitle = view?.findViewById<TextView>(com.example.afinal.R.id.courseTitle)
        val courseTime1 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime1)
        val courseTime2 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime2)
        val courseTime3 = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime3)


        courseTitle!!.text=course.courseTitle
        courseTime1!!.text=course.courseTime1
        courseTime2!!.text=course.courseTime2
        courseTime3!!.text=course.courseTime3





        return view!!
    }


}