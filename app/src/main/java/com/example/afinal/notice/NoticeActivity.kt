package com.example.afinal.notice

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.afinal.R
import com.example.afinal.VO.notice
import com.example.afinal.databinding.ActivityNoticeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoticeActivity : AppCompatActivity() {
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView
    private val noticeList = mutableListOf<notice>()

    private lateinit var binding : ActivityNoticeBinding
    private lateinit var noticeadapter: noticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)



        noticeadapter = noticeAdapter(noticeList)
        binding.listView.adapter= noticeadapter



        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        getFBNoticeData()
    }

    private fun getFBNoticeData() {
        val db = Firebase.firestore
        db.collection("notice")
            .get()
            .addOnSuccessListener { result ->


                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${noticeList.toString()}")
                for (document in result) {


                    noticeList.add(
                        notice(
                            document["notice"].toString()
                        )
                    )

                }
                noticeList.reverse()
                noticeadapter.notifyDataSetChanged()

            }
    }

}
