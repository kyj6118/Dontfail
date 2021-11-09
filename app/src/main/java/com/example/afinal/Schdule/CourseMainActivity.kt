package com.example.afinal.Schdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.example.afinal.R
import com.example.afinal.board.BoardWriteActivity
import com.example.afinal.databinding.ActivityCourseMainBinding

class CourseMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCourseMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_main)


        val monday1 = findViewById<TextView>(R.id.mon1)
        val monday2 = findViewById<TextView>(R.id.mon2)
        val monday3 = findViewById<TextView>(R.id.mon3)
        val monday4 = findViewById<TextView>(R.id.mon4)
        val monday5 = findViewById<TextView>(R.id.mon5)
        val monday6 = findViewById<TextView>(R.id.mon6)
        val tuesday1 = findViewById<TextView>(R.id.tue1)
        val tuesday2 = findViewById<TextView>(R.id.tue2)
        val tuesday3 = findViewById<TextView>(R.id.tue3)
        val tuesday4 = findViewById<TextView>(R.id.tue4)
        val tuesday5 = findViewById<TextView>(R.id.tue5)
        val tuesday6 = findViewById<TextView>(R.id.tue6)
        val wedsday1 = findViewById<TextView>(R.id.wed1)
        val wedsday2 = findViewById<TextView>(R.id.wed2)
        val wedsday3 = findViewById<TextView>(R.id.wed3)
        val wedsday4 = findViewById<TextView>(R.id.wed4)
        val wedsday5 = findViewById<TextView>(R.id.wed5)
        val wedsday6 = findViewById<TextView>(R.id.wed6)
        val thursday1 = findViewById<TextView>(R.id.thur1)
        val thursday2 = findViewById<TextView>(R.id.thur2)
        val thursday3 = findViewById<TextView>(R.id.thur3)
        val thursday4 = findViewById<TextView>(R.id.thur4)
        val thursday5 = findViewById<TextView>(R.id.thur5)
        val thursday6 = findViewById<TextView>(R.id.thur6)
        val friday1 = findViewById<TextView>(R.id.fri1)
        val friday2 = findViewById<TextView>(R.id.fri2)
        val friday3 = findViewById<TextView>(R.id.fri3)
        val friday4 = findViewById<TextView>(R.id.fri4)
        val friday5 = findViewById<TextView>(R.id.fri5)
        val friday6 = findViewById<TextView>(R.id.fri6)







        val ImageView: ImageView = findViewById(R.id.CoursePlusBtn)




        ImageView.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)

        }




    }
}