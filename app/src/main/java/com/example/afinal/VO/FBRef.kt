package com.bokchi.mysolelife.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object {

        private val database = Firebase.database

        val CourseRef = database.getReference("mycourse")
        val ReviewRef = database.getReference("review")

        val EvaluBoardRef= database.getReference("evaluateboard")

        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")
        val calRef = database.getReference("calendar")


    }

}