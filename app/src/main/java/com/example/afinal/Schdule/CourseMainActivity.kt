package com.example.afinal.Schdule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
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




        val ImageView: ImageView = findViewById(R.id.CoursePlusBtn)
        ImageView.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)

        }


    }
}