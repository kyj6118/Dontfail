package com.example.afinal.Schdule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class courseListAdapter(val courseList:MutableList<course>) : BaseAdapter() {
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


        var view =convertView
        if (view == null) {

            view = LayoutInflater.from(parent?.context).inflate(com.example.afinal.R.layout.course, parent, false)
        }


        val courseGrade = view?.findViewById<TextView>(com.example.afinal.R.id.courseGrade)
        val courseTitle = view?.findViewById<TextView>(com.example.afinal.R.id.courseTitle)
        val courseCredit = view?.findViewById<TextView>(com.example.afinal.R.id.courseCredit)
        val courseDivide = view?.findViewById<TextView>(com.example.afinal.R.id.courseDivide)
        val coursePersonnel = view?.findViewById<TextView>(com.example.afinal.R.id.coursePersonnel)
        val courseProfessor = view?.findViewById<TextView>(com.example.afinal.R.id.courseProfessor)
        val courseTime = view?.findViewById<TextView>(com.example.afinal.R.id.courseTime)

        val course=courseList[position]


        courseGrade!!.text=course.courseGrade
        courseTitle!!.text=course.courseTitle
        courseCredit!!.text=course.courseCredit
        courseDivide!!.text=course.courseDivide
        coursePersonnel!!.text=course.coursePersonnel
        courseProfessor!!.text=course.courseProfessor
        courseTime!!.text=course.courseTime


        return view!!
    }


}