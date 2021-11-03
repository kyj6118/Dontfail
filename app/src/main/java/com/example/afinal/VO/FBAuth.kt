package com.example.afinal.VO

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {

    companion object{

        private lateinit var auth:FirebaseAuth

        fun getUid():String{

            auth= FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }

        fun getemail():String{
            auth= FirebaseAuth.getInstance()
            return auth.currentUser?.email.toString()
        }

        fun getTime() : String{
            val currentDataTime= Calendar.getInstance().time
            val dataFomat = SimpleDateFormat("yyyy.MM.dd. HH:mm:ss",Locale.KOREA).format(currentDataTime)

            return  dataFomat
        }
    }
}