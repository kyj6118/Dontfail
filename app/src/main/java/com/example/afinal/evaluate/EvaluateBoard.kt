package com.example.afinal.evaluate

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.evaluate
import com.example.afinal.board.FreeBoardInsideActivity
import com.example.afinal.board.FreeBoardListAdapter
import com.example.afinal.databinding.ActivityEvaluateBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EvaluateBoard : AppCompatActivity() {

    private lateinit var binding : ActivityEvaluateBoardBinding
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    lateinit var listView: ListView
    private val evaluateList = mutableListOf<evaluate>()
    private val evaluateKeyList = mutableListOf<String>()


    private lateinit var evaluateRVAdapter: EvaluateListAdater





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_board)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_board)

/*        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()*/
        getFBEvaluateData()





        //글쓰기 액티비티 이동
        val ImageView: ImageView = findViewById(R.id.wirteBtn)
        ImageView.setOnClickListener {

            val intent = Intent(this, EvaluateBoardWrite::class.java)
            startActivity(intent)
        }

        binding.evaluateListView.setOnItemClickListener { parent, view, position, id ->


            val intent = Intent(this, EvaluateBoardInsideActivity::class.java)
            intent.putExtra("key", evaluateKeyList[position])
            startActivity(intent)}



    }



    private fun getFBEvaluateData() {
        evaluateRVAdapter = EvaluateListAdater(evaluateList)
        binding.evaluateListView.adapter = evaluateRVAdapter

        val db = Firebase.firestore

        evaluateList.clear()

/*
            db.collection("evaluateboard")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        evaluateList.add(
                            evaluate
                                (
                                document["title"].toString(),
                                document["professor"].toString(),
                                document["rating"].toString().toFloat()
                            )
                        )


                        evaluateKeyList.add(document.id.toString())

                    }


                    evaluateKeyList.reverse()
                    evaluateList.reverse()
                    evaluateRVAdapter.notifyDataSetChanged()

                }*/

        //실시간 데이터로 업데이트 하는 방법
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                evaluateList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(evaluate::class.java)
                    evaluateList.add(item!!)
                    evaluateKeyList.add(dataModel.key.toString())

                }


                evaluateKeyList.reverse()
                evaluateList.reverse()
                evaluateRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.EvaluBoardRef.addValueEventListener(postListener)
    }

}


