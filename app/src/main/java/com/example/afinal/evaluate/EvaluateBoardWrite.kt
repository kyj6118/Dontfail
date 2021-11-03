package com.example.afinal.evaluate

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityEvaluateBoardWriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EvaluateBoardWrite : AppCompatActivity(){

        var firestore : FirebaseFirestore? = null
        private lateinit var auth: FirebaseAuth
        private lateinit var binding : ActivityEvaluateBoardWriteBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_board_write)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_board_write)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore

        binding.writeBtn.setOnClickListener {

            val title = binding.classArea.text.toString()
            val content = binding.contentArea.text.toString()
            val professor = binding.professorArea.text.toString()
            val rating = binding.classRating.rating.toFloat()
            val uid = FBAuth.getUid()
            val email = FBAuth.getemail()


            Log.d(TAG, title)
            Log.d(TAG, content)

            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.

            val key = FBRef.EvaluBoardRef.push().key.toString()



            FBRef.EvaluBoardRef
                .child(key)
                .setValue(evaluate(title, professor, rating, content, email, uid))



            Toast.makeText(this, "게시글 입력 완료", Toast.LENGTH_LONG).show()
            finish()

           Log.d(TAG, title)
            Log.d(TAG, content)

            val evaluate = hashMapOf(
                "title" to title,
                "contents" to content,
                "id" to email,
                "rating" to rating,
                "professor" to professor,
                "email" to email
            )

            db.collection("evaluateboard")
                .document(key)
                .set(evaluate)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

            Toast.makeText(this,"Sucess write board", Toast.LENGTH_LONG).show()
            finish()

        }
        }
    }
