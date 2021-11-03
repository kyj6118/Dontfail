package com.example.afinal.evaluate

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.comment.CommentLVAdapter
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityEvaluateBoardInsideBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class EvaluateBoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEvaluateBoardInsideBinding
    private lateinit var key: String

    private val evaluateDataList = mutableListOf<evaluate>()

    private lateinit var evaluateAdater: EvaluateListAdater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_board_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_board_inside)



        key = intent.getStringExtra("key").toString()

        getEvaluateBoardData(key)


        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }





    }

    private fun showDialog() {


        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            val intent = Intent(this, EvaluateBoardModifyActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.deletBtn)?.setOnClickListener {

            db.collection("evaluate").document(key)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

            FBRef.EvaluBoardRef.child(key).removeValue()
            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
            finish()

        }
    }

    private fun getEvaluateBoardData(key: String) {
/*
        val db = Firebase.firestore
        val docRef = db.collection("evaluateboard").document(key)

        val rating = findViewById<RatingBar>(R.id.classRating)

        docRef
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")




                    binding.classArea.setText(document.get("title").toString())
                    binding.professorArea.setText(document.get("professor").toString())
                    binding.contentArea.setText(document.get("contents").toString())
                    rating!!.rating=document.get("rating").toString().toFloat()



                    val myUid = FBAuth.getemail()
                    val writeUid = document.get("email").toString()


                    Log.d(TAG, "getemail" +  FBAuth.getemail() )
                    Log.d(TAG, "writeUid" +  writeUid )

                    if(myUid.equals(writeUid)){
                        binding.boardSettingIcon.isVisible = true
                    }



                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }*/
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val rating = findViewById<RatingBar>(R.id.classRating)
                    val dataModel = dataSnapshot.getValue(evaluate::class.java)

                    binding.classArea.text = dataModel!!.title
                    binding.professorArea.text = dataModel!!.professor
                    binding.contentArea.text = dataModel!!.contents
                    binding.classRating.rating = dataModel!!.rating.toString().toFloat()


                    val myUid = FBAuth.getemail()
                    val writerUid = dataModel.email

                    Log.d(TAG, "getemail" +  FBAuth.getemail() )
                    Log.d(TAG, "writeUid" +  writerUid )


                    if (myUid.equals(writerUid)) {
                        Log.d(TAG, "내가 쓴 글")
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        Log.d(TAG, "내가 쓴 글 아님")
                    }

                } catch (e: Exception) {
                    Log.d(TAG, "getemail" +  FBAuth.getemail() )
                    Log.d(TAG, "삭제완료")

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.EvaluBoardRef.child(key).addValueEventListener(postListener)
    }
}

